package it.univaq.disim.netflics.auth.controller;

import it.univaq.disim.netflics.auth.*;
import it.univaq.disim.netflics.auth.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "AuthPTImpl")
public class AuthPTImpl implements AuthPT {

	private static Logger LOGGER = LoggerFactory.getLogger(it.univaq.disim.netflics.auth.controller.AuthPTImpl.class);
	
	@Autowired
	private AuthService service;

	/**
	 * Checks the credential and if the user is registered in the system
	 * return the info about him.
	 * @param parameters contains email and password
	 * @return logInResponse that contains a boolean (if the user is
	 * registered then it's true, otherwise is false), the role of the
	 * user and the session's token assigned.
	 */
	public LogInResponse logIn(LogInRequest parameters) {
        LogInResponse response = new LogInResponse();
	    try {
			response = service.logIn(parameters);
			return response;
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			response.setResult(false);
			response.setRole(null);
			return response;
		}
	}

	/**
	 * Disconnect the user from the system
	 * @param parameters contains the token associated to the connected user
	 * @return logOutResponse flag signaling the outcome
	 */
	public LogOutResponse logOut(LogOutRequest parameters) {
        LogOutResponse response = new LogOutResponse();
	    try {
			response = service.logOut(parameters);
			return response;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			response.setResult(false);
			return response;
		}
	}


	/**
	 * Check whether the token is valid or not
	 * @param parameters contains the token
	 * @return checkTokenResponse flag signaling the outcome and the role of the token owner
	 */
	public CheckTokenResponse checkToken(CheckTokenRequest parameters){
        CheckTokenResponse response = new CheckTokenResponse();
	    try{
			response = service.checkToken(parameters);
			return response;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			response.setResult(false);
			response.setRole(null);
			return response;
		}
	}
}
