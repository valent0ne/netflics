package it.univaq.disim.netflics.dispatcher.service;

import it.univaq.disim.netflics.clients.vault.GetMovieRequest;
import it.univaq.disim.netflics.clients.vault.GetMovieResponse;
import it.univaq.disim.netflics.clients.vault.VaultPT;
import it.univaq.disim.netflics.clients.vault.VaultService;
import it.univaq.disim.netflics.dispatcher.BusinessException;
import it.univaq.disim.netflics.dispatcher.model.Availability;
import it.univaq.disim.netflics.dispatcher.model.Movie;
import it.univaq.disim.netflics.dispatcher.model.Supplier;
import it.univaq.disim.netflics.dispatcher.model.UserMovie;
import it.univaq.disim.netflics.dispatcher.repository.*;
import org.apache.cxf.helpers.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

@SuppressWarnings("Duplicates")
@Component
public class LoadBalancer {

    private static Logger LOGGER = LoggerFactory.getLogger(LoadBalancer.class);

    @Autowired
    private static SupplierRepository supplierRepository;

    @Autowired
    private static SessionRepository sessionRepository;

    @Autowired
    private static AvailabilityRepository availabilityRepository;

    @Autowired
    private static UserMovieRepository userMovieRepository;

    @Autowired
    private static MovieRepository movieRepository;

    @Value("#{cfg.numsupplierstofetch}")
    private int numSuppliersToFetch;

    @Value("#{cfg.numsupplierstowakeup}")
    private int numSuppliersToWakeUp;

    @Value("#{cfg.freeslotsthreshold}")
    private int freeSlotsThreshold;

    private ConcurrentHashMap<Supplier, Availability> freeSuppliers = new ConcurrentHashMap<>();
    private HashMap<Supplier, Availability> freeSuppliersHavingMovie = new HashMap<>();;
    private HashMap<Supplier, Availability> freeSuppliersNotHavingMovie = new HashMap<>();

    private List<Supplier> sleepingSuppliers = new ArrayList<>();;

    private List<Supplier> suppliersToWakeUp = new ArrayList<>();
    private List<Supplier> suppliersToSleep = new ArrayList<>();
    private List<Supplier> suppliersToFetch = new ArrayList<>();

    private Supplier chosenOne;
    private boolean streamFromVault = false;

    private VaultService vaultService = new VaultService();;
    private VaultPT vaultPT = vaultService.getVaultPort();;

    private Movie movie;
    private String token;

    LoadBalancer(){}

    LoadBalancer(String token, Movie movie) {

        this.movie = movie;
        this.token = token;
    }

