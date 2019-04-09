package it.univaq.disim.netflics.auth.controller;

import it.univaq.disim.netflics.auth.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "AuthPTImpl")
public class AuthPTImpl implements AuthPT {

	private static Logger LOGGER = LoggerFactory.getLogger(it.univaq.disim.netflics.auth.controller.AuthPTImpl.class);
	
	@Autowired
	private it.univaq.disim.netflics.auth.service.AuthService service;

	/**
	 * Checks the credential and if the user is registered in the system
	 * return the info about him.
	 * @param parameters contains email and password
	 * @return logInResponse that contains a boolean (if the user is
	 * registered then it's true, otherwise is false), the role of the
	 * user and the session's token assigned.
	 */
	public LogInResponse logIn(LogInRequest parameters) {
		try {
			LogInResponse response = service.logIn(parameters);
			return response;
		} 
		catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * Disconnect the user from the system
	 * @param parameters contains the token associated to the connected user
	 * @return logOutResponse  flag signaling the outcome
	 */
	public LogOutResponse logOut(LogOutRequest parameters) {
		try {
			LogOutResponse response = service.logOut(parameters);
			return response;
		}
		catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}


	/**
	 * Check whether the token is valid or not
	 * @param parameters contains the token
	 * @return checkTokenResponse flag signaling the outcome and the role of the token owner
	 */
	public CheckTokenResponse checkToken(CheckTokenRequest parameters){
		try{
			CheckTokenResponse response = service.checkToken(parameters);
			return response;
		}
		catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
}
