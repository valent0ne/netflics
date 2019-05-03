package it.univaq.disim.netflics.dispatcher.service;

import it.univaq.disim.netflics.dispatcher.model.User;

public interface DispatcherService {

    String logIn(User u);

    String logOut(String token);

}
