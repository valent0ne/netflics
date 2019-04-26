package it.univaq.disim.netflics.informer.repository;

import it.univaq.disim.netflics.informer.model.Movie;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository {

    List<Movie> findAll();

    Movie findOneByImdbId(String ImdbId);


}
