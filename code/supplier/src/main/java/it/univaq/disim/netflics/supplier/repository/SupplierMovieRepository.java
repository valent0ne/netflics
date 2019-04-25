package it.univaq.disim.netflics.supplier.repository;

import it.univaq.disim.netflics.supplier.model.SupplierMovie;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierMovieRepository {

        SupplierMovie save(SupplierMovie supplierMovie);

        void delete(SupplierMovie supplierMovie);

        SupplierMovie update(SupplierMovie supplierMovie);

}
