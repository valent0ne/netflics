package it.univaq.disim.netflics.supplier.repository;

import it.univaq.disim.netflics.supplier.BusinessException;
import it.univaq.disim.netflics.supplier.model.SupplierMovie;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierMovieRepository {

        SupplierMovie save(SupplierMovie supplierMovie) throws BusinessException;

        void delete(SupplierMovie supplierMovie) throws BusinessException;

        SupplierMovie update(SupplierMovie supplierMovie) throws BusinessException;

        void deleteAllBySupplierId(Long supplierId) throws BusinessException;

}
