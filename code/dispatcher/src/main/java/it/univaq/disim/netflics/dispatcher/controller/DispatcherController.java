package it.univaq.disim.netflics.dispatcher.controller;


import it.univaq.disim.netflics.dispatcher.BusinessException;
import it.univaq.disim.netflics.dispatcher.model.User;
import it.univaq.disim.netflics.dispatcher.service.DispatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.univaq.disim.netflics.clients.informer.Movie;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;
import java.util.List;


@Controller("dispatcherrestcontroller")
public class DispatcherController {

    private static Logger LOGGER = LoggerFactory.getLogger(it.univaq.disim.netflics.dispatcher.controller.DispatcherController.class);

    @Autowired
    private DispatcherService service;

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
    @Path("/logout")
    public Response logOut(@HeaderParam("Token") String token){

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
    @Path("/movie/bestmovies")
    public Response bestMovies(){

        try{
            List<Movie> bestMovies = service.bestMovies();
            return Response.ok(bestMovies).build();

        }catch (BusinessException e){
            return e.restResponseHandler();
        }
    }


    @GET
    @Produces("application/json")
    @Path("/movie/lastviewed")
    public Response lastViewed(@HeaderParam("Token") String token){

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
    @Path("/movie/stream/{imdbId}")
    public Response getMovieStream(@HeaderParam("Token") String token, @PathParam("imdbId") String imdbId){

        try{
            StreamingOutput streamingOutput = service.getMovieStream(token,imdbId);
            return Response.ok(streamingOutput).build();

        }catch (BusinessException e){
            return e.restResponseHandler();
        }
    }

}
