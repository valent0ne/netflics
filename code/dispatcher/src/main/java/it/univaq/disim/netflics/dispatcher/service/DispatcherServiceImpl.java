package it.univaq.disim.netflics.dispatcher.service;

import it.univaq.disim.netflics.clients.auth.*;
import it.univaq.disim.netflics.clients.informer.*;
import it.univaq.disim.netflics.clients.vault.AddMovieRequest;
import it.univaq.disim.netflics.clients.vault.AddMovieResponse;
import it.univaq.disim.netflics.clients.vault.VaultPT;
import it.univaq.disim.netflics.clients.vault.VaultService;
import it.univaq.disim.netflics.dispatcher.BusinessException;
import it.univaq.disim.netflics.dispatcher.model.Availability;
import it.univaq.disim.netflics.dispatcher.model.Supplier;
import it.univaq.disim.netflics.dispatcher.model.User;
import it.univaq.disim.netflics.dispatcher.model.UserMovie;
import it.univaq.disim.netflics.dispatcher.repository.*;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static it.univaq.disim.netflics.dispatcher.service.LoadBalancer.sendFetchMovie;
import static it.univaq.disim.netflics.dispatcher.service.LoadBalancer.sendGetAvailability;


@Service
@SuppressWarnings("Duplicates")
public class DispatcherServiceImpl implements DispatcherService {

    private static Logger LOGGER = LoggerFactory.getLogger(DispatcherServiceImpl.class);

    private AuthService authService = new AuthService();
    private AuthPT authPT = authService.getAuthPort();
    private InformerService informerService = new InformerService();
    private InformerPT informerPT = informerService.getInformerPort();
    private VaultService vaultService = new VaultService();
    private VaultPT vaultPT = vaultService.getVaultPort();

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

    @Value("#{cfg.numsupplierstofetch}")
    private int numSuppliersToFetch;

    @Value("#{cfg.numsupplierstowakeup}")
    private int numSuppliersToWakeUp;

    @Value("#{cfg.freeslotsthreshold}")
    private int freeSlotsThreshold;

    /**
     * calls the auth service to check the user's credentials (token)
     * @param token user's token
     * @param role the role the user should have
     * @return true if the token is valid
     */
    private boolean auth(String token, String role) {

        CheckTokenRequest checkTokenRequest = new CheckTokenRequest();
        checkTokenRequest.setToken(token);
        CheckTokenResponse checkTokenResponse = authPT.checkToken(checkTokenRequest);

        if(role.equals("ANY")){
            return (checkTokenResponse.isResult());

        }else{
            return (checkTokenResponse.isResult() && checkTokenResponse.getRole().equals(role));
        }

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
     * stream function, performs the supplier selection
     * and redirects the supplier stream to the client
     */
    @Override
    public StreamingOutput getMovieStream(String token, String imdbId) throws BusinessException {

        // check credentials
        if (!auth(token, "ANY")) {
            throw new BusinessException("401/unauthorized");
        }

        // check if the movie exists in the system
        it.univaq.disim.netflics.dispatcher.model.Movie m = movieRepository.findOneByImdbId(imdbId);
        if(m == null){
            throw new BusinessException("404/the requested movie does not exists");
        }

        try{
            LoadBalancer lb = new LoadBalancer(availabilityRepository,
                                               supplierRepository,
                                               userMovieRepository,
                                               sessionRepository,
                                               movieRepository,
                                               numSuppliersToFetch,
                                               numSuppliersToWakeUp,
                                               freeSlotsThreshold,
                                               token,
                                               m);

            lb.monitor();
            lb.analyze();
            lb.plan();
            return lb.execute();

        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }


    }

    /**
     * forwards the movie file to the vault and commands the least loaded supplier to fetch it
     * @param token auth token
     * @param movieFile movie file
     * @param imdbId movie unique identifier
     */
    public void addMovie(String token, Attachment movieFile, String imdbId){
        if(!auth(token, "ADMIN")){
            throw new BusinessException("401/unauthorized");
        }
        DataHandler dh = movieFile.getDataHandler();

        AddMovieRequest addMovieRequest = new AddMovieRequest();
        addMovieRequest.setToken(token);
        addMovieRequest.setMovie(dh);
        addMovieRequest.setImdbId(imdbId);
        AddMovieResponse addMovieResponse = vaultPT.addMovie(addMovieRequest);

        if(!addMovieResponse.getResult().equals("200")){
            throw new BusinessException(addMovieResponse.getResult());
        }

        ConcurrentHashMap<Supplier, Availability> map = new ConcurrentHashMap<>();
        List<Supplier> suppliersToPoll;
        suppliersToPoll = supplierRepository.findAll();
        if(suppliersToPoll !=null && suppliersToPoll.size()>0){
            // call getAvailability of all suppliers
            try{
                ExecutorService getAvailabilityThreadPool = Executors.newFixedThreadPool(suppliersToPoll.size());
                for(Supplier s: suppliersToPoll){
                    getAvailabilityThreadPool.submit(() -> sendGetAvailability(s, token, map, freeSlotsThreshold, availabilityRepository));
                }
                getAvailabilityThreadPool.shutdown();
                getAvailabilityThreadPool.awaitTermination(3000, TimeUnit.MILLISECONDS);

            }catch (Exception e){
                LOGGER.warn("500/error while polling the suppliers, continuing anyway: {}", e.getMessage());
            }

            // Choose the supplier that has the higher number of free slots
            Supplier bestOne = new Supplier();
            int maxFreeSlots = -1;
            for(Map.Entry<Supplier, Availability> entry: map.entrySet()){
                int localFreeSlots = (entry.getValue().getFreeSlots());
                if (localFreeSlots > maxFreeSlots){
                    maxFreeSlots = localFreeSlots;
                    bestOne = entry.getKey();
                }
            }
            LOGGER.info("chosen supplier: {} - {}:{}, max free slots: {}", bestOne.getId(), bestOne.getIp(), bestOne.getPort(), maxFreeSlots);

            sendFetchMovie(bestOne, token, imdbId);
            LOGGER.info("sent fatch movie to the best supplier: {}", bestOne.getId());
        }




    }
}
