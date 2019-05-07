package it.univaq.disim.netflics.supplier.repository;

import it.univaq.disim.netflics.supplier.BusinessException;
import it.univaq.disim.netflics.supplier.model.Movie;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository {

    Movie save(Movie movie) throws BusinessException;

    Movie update(Movie movie) throws BusinessException;

    Movie findOneByImdbId(String ImdbId) throws BusinessException;

    void deleteByImdbId(String imsbId) throws BusinessException;

}
