package it.univaq.disim.netflics.dispatcher.repository;

import it.univaq.disim.netflics.dispatcher.model.Supplier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository {

    List<Supplier> findAll();

    List<Supplier> findAllByMovie(String imdbId);

}
