package it.univaq.disim.netflics.informer.service;

import it.univaq.disim.netflics.clients.auth.AuthPT;
import it.univaq.disim.netflics.clients.auth.AuthService;
import it.univaq.disim.netflics.clients.auth.CheckTokenRequest;
import it.univaq.disim.netflics.clients.auth.CheckTokenResponse;
import it.univaq.disim.netflics.informer.*;
import it.univaq.disim.netflics.informer.model.Movie;
import it.univaq.disim.netflics.informer.model.Session;
import it.univaq.disim.netflics.informer.model.User;
import it.univaq.disim.netflics.informer.repository.MovieRepository;
import it.univaq.disim.netflics.informer.repository.SessionRepository;
import it.univaq.disim.netflics.informer.repository.UserRepository;
import it.univaq.disim.netflics.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InformerServiceImpl implements InformerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Value("#{cfg.movieslistlimit}")
    private int limit;


    private static Logger LOGGER = LoggerFactory.getLogger(InformerServiceImpl.class);

    @Override
    public MostViewedResponse mostViewed(MostViewedRequest parameters) throws BusinessException {

        MostViewedResponse response = new MostViewedResponse();

        List<Movie> movies = movieRepository.mostViewed(limit);
        List<it.univaq.disim.netflics.wsdl.Movie> moviesList = convertMovie(movies);

        response.setResult("200");
        response.getMoviesList().addAll(moviesList);

        return response;
    }

    @Override
    public BestMoviesResponse bestMovies(BestMoviesRequest parameters) throws BusinessException {

        BestMoviesResponse response = new BestMoviesResponse();

        List<Movie> movies = movieRepository.bestOnes(limit);
        List<it.univaq.disim.netflics.wsdl.Movie> moviesList = convertMovie(movies);

        response.setResult("200");
        response.getMoviesList().addAll(moviesList);

        return response;
    }

    @Override
    public LastViewedResponse lastViewed(LastViewedRequest parameters) throws BusinessException {

        // check credentials
        if (!auth(parameters.getToken())) {
            LastViewedResponse lastViewedResponse = new LastViewedResponse();
            lastViewedResponse.setResult("301");
            return lastViewedResponse;
        }
        LastViewedResponse response = new LastViewedResponse();

        Session s = sessionRepository.findByToken(parameters.getToken());
        User u = userRepository.findOneById(s.getUserId());
        List<Movie> movies = movieRepository.lastViewed(u, limit);
        List<it.univaq.disim.netflics.wsdl.Movie> moviesList = convertMovie(movies);

        response.setResult("200");
        response.getMoviesList().addAll(moviesList);

        return response;
    }

    @Override
    public MoviesResponse movies(MoviesRequest parameters) throws BusinessException {

        MoviesResponse response = new MoviesResponse();
        List<Movie> movies = movieRepository.findAll();
        List<it.univaq.disim.netflics.wsdl.Movie> moviesList = convertMovie(movies);

        response.getMoviesList().addAll(moviesList);
        response.setResult("200");
        return response;
    }

    @Override
    public MovieResponse movie(MovieRequest parameters) throws BusinessException {

        MovieResponse response = new MovieResponse();
        String imdbId = parameters.getImdbId();
        Movie m = movieRepository.findOneByImdbId(imdbId);
        response.setMovie(convertMovie(m));
        response.setResult("200");

        return response;
    }

    /**
     * converts a Movie (model) object to a Movie (wsdl response) object
     * @param m input movie to convert
     * @return the wsdl movie object
     */
    private it.univaq.disim.netflics.wsdl.Movie convertMovie(Movie m){
        it.univaq.disim.netflics.wsdl.Movie movie = new it.univaq.disim.netflics.wsdl.Movie();
        movie.setTitle(m.getTitle());
        movie.setDirectors(m.getDirectors());
        movie.setGenres(m.getGenres());
        movie.setImdbId(m.getImdbId());
        movie.setPoster(m.getPoster());
        movie.setRating(m.getRating());
        return movie;
    }

    /**
     * converts a list of Movie (model) objects to a list of Movie (wsdl response) objects
     * @param movies input movie list to convert
     * @return the wsdl movie list object
     */
    private List<it.univaq.disim.netflics.wsdl.Movie> convertMovie(List<Movie> movies){
        List<it.univaq.disim.netflics.wsdl.Movie> converted = new ArrayList<>();
        for(Movie m : movies){
            it.univaq.disim.netflics.wsdl.Movie cm = convertMovie(m);
            converted.add(cm);
        }

        return converted;
    }

    /**
     * calls the auth service to check the user's credentials (token)
     *
     * @param token user's token
     * @return true if the token is valid
     */
    private boolean auth(String token) {
        AuthService authService = new AuthService();
        AuthPT authPT = authService.getAuthPort();
        CheckTokenRequest checkTokenRequest = new CheckTokenRequest();
        checkTokenRequest.setToken(token);
        CheckTokenResponse checkTokenResponse = authPT.checkToken(checkTokenRequest);

        return (checkTokenResponse.isResult());
    }
}
