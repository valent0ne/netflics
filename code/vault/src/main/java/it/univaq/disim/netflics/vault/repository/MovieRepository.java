package it.univaq.disim.netflics.vault.repository;

import it.univaq.disim.netflics.vault.BusinessException;
import it.univaq.disim.netflics.vault.model.Movie;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository {

    Movie save(Movie movie) throws BusinessException;

    Movie update(Movie movie) throws BusinessException;

    Movie findOneByImdbId(String ImdbId) throws BusinessException;

    void deleteByImdbId(String imsbId) throws BusinessException;

}
