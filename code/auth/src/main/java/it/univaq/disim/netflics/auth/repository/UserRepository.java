package it.univaq.disim.netflics.auth.repository;

import it.univaq.disim.netflics.auth.BusinessException;
import it.univaq.disim.netflics.auth.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    User findOne(User user) throws BusinessException;

    User findOneById (Long id) throws BusinessException;

}
