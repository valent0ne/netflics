package it.univaq.disim.netflics.dispatcher.repository;

import it.univaq.disim.netflics.dispatcher.BusinessException;
import it.univaq.disim.netflics.dispatcher.model.Availability;
import it.univaq.disim.netflics.dispatcher.model.Supplier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public interface AvailabilityRepository {

    HashMap<Supplier, Availability> findRecent(int threshold) throws BusinessException;

    void setUnavailable(Supplier s) throws BusinessException;

}
