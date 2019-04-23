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
public class SupplierController {

    private static Logger LOGGER = LoggerFactory.getLogger(it.univaq.disim.netflics.supplier.controller.SupplierController.class);

    @Autowired
    private SupplierService service;

    /**
     * returns the requested movie
     *
     * @param imdbId the movie identifier
     * @return the movie as a bytestream
     */
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/movie/{id}")
    public Response getMovie(@PathParam("id") String imdbId) {
        ResponseBuilder response;
        File file = service.getMovie(imdbId);
        if (file != null) {
            response = Response.ok(file);
            response.header("Content-Disposition", "attachment;filename=" + imdbId);
        }else{
            response = Response.status(Response.Status.SEE_OTHER).entity("Movie "+imdbId+" cannot be found on this supplier.");
        }
        return response.build();

    }


    /**
     * @return the system's % of occupied cpu and memory
     */
    @GET
    @Produces("application/json")
    @Path("/availability")
    public Response getAvailability() {
        try{
            Availability a = service.getAvailability();
            // system's info reads could be wrong,
            // see it.univaq.disim.netflics.supplier.service.SupplierServiceImpl.getAvailability()
            // for more infos
            while (a == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                a = service.getAvailability();
            }
            return Response.ok(a).build();
        }catch (Exception e){
            e.printStackTrace();
            ResponseBuilder response;
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong: "+e.getMessage());
            return response.build();
        }

    }


    /**
     * commands this supplier to fetch the movie identified by the imdbId from the vault service
     *
     * @param imdbId the movie identifier
     */
    @POST
    @Path("/movie/{id}")
    @Produces("application/json")
    public Response fetchMovie(@PathParam("id") String imdbId) {
        try{
            service.fetchMovie(imdbId);
            return Response.ok().build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
