package it.univaq.disim.netflics.auth.repository;

import it.univaq.disim.netflics.auth.model.Session;
import it.univaq.disim.netflics.auth.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository {

    Session findByToken(String token);

    Session save(Session s);

    void deleteAll(User u);

    void delete(String token);

    String generateToken();

}
