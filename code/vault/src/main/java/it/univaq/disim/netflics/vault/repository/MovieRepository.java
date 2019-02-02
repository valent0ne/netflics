package it.univaq.disim.netflics.vault.repository;

import it.univaq.disim.netflics.vault.model.Movie;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository {

    <M extends Movie> M save(M movie);

}
