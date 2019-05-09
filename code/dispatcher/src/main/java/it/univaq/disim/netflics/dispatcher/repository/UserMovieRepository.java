package it.univaq.disim.netflics.dispatcher.repository;

import it.univaq.disim.netflics.dispatcher.BusinessException;
import it.univaq.disim.netflics.dispatcher.model.UserMovie;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMovieRepository {

    UserMovie save(UserMovie um) throws BusinessException;
}
