package it.univaq.disim.netflics.auth.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository {

    Boolean findByToken(String token);
}
