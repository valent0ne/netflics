package it.univaq.disim.netflics.informer.repository;

import it.univaq.disim.netflics.informer.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    User findOneById (Long id);

}
