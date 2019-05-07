package it.univaq.disim.netflics.auth.repository;

import it.univaq.disim.netflics.auth.BusinessException;
import it.univaq.disim.netflics.auth.model.Session;
import it.univaq.disim.netflics.auth.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository {

    Session findByToken(String token) throws BusinessException;

    Session save(Session s) throws BusinessException;

    void deleteAll(User u) throws BusinessException;

    void delete(String token) throws BusinessException;

    String generateToken()throws BusinessException;

}
