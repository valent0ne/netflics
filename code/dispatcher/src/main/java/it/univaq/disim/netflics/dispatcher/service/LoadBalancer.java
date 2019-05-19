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
import org.springframework.stereotype.Service;

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
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

@SuppressWarnings("Duplicates")
public class LoadBalancer {

    private static Logger LOGGER = LoggerFactory.getLogger(LoadBalancer.class);

    private SupplierRepository supplierRepository;

    private SessionRepository sessionRepository;

    private AvailabilityRepository availabilityRepository;

    private UserMovieRepository userMovieRepository;

    private MovieRepository movieRepository;

    private int numSuppliersToFetch;
    private int numSuppliersToWakeUp;
    private int freeSlotsThreshold;

    private ConcurrentHashMap<Supplier, Availability> freeSuppliers = new ConcurrentHashMap<>();
    private HashMap<Supplier, Availability> freeSuppliersHavingMovie = new HashMap<>();
    private HashMap<Supplier, Availability> freeSuppliersNotHavingMovie = new HashMap<>();

    private List<Supplier> sleepingSuppliers = new ArrayList<>();

    private List<Supplier> suppliersToWakeUp = new ArrayList<>();
    private List<Supplier> suppliersToSleep = new ArrayList<>();
    private List<Supplier> suppliersToFetch = new ArrayList<>();

    private Supplier chosenOne;
    private boolean streamFromVault = false;

    private VaultService vaultService = new VaultService();;
    private VaultPT vaultPT = vaultService.getVaultPort();;

    private Movie movie;
    private String token;

    LoadBalancer(AvailabilityRepository availabilityRepository,
                 SupplierRepository supplierRepository,
                 UserMovieRepository userMovieRepository,
                 SessionRepository sessionRepository,
                 MovieRepository movieRepository,
                 int numSuppliersToFetch,
                 int numSuppliersToWakeUp,
                 int freeSlotsThreshold,
                 String token,
                 Movie movie) {

        this.availabilityRepository = availabilityRepository;
        this.supplierRepository = supplierRepository;
        this.userMovieRepository = userMovieRepository;
        this.movieRepository = movieRepository;
        this.sessionRepository = sessionRepository;

        this.numSuppliersToFetch = numSuppliersToFetch;
        this.numSuppliersToWakeUp = numSuppliersToWakeUp;
        this.freeSlotsThreshold = freeSlotsThreshold;

        this.movie = movie;
        this.token = token;
    }

