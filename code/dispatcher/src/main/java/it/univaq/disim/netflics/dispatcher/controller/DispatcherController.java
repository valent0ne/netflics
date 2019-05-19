package it.univaq.disim.netflics.dispatcher.controller;


import it.univaq.disim.netflics.dispatcher.BusinessException;
import it.univaq.disim.netflics.dispatcher.model.User;
import it.univaq.disim.netflics.dispatcher.service.DispatcherService;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.univaq.disim.netflics.clients.informer.Movie;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Controller("dispatcherrestcontroller")
public class DispatcherController {

    private static Logger LOGGER = LoggerFactory.getLogger(it.univaq.disim.netflics.dispatcher.controller.DispatcherController.class);

    @Autowired
    private DispatcherService service;

    @Value("#{cfg.retrythreshold}")
    private int retrythreshold;

    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/{token}/movie/{imdbId}")
    public Response addMovie(@PathParam("token") String token, @PathParam("imdbId") String imdbId, @Multipart("file") Attachment uploadedInputStream){
        try{
            service.addMovie(token, uploadedInputStream, imdbId);
            return Response.ok().build();
        }catch (BusinessException e){
            return e.restResponseHandler();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/login")
    public Response logIn(@RequestBody User u){

        try{
            String token = service.logIn(u);
            return Response.ok(token).build();

        }catch (BusinessException e){
            return e.restResponseHandler();
        }
    }


    @POST
    @Produces("application/json")
    @Path("/{token}/logout")
    public Response logOut(@PathParam("token") String token){

        try{
            boolean outcome = service.logOut(token);
            return Response.ok(outcome).build();

        }catch (BusinessException e){
            return e.restResponseHandler();
        }
    }


    @GET
    @Produces("application/json")
    @Path("/movie/mostviewed")
    public Response mostViewed(){

        try{
            List<Movie> mostViewed = service.mostViewed();
            return Response.ok(mostViewed).build();

        }catch (BusinessException e){
            return e.restResponseHandler();
        }
    }


    @GET
    @Produces("application/json")
    @Path("/movie/bestones")
    public Response bestOnes(){

        try{
            List<Movie> bestMovies = service.bestOnes();
            return Response.ok(bestMovies).build();

        }catch (BusinessException e){
            return e.restResponseHandler();
        }
    }


    @GET
    @Produces("application/json")
    @Path("/{token}/movie/lastviewed")
    public Response lastViewed(@PathParam("token") String token){

        try{
            List<Movie> lastViewed = service.lastViewed(token);
            return Response.ok(lastViewed).build();

        }catch (BusinessException e){
            return e.restResponseHandler();
        }
    }


    @GET
    @Produces("application/json")
    @Path("/movie/all")
    public Response movies(){

        try{
            List<Movie> movies = service.movies();
            return Response.ok(movies).build();

        }catch (BusinessException e){
            return e.restResponseHandler();
        }
    }

    @GET
    @Produces("application/json")
    @Path("/movie/{imdbId}")
    public Response movie(@PathParam("imdbId") String imdbId){

        try{
            Movie movie = service.movie(imdbId);
            return Response.ok(movie).build();

        }catch (BusinessException e){
            return e.restResponseHandler();
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/{token}/movie/stream/{imdbId}")
    public Response getMovieStream(@PathParam("token") String token, @PathParam("imdbId") String imdbId){

        int retrycount=0;

        while(true){

            try{
                StreamingOutput streamingOutput = service.getMovieStream(token,imdbId);
                return Response.ok(streamingOutput).build();

            }catch (BusinessException e){

                // if the movie is not available on any supplier
                if(e.getMessage().startsWith("503") && retrycount < retrythreshold){
                    retrycount++;
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    }catch (Exception ignored){
                    }
                    LOGGER.warn("{}, retrying {}/{}..., token {}", e.getMessage(), retrycount, retrythreshold, token);
                    continue;
                }
                return e.restResponseHandler();
            }
        }
    }

}
