package it.univaq.disim.netflics.vault.service;

import it.univaq.disim.netflics.vault.*;

public interface VaultService {

	GetMovieResponse getMovie(GetMovieRequest movie_id) throws BusinessException;

	AddMovieResponse addMovie(AddMovieRequest parameters) throws BusinessException;

}
