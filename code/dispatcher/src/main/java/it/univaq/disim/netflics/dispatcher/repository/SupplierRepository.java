package it.univaq.disim.netflics.dispatcher.repository;

import it.univaq.disim.netflics.dispatcher.BusinessException;
import it.univaq.disim.netflics.dispatcher.model.Supplier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository {

    List<Supplier> findAll() throws BusinessException;

    List<Supplier> findAllByMovieFetching(String imdbId) throws BusinessException;

    List<Supplier> findAllByMovieFetched(String imdbId) throws BusinessException;

    List<Supplier> findAllByStatus(String status) throws BusinessException;


}
