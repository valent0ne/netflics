package it.univaq.disim.netflics.dispatcher.service;

import it.univaq.disim.netflics.dispatcher.model.User;
import it.univaq.disim.netflics.clients.informer.Movie;

import javax.ws.rs.core.StreamingOutput;
import java.util.List;

public interface DispatcherService {

    String logIn(User u);

    Boolean logOut(String token);

    List<Movie> mostViewed();

    List<Movie> bestOnes();

    List<Movie> lastViewed(String token);

    List<Movie> movies();

    Movie movie(String imdbId);

    StreamingOutput getMovieStream(String token, String ImdbId);






}
