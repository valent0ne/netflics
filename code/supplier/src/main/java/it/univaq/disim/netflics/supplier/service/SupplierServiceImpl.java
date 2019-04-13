package it.univaq.disim.netflics.supplier.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;


@Service
public class SupplierServiceImpl implements SupplierService {


	private static Logger LOGGER = LoggerFactory.getLogger(SupplierServiceImpl.class);

	@Value("#{cfg.videopath}")
	private String videopath;

	public File getMovie(String imdbId){

	    String pathToFile = videopath+imdbId;
        File file = null;
	    try{
            file = new File(pathToFile);
            if(!file.exists()){
                throw new FileNotFoundException();
            }
        }catch (FileNotFoundException e){
	        LOGGER.error("the movie is not available on the server");
	        //TODO filenotfound retrieve the movie from vault
        }
        return file;
    }



}