    /**
     * retrieves the system's status (suppliers data and their availabilities
     */
    void monitor() {

        // fill the sleepingSuppliers list
        sleepingSuppliers = supplierRepository.findAllByStatus("SLEEP");

        // fill the freeSuppliers list
        List<Supplier> suppliersToPoll = supplierRepository.findAllByStatus("AWAKE");

        // call getAvailability of all awake suppliers
        try {
            ExecutorService getAvailabilityThreadPool = Executors.newFixedThreadPool(suppliersToPoll.size());
            for (Supplier s : suppliersToPoll) {
                getAvailabilityThreadPool.submit(() -> sendGetAvailability(s, token, freeSuppliers, freeSlotsThreshold));
            }
            getAvailabilityThreadPool.shutdown();
            getAvailabilityThreadPool.awaitTermination(3000, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
            LOGGER.warn("500/error while polling the suppliers, continuing anyway: {}", e.getMessage());
        }
        // freeSuppliers is now filled
    }

    /**
     * analyzes the data retrieved by the monitor phase
     */
    void analyze(){

        List<Supplier> allSuplliersHavingMovie = supplierRepository.findAllByMovieFetched(movie.getImdbId());

        for (Map.Entry<Supplier, Availability> entry : freeSuppliers.entrySet()) {
            if (allSuplliersHavingMovie.contains(entry.getKey())) {
                freeSuppliersHavingMovie.put(entry.getKey(), entry.getValue());
            } else {
                freeSuppliersNotHavingMovie.put(entry.getKey(), entry.getValue());
            }
        }
        // freeSuppliersHavingMovie and freeSuppliersNotHavingMovie are file
    }

    /**
     * defines what operations ust be performed
     */
    void plan(){

        if(freeSuppliers.size() > 0){

            if(freeSuppliersHavingMovie.size() > 0){
                // chose the best supplier to stream
                chosenOne = bestSuppliers(freeSuppliersHavingMovie).get(0);
            }else{ // there are free suppliers not having movie
                // choose suppliers to fetch movie
                suppliersToFetch = bestSuppliers(freeSuppliersNotHavingMovie).subList(0, Math.min(freeSuppliersNotHavingMovie.size(), numSuppliersToFetch));
            }

            // if there are suppliers that are not serving anyone, put them to sleep list
            for(Map.Entry<Supplier, Availability> entry : freeSuppliers.entrySet()){
                if(entry.getValue().getFreeSlots() == entry.getKey().getSlots()){
                    suppliersToSleep.add(entry.getKey());
                }
            }
            // if the chosen one ended up on the sleep list, remove it
            suppliersToSleep.remove(chosenOne);

        }else{ // there are no free suppliers

            if(sleepingSuppliers.size() > 0){
                // choose suppliers to wake up and fecth (between sleep suppliers, if any)
                Collections.shuffle(sleepingSuppliers);
                suppliersToWakeUp = sleepingSuppliers.subList(0, Math.min(sleepingSuppliers.size(), numSuppliersToWakeUp));
                suppliersToFetch.addAll(suppliersToWakeUp);
            }else{ // there are suppliers that can be awaken and there are no supplier free, so ask the vault
                streamFromVault = true; // stream from vault
            }
        }

    }

    /**
     * executes the operations defined in the plan phase
     */
    StreamingOutput execute(){

        // send sleep
        if(suppliersToSleep.size() > 0){
            LOGGER.info("sending sleep...");
            try {
                ExecutorService sendSleepThreadPool = Executors.newFixedThreadPool(suppliersToSleep.size());
                for (Supplier s : suppliersToSleep) {
                    sendSleepThreadPool.submit(() -> sendSleep(s));
                }
                sendSleepThreadPool.shutdown();
                // getAvailabilityThreadPool.awaitTermination(3000, TimeUnit.MILLISECONDS);

            } catch (Exception e) {
                LOGGER.warn("500/error sending sleep: {}", e.getMessage());
            }
        }

        // send awake
        if(suppliersToWakeUp.size() > 0){
            LOGGER.info("sending wake up...");
            try {
                ExecutorService sendWakeUpThreadPool = Executors.newFixedThreadPool(suppliersToWakeUp.size());
                for (Supplier s : suppliersToWakeUp) {
                    sendWakeUpThreadPool.submit(() -> sendWakeUp(s));
                }
                sendWakeUpThreadPool.shutdown();
                // getAvailabilityThreadPool.awaitTermination(3000, TimeUnit.MILLISECONDS);

            } catch (Exception e) {
                LOGGER.warn("500/error sending wakeUp: {}", e.getMessage());
            }
        }

        // send fetch
        if(suppliersToFetch.size() > 0){

            LOGGER.info("sending fetchMovie...");
            try {
                ExecutorService sendFetchThreadPool = Executors.newFixedThreadPool(suppliersToSleep.size());
                for (Supplier s : suppliersToSleep) {
                    sendFetchThreadPool.submit(() -> sendFetchMovie(s, token, movie.getImdbId()));
                }
                sendFetchThreadPool.shutdown();
                // getAvailabilityThreadPool.awaitTermination(3000, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                LOGGER.warn("500/error sending fetchMovie: {}", e.getMessage());
            }
        }

        // stream from vault
        if(streamFromVault){

            LOGGER.info("starting stream from Vault...");

            // add entry to user_movie signaling that this user has seen this movie
            userMovieRepository.save(new UserMovie(null, sessionRepository.findByToken(token).getUserId(), movie.getId()));

            // increment movie's views
            movieRepository.updateViews(movie);

            // return the stream
            return outputStream -> {

                GetMovieRequest getMovieRequest = new GetMovieRequest();
                getMovieRequest.setToken(token);
                getMovieRequest.setImdbId(movie.getImdbId());
                GetMovieResponse getMovieResponse = vaultPT.getMovie(getMovieRequest);

                if(!getMovieResponse.getResult().equals("200")){
                    throw new BusinessException(getMovieResponse.getResult());
                }

                try {
                    IOUtils.copy(getMovieResponse.getMovie().getInputStream(), outputStream);

                } catch (Exception e) {
                    LOGGER.warn("streaming error (stream closed by the user?)");

                } finally {
                    outputStream.close();
                }
            };
        }

        // stream from the chosen supplier if any
        if(chosenOne != null){

            LOGGER.info("starting stream from {}...", chosenOne.getId());

            // add entry to user_movie signaling that this user has seen this movie
            userMovieRepository.save(new UserMovie(null, sessionRepository.findByToken(token).getUserId(), movie.getId()));

            // increment movie's views
            movieRepository.updateViews(movie);

            // return the stream
            return outputStream -> {

                Client client = ClientBuilder.newClient();
                WebTarget target = client
                        .target("http://"+chosenOne.getIp()+":"+chosenOne.getPort()+"/supplier/api/supplier/"+token+"/movie/"+movie.getImdbId());
                InputStream is = target.request(MediaType.APPLICATION_OCTET_STREAM).get(InputStream.class);

                try {
                    IOUtils.copy(is, outputStream);

                } catch (Exception e) {
                    LOGGER.warn("streaming error (stream closed by the user?)");

                } finally {
                    is.close();
                    outputStream.close();
                }

            };
        }

        throw new BusinessException("503/the requested movie is currently not available due to system overload, try again later");

    }


    /**
     * sends a getAvailability request to the supplier, if the response is valid,
     * the availability data is stored in a map
     * @param s supplier to query
     * @param token auth token
     * @param map contains the result
     */
    static void sendGetAvailability(Supplier s, String token, ConcurrentHashMap<Supplier, Availability> map, int freeSlotsThreshold) {

        LOGGER.info("sending getAvailability to supplier: {} - {}:{}, token: {}", s.getId(), s.getIp(), s.getPort(), token);

        Availability a;

        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client
                    .target("http://" + s.getIp() + ":" + s.getPort() + "/supplier/api/supplier/" + token + "/availability");
            a = target.request(MediaType.APPLICATION_JSON).get(Availability.class);
        } catch (Exception e) {
            //e.printStackTrace();
            LOGGER.error("the supplier " + s.getId() + " seems unavailable: {}", e.getMessage());
            availabilityRepository.setUnavailable(s);
            return;
        }

        if (a != null && a.getAvailable()) {
            LOGGER.info("supplier " + a.getSupplier_id() + " is available");
            if (a.getFreeSlots() > freeSlotsThreshold) {
                LOGGER.info("supplier " + a.getSupplier_id() + " is free ({} slots are available)", a.getFreeSlots());
                map.put(s, a);
            }
        }
    }

