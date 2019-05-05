package it.univaq.disim.netflics.informer.repository;

import it.univaq.disim.netflics.informer.model.Movie;
import it.univaq.disim.netflics.informer.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository {

    List<Movie> findAll();

    Movie findOneByImdbId(String ImdbId);

    List<Movie> mostViewed(int limit);

    List<Movie> bestOnes(int limit);

    List<Movie> lastViewed(User u, int limit);
}
