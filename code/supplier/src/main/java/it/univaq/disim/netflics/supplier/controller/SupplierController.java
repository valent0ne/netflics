package it.univaq.disim.netflics.supplier.controller;

import it.univaq.disim.netflics.supplier.model.Availability;
import it.univaq.disim.netflics.supplier.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;

import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import java.io.File;

@Controller("supplierrestcontroller")
public class SupplierController{

	private static Logger LOGGER = LoggerFactory.getLogger(it.univaq.disim.netflics.supplier.controller.SupplierController.class);
	
	@Autowired
	private SupplierService service;

    /**
     * returns the requested movie
     * @param imdbId the movie identifier
     * @return the movie as a bytestream
     */
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/movie/{id}")
    public Response getMovie(@PathParam("id") String imdbId) {
            File file = service.getMovie(imdbId);
            ResponseBuilder response = Response.ok(file);
            response.header("Content-Disposition", "attachment;filename=" + file);
            return response.build();
    }


    /**
     * @return the system's % of occupied cpu and memory
     */
    @GET
    @Produces("application/json")
    @Path("/availability")
    public Availability getAvailability() {
        return service.getAvailability();
    }


    /**
     * commands this supplier to fetch the movie identified by the imdbId from the vault service
     * @param imdbId the movie identifier
     */
    @POST
    @Path("/movie/{id}")
    public void fetchMovie(@PathParam("id") String imdbId) {
        service.fetchMovie(imdbId);
    }
}
