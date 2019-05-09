package it.univaq.disim.netflics.dispatcher.service;

import it.univaq.disim.netflics.clients.auth.*;
import it.univaq.disim.netflics.clients.informer.*;
import it.univaq.disim.netflics.dispatcher.BusinessException;
import it.univaq.disim.netflics.dispatcher.model.Availability;
import it.univaq.disim.netflics.dispatcher.model.Supplier;
import it.univaq.disim.netflics.dispatcher.model.User;

import it.univaq.disim.netflics.dispatcher.model.UserMovie;
import it.univaq.disim.netflics.dispatcher.repository.*;
import org.apache.cxf.Bus;
import org.apache.cxf.helpers.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


@Service
public class DispatcherServiceImpl implements DispatcherService {

    private static Logger LOGGER = LoggerFactory.getLogger(DispatcherServiceImpl.class);
    private AuthService authService = new AuthService();
    private AuthPT authPT = authService.getAuthPort();
    private InformerService informerService = new InformerService();
    private InformerPT informerPT = informerService.getInformerPort();

    private ConcurrentHashMap<Supplier, Availability> supplierAvailability = new ConcurrentHashMap<>();

    @Value("#{cfg.recentavailabilitythreshold}")
    private int recentAvailabilityThreshold;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private UserMovieRepository userMovieRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private MovieRepository movieRepository;

    /**
     * calls the auth service to check the user's credentials (token)
     * @param token user's token
     * @return true if the token is valid
     */
    private boolean auth(String token) {

        CheckTokenRequest checkTokenRequest = new CheckTokenRequest();
        checkTokenRequest.setToken(token);
        CheckTokenResponse checkTokenResponse = authPT.checkToken(checkTokenRequest);

        return (checkTokenResponse.isResult());
    }


    /**
     * calls the auth service to check if the user is registered
     * @param u the object user
     * @return a token
     */
    public String logIn(User u){

        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setEmail(u.getEmail());
        logInRequest.setPassword(u.getPassword());
        LogInResponse logInResponse = authPT.logIn(logInRequest);

        return (logInResponse.getToken());
    }

    /**
     * calls the auth service to disconnect user from the system
     * @param token is the token associated to the user that made the request
     * @return if the operation goes to good end
     */
    public Boolean logOut(String token){

        LOGGER.info("logout token: {}", token);

        LogOutRequest logOutRequest = new LogOutRequest();
        logOutRequest.setToken(token);
        LogOutResponse logOutResponse = authPT.logOut(logOutRequest);

        return (logOutResponse.isResult());
    }


    /**
     * calls the informer service to return the list of the most viewed movies
     * @return a list of movies
     */
    @Override
    public List<Movie> mostViewed() {

        MostViewedRequest mostViewedRequest = new MostViewedRequest();
        MostViewedResponse mostViewedResponse = informerPT.mostViewed(mostViewedRequest);

        return mostViewedResponse.getMoviesList();
    }


    /**
     * calls the informer service to return the list of most voted movies
     * @return a list of movies
     */
    @Override
    public List<Movie> bestOnes() {

        BestMoviesRequest bestMoviesRequest = new BestMoviesRequest();
        BestMoviesResponse bestMoviesResponse = informerPT.bestMovies(bestMoviesRequest);

        return bestMoviesResponse.getMoviesList();
    }


    /**
     * calls the informer service to return the list of the last viewed movies by a specific user
     * @param token the token associated to the user
     * @return a list of movies
     */
    @Override
    public List<Movie> lastViewed(String token) {

        LOGGER.info("lastviewed token: {}", token);

        LastViewedRequest lastViewedRequest = new LastViewedRequest();
        lastViewedRequest.setToken(token);
        LastViewedResponse lastViewedResponse = informerPT.lastViewed(lastViewedRequest);

        if (!lastViewedResponse.getResult().equals("200")){
            throw new BusinessException(lastViewedResponse.getResult()+"/error while retrieving last viewed movie list");
        }

        return lastViewedResponse.getMoviesList();
    }