    /**
     * sends a post request invoking the fetchMovie on the designated supplier
     * @param s supplier object
     * @param imdbId the movie of interest
     */
    static void sendFetchMovie(Supplier s, String token, String imdbId){

        LOGGER.info("sending fetchMovie to supplier: {} - {}{}, token: {}", s.getId(), s.getIp(), s.getPort(), token);
        Client client = ClientBuilder.newClient();
        WebTarget target = client
                .target("http://"+s.getIp()+":"+s.getPort()+"/supplier/api/supplier/"+token+"/movie/"+imdbId);
        target.request().post(null);
    }

    /**
     * sends a post request invoking the wakeUp on the designated supplier
     * @param s supplier object
     */
    private void sendWakeUp(Supplier s){

        LOGGER.info("sending wakeUp to supplier: {} - {}{}, token: {}", s.getId(), s.getIp(), s.getPort(), token);
        Client client = ClientBuilder.newClient();
        WebTarget target = client
                .target("http://"+s.getIp()+":"+s.getPort()+"/supplier/api/supplier/"+token+"/wakeup");
        target.request().post(null);
    }

    /**
     * sends a post request invoking the sleep on the designated supplier
     * @param s supplier object
     */
    private void sendSleep(Supplier s){

        LOGGER.info("sending sleep to supplier: {} - {}{}, token: {}", s.getId(), s.getIp(), s.getPort(), token);
        Client client = ClientBuilder.newClient();
        WebTarget target = client
                .target("http://"+s.getIp()+":"+s.getPort()+"/supplier/api/supplier/"+token+"/sleep");
        target.request().post(null);
    }

    /**
     * returns the list of the "best" suppliers by comparing the Availability object in the map (value field) using
     * its compareTo
     * @param map input data
     * @return list of the best suppliers
     */
    private List<Supplier> bestSuppliers(HashMap<Supplier, Availability> map){

        LinkedHashMap<Supplier, Availability> orderedMap = map
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        return new ArrayList<>(orderedMap.keySet());

    }



}
