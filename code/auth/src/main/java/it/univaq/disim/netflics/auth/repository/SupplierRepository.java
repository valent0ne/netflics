package it.univaq.disim.netflics.auth.repository;

import it.univaq.disim.netflics.auth.BusinessException;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository {

    Boolean findByToken(String token) throws BusinessException;
}
