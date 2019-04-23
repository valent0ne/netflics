package it.univaq.disim.netflics.supplier.service;

import it.univaq.disim.netflics.supplier.model.Availability;

import java.io.File;

public interface SupplierService {

    File getMovie(String imdbId);

    Availability getAvailability();

    void fetchMovie(String imdbId);

}
