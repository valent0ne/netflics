package it.univaq.disim.netflics.supplier.service;

import it.univaq.disim.netflics.supplier.model.Availability;

import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.io.OutputStream;

public interface SupplierService {

    StreamingOutput getMovie(String token, String imdbId);

    Availability getAvailability(String token);

    void fetchMovie(String token, String imdbId);

    void awake(String token);

    void sleep(String token);

}
