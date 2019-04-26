package it.univaq.disim.netflics.informer.service;


import it.univaq.disim.netflics.informer.*;
import it.univaq.disim.netflics.wsdl.*;

public interface InformerService {

	MostViewedResponse mostViewed(MostViewedRequest parameters) throws BusinessException;

	BestMoviesResponse bestMovies(BestMoviesRequest parameters) throws BusinessException;

	LastViewedResponse lastViewed(LastViewedRequest parameters) throws BusinessException;

	MoviesResponse movies(MoviesRequest parameters) throws BusinessException;

	MovieResponse movie(MovieRequest parameters) throws BusinessException;

}
