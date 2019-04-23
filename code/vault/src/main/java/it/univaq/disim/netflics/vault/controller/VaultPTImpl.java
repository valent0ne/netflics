package it.univaq.disim.netflics.vault.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.disim.netflics.vault.VaultPT;
import it.univaq.disim.netflics.vault.GetMovieRequest;
import it.univaq.disim.netflics.vault.GetMovieResponse;
import it.univaq.disim.netflics.vault.AddMovieResponse;
import it.univaq.disim.netflics.vault.AddMovieRequest;
import it.univaq.disim.netflics.vault.service.VaultService;

@Component(value = "VaultPTImpl")
public class VaultPTImpl implements VaultPT {

	private static Logger LOGGER = LoggerFactory.getLogger(VaultPTImpl.class);
	
	@Autowired
	private VaultService service;

	/**
	 * Return the requested movie file by id
	 * @param parameters contains the id of the requested movie
	 * @return the movie itself
	 */
	public GetMovieResponse getMovie(GetMovieRequest parameters) {
        GetMovieResponse response = new GetMovieResponse();
	    try {
			response = service.getMovie(parameters);
			return response;
		} 
		catch (Exception ex) {
			ex.printStackTrace();
            response.setResult("ko");
            response.setMovie(null);
			return response;
		}
	}

	/**
	 * Add the movie that is passed as parameter in the vault,
	 * retrieves the movie data from OpenMovieDB (querying the service by the title)
	 * and uploads them on the database.
	 * @param parameters contain both the title and the movie itself
	 * @return movieResponse flag signaling the outcome
	 */
	public AddMovieResponse addMovie(AddMovieRequest parameters) {
        AddMovieResponse response = new AddMovieResponse();
	    try {
			response = service.addMovie(parameters);
			return response;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			response.setResult("ko");
			return response;
		}
	}
}
