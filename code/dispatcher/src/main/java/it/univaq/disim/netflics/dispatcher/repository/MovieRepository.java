package it.univaq.disim.netflics.dispatcher.repository;

import it.univaq.disim.netflics.clients.informer.Movie;
import it.univaq.disim.netflics.dispatcher.BusinessException;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository {

    Movie updateViews(Movie movie) throws BusinessException;
}
