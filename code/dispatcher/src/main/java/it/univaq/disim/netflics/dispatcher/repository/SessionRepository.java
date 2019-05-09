package it.univaq.disim.netflics.dispatcher.repository;

import it.univaq.disim.netflics.dispatcher.BusinessException;
import it.univaq.disim.netflics.dispatcher.model.Session;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository {

    Session findByToken(String token) throws BusinessException;


}