    /**
     * calls the informer service to return the list of all the movies
     * @return a list of movies
     */
    @Override
    public List<Movie> movies() {

        MoviesRequest moviesRequest = new MoviesRequest();
        MoviesResponse moviesResponse = informerPT.movies(moviesRequest);

        return moviesResponse.getMoviesList();
    }


    /**
     * calls the informer service to return a specific movie
     * @return a list of movies
     */
    @Override
    public Movie movie(String imdbId) {

        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setImdbId(imdbId);
        MovieResponse movieResponse = informerPT.movie(movieRequest);

        return movieResponse.getMovie();
    }

    /**
     *
     */
    @Override
    public StreamingOutput getMovieStream(String token, String imdbId) throws BusinessException {

        // check credentials
        if (!auth(token)) {
            throw new BusinessException("401/token not valid");
        }

        // retrieve full supplier list
        List<Supplier> allSuppliers = new ArrayList<>();
        try{
            allSuppliers = supplierRepository.findAll();
            if(allSuppliers == null || allSuppliers.size() == 0){
                throw new BusinessException("500/error while retrieving suppliers list");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(e);
        }

        List<Supplier> allSuppliersHavingMovie = new ArrayList<>();
        try{
            allSuppliersHavingMovie = supplierRepository.findAllByMovie(imdbId);
            if(allSuppliersHavingMovie == null || allSuppliersHavingMovie.size() == 0){
                LOGGER.error("500/error while retrieving suppliers list");
            }
        }catch (Exception e){
            e.printStackTrace();
            // continue anyway to send the fetchMOvie() request to the suppliers that do not have the movie
        }


        // execute the following code only if i was able to retrieve the suppliers list
        if(allSuppliersHavingMovie != null){ // the case in which allsuppliersHavingMovie.size()=0 is still allowed
            // compute the list of all the suppliers that do not have the movie
            List<Supplier> allSuppliersNotHavingMovie = new ArrayList<>(allSuppliers);
            allSuppliersNotHavingMovie.removeAll(allSuppliersHavingMovie);

            if(allSuppliersNotHavingMovie.size() > 0){
                // send fetchMovie() to suppliers that do not have the movie
                ExecutorService fetchMovieThreadPool = Executors.newFixedThreadPool(allSuppliersNotHavingMovie.size());
                try{
                    for(Supplier s: allSuppliersNotHavingMovie){
                        fetchMovieThreadPool.submit(() -> sendFetchMovie(s, token, imdbId));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    // try to continue anyway...the following calls to this method will retry to submit the fetchMovie()
                }finally {
                    fetchMovieThreadPool.shutdown();
                    // fetchMovieThreadPool.awaitTermination(5000, TimeUnit.MILLISECONDS);
                }
            }

            // after having sent the requests, if there are no suppliers with the movie then abort
            if(allSuppliersHavingMovie.size() == 0){
                LOGGER.info("the movie is not available on any supplier, try again later");
                throw new BusinessException("404/the requested movie is not available");
            }

        }

        // consider the remaining ones for the polling
        // if the availability from the remaining suppliers is older then 5 seconds or it is missing poll them
        HashMap<Supplier, Availability> recentAvailability = new HashMap<>();
        try{
            recentAvailability = availabilityRepository.findRecent(recentAvailabilityThreshold);
            // clean the concurrent map
            supplierAvailability.keySet().removeAll(recentAvailability.keySet());
            // add the new entries
            supplierAvailability.putAll(recentAvailability);
        }catch (Exception e){
            e.printStackTrace();
            // try to continue anyway
        }

        if(allSuppliersHavingMovie != null){
            // create the list of the suppliers that are going to get polled
            List<Supplier> suppliersToPoll = new ArrayList<>(allSuppliersHavingMovie);
            suppliersToPoll.removeAll(recentAvailability.keySet());

            if(suppliersToPoll.size() > 0){
                // call getAvailability, use the fresh values for the others
                // send fetchMovie() to suppliers that do not have the movie
                try{
                    ExecutorService getAvailabilityThreadPool = Executors.newFixedThreadPool(suppliersToPoll.size());
                    for(Supplier s: suppliersToPoll){
                        getAvailabilityThreadPool.submit(() -> sendGetAvailability(s, token));
                    }
                    getAvailabilityThreadPool.shutdown();
                    getAvailabilityThreadPool.awaitTermination(3000, TimeUnit.MILLISECONDS);

                }catch (Exception e){
                    LOGGER.error("500/error while polling the suppliers, continuing anyway: {}", e.getMessage());
                    // throw new BusinessException("500/error while polling the suppliers");
                    // try to continue anyway...the suppliers will be polled again during the next method invocation
                }
            }

        }

        // choose the least loaded supplier
        Supplier chosenOne = getBestSupplier();

        if(chosenOne == null || chosenOne.getIp() == null || chosenOne.getPort() == null){
            throw new BusinessException("500/error while choosing the best supplier");
        }

        LOGGER.info("starting stream...");

        it.univaq.disim.netflics.dispatcher.model.Movie m = movieRepository.findOneByImdbId(imdbId);

        // add entry to user_movie signaling that this user has seen this movie
        userMovieRepository.save(new UserMovie(null, sessionRepository.findByToken(token).getUserId(), m.getId()));

        // increment movie's views
        movieRepository.updateViews(m);

        // return the stream
        return outputStream -> {

            Client client = ClientBuilder.newClient();
            WebTarget target = client
                    .target("http://"+chosenOne.getIp()+":"+chosenOne.getPort()+"/supplier/api/supplier/"+token+"/movie/"+imdbId);
            InputStream is = target.request(MediaType.APPLICATION_OCTET_STREAM).get(InputStream.class);

            IOUtils.copy(is, outputStream);
            is.close();
            outputStream.close();

        };
    }

    /**
     * sends a post request invoking the fetchMovie on the designated supplier
     * @param s supplier object
     * @param imdbId the movie of interest
     */
    private void sendFetchMovie(Supplier s, String token, String imdbId){

        LOGGER.info("sending fetchMovie to supplier: {} - {}{}, token: {}", s.getId(), s.getIp(), s.getPort(), token);
        Client client = ClientBuilder.newClient();
        WebTarget target = client
                .target("http://"+s.getIp()+":"+s.getPort()+"/supplier/api/supplier/"+token+"/movie/"+imdbId);
        target.request().post(null);
    }

    /**
     * sends a getAvailability request to the supplier, if the response is valid,
     * the availability data is stored in a map
     * @param s supplier object
     */
    private void sendGetAvailability(Supplier s, String token){
        LOGGER.info("sending getAvailability to supplier: {} - {}:{}, token: {}", s.getId(), s.getIp(), s.getPort(), token);

        Availability a;
        supplierAvailability.remove(s);

        try{
            Client client = ClientBuilder.newClient();
            WebTarget target = client
                    .target("http://"+s.getIp()+":"+s.getPort()+"/supplier/api/supplier/"+token+"/availability");
            a = target.request(MediaType.APPLICATION_JSON).get(Availability.class);
        }catch (Exception e){
            //e.printStackTrace();
            LOGGER.error("the supplier "+s.getId()+" seems unavailable: {}", e.getMessage());
            availabilityRepository.setUnavailable(s);
            return;
        }

        if(a != null && a.getAvailable()){
            LOGGER.info("supplier "+a.getSupplier_id()+" is available");
            supplierAvailability.put(s, a);
        }
    }

    /**
     * returns the supplier with lowest load, computed as the average between the cpu and memory load
     * @return the best supplier object
     */
    private Supplier getBestSupplier(){
        Supplier bestOne = new Supplier();
        Double minAverageLoad = 100.0;
        for(Map.Entry<Supplier, Availability> entry: supplierAvailability.entrySet()){
            Double localAverageLoad = (entry.getValue().getCpuSaturation()+entry.getValue().getMemSaturation())/2;
            if (localAverageLoad < minAverageLoad){
                minAverageLoad = localAverageLoad;
                bestOne = entry.getKey();
            }
        }
        LOGGER.info("chosen supplier: {} - {}:{}, load: {}", bestOne.getId(), bestOne.getIp(), bestOne.getPort(), minAverageLoad);
        return bestOne;
    }
}
