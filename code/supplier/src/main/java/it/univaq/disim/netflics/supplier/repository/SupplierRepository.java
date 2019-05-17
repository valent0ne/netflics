package it.univaq.disim.netflics.supplier.repository;

import it.univaq.disim.netflics.supplier.BusinessException;
import it.univaq.disim.netflics.supplier.model.Supplier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository {

    List<Supplier> findAll() throws BusinessException;

    List<Supplier> findAllByMovieFetching(String imdbId) throws BusinessException;

    List<Supplier> findAllByMovieFetched(String imdbId) throws BusinessException;

    void setAwake() throws BusinessException;

    void setSleep() throws BusinessException;


}
