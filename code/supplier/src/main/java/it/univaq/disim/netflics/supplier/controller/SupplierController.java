package it.univaq.disim.netflics.supplier.controller;

import it.univaq.disim.netflics.supplier.BusinessException;
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
import javax.ws.rs.core.StreamingOutput;

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
    public Response getMovie(@HeaderParam("Token") String token, @PathParam("id") String imdbId) {
        ResponseBuilder response;
        StreamingOutput file = service.getMovie(token, imdbId);
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
    public Response getAvailability(@HeaderParam("Token") String token) {
        try{
            Availability a = service.getAvailability(token);
            // system's info reads could be wrong,
            // see it.univaq.disim.netflics.supplier.service.SupplierServiceImpl.getAvailability()
            // for more infos
            while (a == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                a = service.getAvailability(token);
            }
            return Response.ok(a).build();
        }catch (BusinessException e){
            return e.restResponseHandler();
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
    public Response fetchMovie(@HeaderParam("Token") String token, @PathParam("id") String imdbId) {
        new Thread(() -> service.fetchMovie(token, imdbId)).start();
        return Response.status(201).build();
    }
}
