package it.univaq.disim.netflics.supplier.controller;

import it.univaq.disim.netflics.supplier.service.SupplierService;
import org.apache.cxf.helpers.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import java.io.File;
import java.io.InputStream;

@Controller("supplierrestcontroller")
public class SupplierController{

	private static Logger LOGGER = LoggerFactory.getLogger(it.univaq.disim.netflics.supplier.controller.SupplierController.class);
	
	@Autowired
	private SupplierService service;

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/movie/{id}")
    public Response getMovie(@PathParam("id") String imdbId) {

            File file = service.getMovie(imdbId);
            ResponseBuilder response = Response.ok((Object) file);
            response.header("Content-Disposition", "attachment;filename=" + file);
            return response.build();
    }



}
