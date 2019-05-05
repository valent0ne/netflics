package it.univaq.disim.netflics.informer.repository;

import it.univaq.disim.netflics.informer.model.Session;
import it.univaq.disim.netflics.informer.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository {

    Session findByToken(String token);
}
