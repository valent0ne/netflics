package it.univaq.disim.netflics.supplier.repository;

import it.univaq.disim.netflics.supplier.model.Availability;
import org.springframework.stereotype.Repository;


@Repository
public interface AvailabilityRepository {

        Availability save (Availability availability);

}
