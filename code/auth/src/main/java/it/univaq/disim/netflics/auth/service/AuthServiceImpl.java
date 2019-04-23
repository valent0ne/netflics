package it.univaq.disim.netflics.auth.service;

import it.univaq.disim.netflics.auth.*;
import it.univaq.disim.netflics.auth.model.Session;
import it.univaq.disim.netflics.auth.model.User;
import it.univaq.disim.netflics.auth.repository.SessionRepository;
import it.univaq.disim.netflics.auth.repository.SupplierRepository;
import it.univaq.disim.netflics.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    private static Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public LogInResponse logIn(LogInRequest parameters) throws BusinessException {

        LogInResponse response = new LogInResponse();
        response.setResult(false);
        response.setRole(null);
        response.setToken(null);

        String email = parameters.getEmail();
        String password = parameters.getPassword();

        LOGGER.info("received email: {}, password: {}", email, password);

        User user = new User();

        user.setEmail(email);
        user.setPassword(password);

        User u = userRepository.findOne(user);
        if (u != null) {

            // clean old sessions
            sessionRepository.deleteAll(u);
            // generate new token
            String token = sessionRepository.generateToken();

            // create new session
            Session s = new Session();
            s.setUserId(u.getId());
            s.setToken(token);

            // insert new session into db
            sessionRepository.save(s);

            // build response
            response.setResult(true);
            response.setToken(token);
            response.setRole(u.getRole());
        }
        return response;
    }

    @Override
    public LogOutResponse logOut(LogOutRequest parameters) throws BusinessException {

        LogOutResponse response = new LogOutResponse();
        String token = parameters.getToken();
        sessionRepository.delete(token);
        response.setResult(true);

        return response;
    }

    @Override
    public CheckTokenResponse checkToken(CheckTokenRequest parameters) throws BusinessException {

        CheckTokenResponse response = new CheckTokenResponse();
        response.setResult(false);
        response.setRole(null);

        // check if the token is valid and it belongs to an user
        Session s = sessionRepository.findByToken(parameters.getToken());

        // if the token belongs to an user
        if (s != null && !s.getToken().isEmpty()) {

            User validatedUser = userRepository.findOneById(s.getUserId());

            if (validatedUser != null) {
                response.setResult(true);
                response.setRole(validatedUser.getRole());
            }
        // the token does not belong to an user, it may belong to a supplier
        } else {
            // check that
            if (supplierRepository.findByToken(parameters.getToken())){
                response.setResult(true);
                response.setRole("ADMIN");
            }
        }

        return response;

    }

}
