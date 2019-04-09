package it.univaq.disim.netflics.auth.service;


import it.univaq.disim.netflics.auth.*;

public interface AuthService {

	LogInResponse logIn(LogInRequest parameters) throws BusinessException;

	LogOutResponse logOut(LogOutRequest parameters) throws BusinessException;

	CheckTokenResponse checkToken(CheckTokenRequest parameters) throws BusinessException;

}
