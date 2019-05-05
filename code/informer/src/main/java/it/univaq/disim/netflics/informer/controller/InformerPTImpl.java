package it.univaq.disim.netflics.informer.controller;

import it.univaq.disim.netflics.informer.BusinessException;
import it.univaq.disim.netflics.informer.service.InformerService;
import it.univaq.disim.netflics.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "InformerPTImpl")
public class InformerPTImpl implements InformerPT {

	private static Logger LOGGER = LoggerFactory.getLogger(it.univaq.disim.netflics.informer.controller.InformerPTImpl.class);
	
	@Autowired
	private InformerService service;

	/**
	 *The method compute the list of the most viewed movies
	 * @param parameters contains the token associated to the login user
	 * @return the list of the most viewed movies
	 */
	public MostViewedResponse mostViewed(MostViewedRequest parameters) {
        MostViewedResponse response = new MostViewedResponse();
	    try {
			response = service.mostViewed(parameters);
			return response;
		} 
		catch (BusinessException ex) {
			response.setResult(ex.getMessage());
			return response;
		}
	}

	/**
	*The method compute the list of the higer rating movies
	* @param parameters contains the token associated to the login user
	* @return the list of the higer rating movies
	*/

	public BestMoviesResponse bestMovies(BestMoviesRequest parameters) {
        BestMoviesResponse response = new BestMoviesResponse();
	    try {
			response = service.bestMovies(parameters);
			return response;
		}
		catch (BusinessException ex) {
			response.setResult(ex.getMessage());
			return response;
		}
	}


	/**
	*The method compute the list of the last viewed movies
	* @param parameters contains the token associated to the login user
	* @return the list of the last viewed movies
	*/
	public LastViewedResponse lastViewed(LastViewedRequest parameters){
        LastViewedResponse response = new LastViewedResponse();
	    try{
			response = service.lastViewed(parameters);
			return response;
		}
		catch (BusinessException ex) {
			response.setResult(ex.getMessage());
			return response;
		}
	}


	/**
	*The method compute the list of all the movies
	* @param parameters contains the token associated to the login user
	* @return the list of all movies
	*/
	public MoviesResponse movies(MoviesRequest parameters){
        MoviesResponse response = new MoviesResponse();
	    try{
			response = service.movies(parameters);
			return response;
		}
		catch (BusinessException ex) {
			response.setResult(ex.getMessage());
			return response;
		}
	}

	/**
	*The method find the indicated movie
	* @param parameters contains the token associated to the login user and the imdb_id associated to the movie
	* @return the movie
	*/
	public MovieResponse movie(MovieRequest parameters){
        MovieResponse response = new MovieResponse();
	    try{
			response = service.movie(parameters);
			return response;
		}
		catch (BusinessException ex) {
			response.setResult(ex.getMessage());
			return response;
		}
	}
}