    /**
     * retrieves the system's status (suppliers data and their availabilities
     */
    void monitor() {
        LOGGER.info("MONITOR PHASE, token: {}", token);

        // fill the sleepingSuppliers list
        sleepingSuppliers = supplierRepository.findAllByStatus("SLEEP");

        LOGGER.info("there are {} sleeping suppliers, token: {}", sleepingSuppliers.size(), token);

        // fill the freeSuppliers list
        List<Supplier> suppliersToPoll = supplierRepository.findAllByStatus("AWAKE");

        LOGGER.info("there are {} awake suppliers, token: {}", suppliersToPoll.size(), token);

        // call getAvailability of all awake suppliers
        try {
            ExecutorService getAvailabilityThreadPool = Executors.newFixedThreadPool(suppliersToPoll.size());
            for (Supplier s : suppliersToPoll) {
                getAvailabilityThreadPool.submit(() -> sendGetAvailability(s, token, freeSuppliers, freeSlotsThreshold, availabilityRepository));
            }
            getAvailabilityThreadPool.shutdown();
            getAvailabilityThreadPool.awaitTermination(5000, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
            LOGGER.warn("error while polling the suppliers, continuing anyway: {}", e.getMessage());
        }
        // freeSuppliers is now filled
    }

    /**
     * analyzes the data retrieved by the monitor phase
     */
    void analyze(){
        LOGGER.info("ANALYZE PHASE, token: {}", token);

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
        LOGGER.info("PLAN PHASE, token: {}", token);

        if(freeSuppliers.size() > 0){
            LOGGER.info("there are free suppliers, token: {}", token);
            if(freeSuppliersHavingMovie.size() > 0){
                LOGGER.info("there are free suppliers having movie, token: {}", token);
                // chose the best supplier to stream
                chosenOne = bestSuppliers(freeSuppliersHavingMovie).get(0);
                LOGGER.info("there best supplier has been chosen: {}, token: {}", chosenOne.getId(), token);
            }else{ // there are free suppliers not having movie
                LOGGER.info("there are free suppliers not having the movie, token: {}", token);
                // choose suppliers to fetch movie
                suppliersToFetch.addAll(bestSuppliers(freeSuppliersNotHavingMovie).subList(0, Math.min(freeSuppliersNotHavingMovie.size(), numSuppliersToFetch)));
            }

            // if there are suppliers that are not serving anyone, put them to sleep list
            for(Map.Entry<Supplier, Availability> entry : freeSuppliers.entrySet()){
                if(entry.getValue().getFreeSlots() == entry.getKey().getSlots()){
                    suppliersToSleep.add(entry.getKey());
                }
            }
            // if the chosen one or one of the supplierstofetch have ended up on the sleep list, remove them
            suppliersToSleep.remove(chosenOne);
            suppliersToSleep.removeAll(suppliersToFetch);

        }else{ // there are no free suppliers
            LOGGER.info("there are no free suppliers available");
            if(sleepingSuppliers.size() > 0){
                LOGGER.info("there are sleeping suppliers, token: {}", token);
                // choose suppliers to wake up and fecth (between sleep suppliers, if any)
                Collections.shuffle(sleepingSuppliers);
                suppliersToWakeUp.addAll(sleepingSuppliers.subList(0, Math.min(sleepingSuppliers.size(), numSuppliersToWakeUp)));
                suppliersToFetch.addAll(suppliersToWakeUp);
                LOGGER.info("waiting for suppliers to wake up and fecth: try again later, token: {}", token);
                // streamFromVault = true;
            }else{ // there are suppliers that can be awaken and there are no supplier free, so ask the vault
                LOGGER.info("no free suppliers and no sleeping suppliers: streaming from vault, token: {}", token);
                streamFromVault = true; // stream from vault
            }
        }

    }

    /**
     * executes the operations defined in the plan phase
     */
    StreamingOutput execute(){
        LOGGER.info("EXECUTE PHASE, token: {}", token);

        // send sleep
        if(suppliersToSleep.size() > 0){
            LOGGER.info("sending sleep..., token: {}", token);
            try {
                ExecutorService sendSleepThreadPool = Executors.newFixedThreadPool(suppliersToSleep.size());
                for (Supplier s : suppliersToSleep) {
                    sendSleepThreadPool.submit(() -> sendSleep(s));
                }
                sendSleepThreadPool.shutdown();
                // getAvailabilityThreadPool.awaitTermination(3000, TimeUnit.MILLISECONDS);

            } catch (Exception e) {
                LOGGER.warn("error sending sleep: {}", e.getMessage());
            }
        }

        // send awake
        if(suppliersToWakeUp.size() > 0){
            LOGGER.info("sending wake up..., token: {}", token);
            try {
                ExecutorService sendWakeUpThreadPool = Executors.newFixedThreadPool(suppliersToWakeUp.size());
                for (Supplier s : suppliersToWakeUp) {
                    sendWakeUpThreadPool.submit(() -> sendWakeUp(s));
                }
                sendWakeUpThreadPool.shutdown();
                // getAvailabilityThreadPool.awaitTermination(3000, TimeUnit.MILLISECONDS);

            } catch (Exception e) {
                LOGGER.warn("error sending wakeUp: {}", e.getMessage());
            }
        }

        // send fetch
        if(suppliersToFetch.size() > 0){

            LOGGER.info("sending fetchMovie..., token: {}", token);
            try {
                ExecutorService sendFetchThreadPool = Executors.newFixedThreadPool(suppliersToFetch.size());
                for (Supplier s : suppliersToFetch) {
                    sendFetchThreadPool.submit(() -> sendFetchMovie(s, token, movie.getImdbId()));
                }
                sendFetchThreadPool.shutdown();
                // getAvailabilityThreadPool.awaitTermination(3000, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                // e.printStackTrace();
                LOGGER.warn("error sending fetchMovie: {}", e.getMessage());
            }
        }

        // stream from vault
        if(streamFromVault){

            LOGGER.info("starting stream from Vault..., token: {}", token);

            // add entry to user_movie signaling that this user has seen this movie
            userMovieRepository.save(new UserMovie(null, sessionRepository.findByToken(token).getUserId(), movie.getId()));

            // increment movie's views
            movieRepository.updateViews(movie);

            // return the stream
            return outputStream -> {

                try{
                    // artificial vault delay
                    TimeUnit.SECONDS.sleep(15);
                }catch (Exception ignored){}

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

            LOGGER.info("starting stream from {}..., token: {}", chosenOne.getId(), token);

            // add entry to user_movie signaling that this user has seen this movie
            userMovieRepository.save(new UserMovie(null, sessionRepository.findByToken(token).getUserId(), movie.getId()));

            // increment movie's views
            movieRepository.updateViews(movie);

            // return the stream
            return outputStream -> {


                Client client = ClientBuilder.newClient();
                WebTarget target = client.target("http://"+chosenOne.getIp()+":"+chosenOne.getPort()+"/supplier/api/supplier/"+token+"/movie/"+movie.getImdbId());
                InputStream is = target.request(MediaType.APPLICATION_OCTET_STREAM).get(InputStream.class);

                try {
                    outputStream.write(is.read());
                    outputStream.flush();

                } catch (Exception e) {
                    LOGGER.warn("streaming error (stream closed by the user?), token: {}", token);

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
    static void sendGetAvailability(Supplier s, String token, ConcurrentHashMap<Supplier, Availability> map, int freeSlotsThreshold, AvailabilityRepository availabilityRepository) {

        LOGGER.info("sending getAvailability to supplier: {} - {}:{}, token: {}", s.getId(), s.getIp(), s.getPort(), token);

        Availability a;

        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client
                    .target("http://" + s.getIp() + ":" + s.getPort() + "/supplier/api/supplier/" + token + "/availability");
            a = target.request(MediaType.APPLICATION_JSON).get(Availability.class);
        } catch (Exception e) {
            LOGGER.error("the supplier " + s.getId() + " seems unavailable: {}", e.getMessage());
            availabilityRepository.setUnavailable(s);
            return;
        }

        if (a != null && a.getAvailable()) {
            LOGGER.info("supplier " + a.getSupplier_id() + " is available");
            if (a.getFreeSlots() > freeSlotsThreshold) {
                LOGGER.info("supplier " + a.getSupplier_id() + " is free ({} slots are available), token: {}", a.getFreeSlots(), token);
                map.put(s, a);
            }else{
                LOGGER.info("supplier " + a.getSupplier_id() + " is not free ({} slots are available), token: {}", a.getFreeSlots(), token);
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
