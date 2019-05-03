package it.univaq.disim.netflics.dispatcher.controller;


import it.univaq.disim.netflics.dispatcher.BusinessException;
import it.univaq.disim.netflics.dispatcher.model.User;
import it.univaq.disim.netflics.dispatcher.service.DispatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;


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

}
