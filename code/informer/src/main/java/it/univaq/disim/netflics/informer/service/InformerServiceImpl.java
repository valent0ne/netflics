package it.univaq.disim.netflics.informer.service;

import it.univaq.disim.netflics.clients.auth.*;
import it.univaq.disim.netflics.informer.*;
import it.univaq.disim.netflics.informer.model.Session;
import it.univaq.disim.netflics.informer.model.Movie;
import it.univaq.disim.netflics.informer.model.User;
import it.univaq.disim.netflics.informer.repository.MovieRepository;
import it.univaq.disim.netflics.informer.repository.SessionRepository;
import it.univaq.disim.netflics.informer.repository.UserRepository;
import it.univaq.disim.netflics.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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


    private static Logger LOGGER = LoggerFactory.getLogger(InformerServiceImpl.class);

    @Override
    public MostViewedResponse mostViewed(MostViewedRequest parameters) throws BusinessException {

        MostViewedResponse response = new MostViewedResponse();

        //todo implement

        return response;
    }

    @Override
    public BestMoviesResponse bestMovies(BestMoviesRequest parameters) throws BusinessException {

        BestMoviesResponse response = new BestMoviesResponse();

        //todo implement

        return response;
    }

    @Override
    public LastViewedResponse lastViewed(LastViewedRequest parameters) throws BusinessException {

        //todo fix this


        // check credentials
        if (!auth(parameters.getToken())) {
            GetMovieResponse getMovieResponse = new GetMovieResponse();
            getMovieResponse.setMovie(null);
            getMovieResponse.setResult("unauthorized");
            return getMovieResponse;
        }
        LastViewedResponse response = new LastViewedResponse();

        return response;
    }

    @Override
    public MoviesResponse movies(MoviesRequest parameters) throws BusinessException {

        MoviesResponse response = new MoviesResponse();
        List<it.univaq.disim.netflics.wsdl.Movie> moviesList = new ArrayList<>();
        List<Movie> movies= movieRepository.findAll();
        for(Movie m: movies){
            moviesList.add(convertMovie(m));
        }
        response.getMoviesList().addAll(moviesList);
        response.setResult("ok");
        return response;
    }

    @Override
    public MovieResponse movie(MovieRequest parameters) throws BusinessException {

        MovieResponse response = new MovieResponse();
        String imdbId = parameters.getImdbId();
        Movie m = movieRepository.findOneByImdbId(imdbId);
        response.setMovie(convertMovie(m));
        response.setResult("ok");

        return response;
    }


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
}
