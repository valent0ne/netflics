package it.univaq.disim.netflics.informer.repository;

import it.univaq.disim.netflics.informer.BusinessException;
import it.univaq.disim.netflics.informer.model.Movie;
import it.univaq.disim.netflics.informer.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository {

    List<Movie> findAll() throws BusinessException;

    Movie findOneByImdbId(String ImdbId) throws BusinessException;

    List<Movie> mostViewed(int limit) throws BusinessException;

    List<Movie> bestOnes(int limit) throws BusinessException;

    List<Movie> lastViewed(User u, int limit) throws BusinessException;
}
