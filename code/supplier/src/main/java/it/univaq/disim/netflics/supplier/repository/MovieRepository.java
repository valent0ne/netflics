package it.univaq.disim.netflics.supplier.repository;

import it.univaq.disim.netflics.supplier.model.Movie;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository {

    Movie findOneByImdbId(String imdbId);
}
