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


@Controller("supplierrestcontroller")
public class SupplierController {

    private static Logger LOGGER = LoggerFactory
            .getLogger(it.univaq.disim.netflics.supplier.controller.SupplierController.class);

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
    @Path("{token}/movie/{id}")
    public Response getMovie(@PathParam("token") String token, @PathParam("id") String imdbId) {
        LOGGER.info("supplier - getMovie(), token: {}", token);
        ResponseBuilder response;
        StreamingOutput file = service.getMovie(token, imdbId);
        if (file != null) {
            response = Response.ok(file);
            return response
                    .header("Content-Disposition", "attachment;filename=" + imdbId)
                    .build();
        } else {
            return Response
                    .status(Response.Status.SEE_OTHER)
                    .entity("Movie " + imdbId + " cannot be found on this supplier.")
                    .build();
        }

    }

    /**
     * @return the system's % of occupied cpu/memory and load
     */
    @GET
    @Produces("application/json")
    @Path("/{token}/availability")
    public Response getAvailability(@PathParam("token") String token) {
        LOGGER.info("supplier - getAvailability(), token: {}", token);
        try {
            Availability a = service.getAvailability(token);
            // system's info reads could be wrong,
            // see
            // it.univaq.disim.netflics.supplier.service.SupplierServiceImpl.getAvailability()
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
        } catch (BusinessException e) {
            return e.restResponseHandler();
        }

    }


    /**
     * commands this supplier to fetch the movie identified by the imdbId from the
     * vault service
     * @param token auth token
     * @param imdbId movie identifier
     * @return status based on obtained outcome
     */
    @POST
    @Path("/{token}/movie/{id}")
    @Produces("application/json")
    public Response fetchMovie(@PathParam("token") String token, @PathParam("id") String imdbId) {
        LOGGER.info("supplier - fecthMovie(), token: {}", token);
        try {
            new Thread(() -> service.fetchMovie(token, imdbId)).start();
            return Response.status(201).build();
        } catch (BusinessException e) {
            return e.restResponseHandler();
        }
    }


    /**
     * awake this supplier
     * @param token auth token
     * @return status based on obtained outcome
     */
    @POST
    @Path("/{token}/awake")
    public Response awake(@PathParam("token") String token){
        LOGGER.info("supplier - awake(), token: {}", token);
        try{
            service.awake(token);
            return Response.status(200).build();
        }catch (BusinessException e){
            return e.restResponseHandler();
        }
    }

    /**
     * put this supplier to sleep
     * @param token auth token
     * @return status based on obtained outcome
     */
    @POST
    @Path("/{token}/sleep")
    public Response sleep(@PathParam("token") String token){
        LOGGER.info("received sleep command, token: {}", token);
        try{
            service.sleep(token);
            return Response.status(200).build();
        }catch (BusinessException e){
            return e.restResponseHandler();
        }
    }
}
