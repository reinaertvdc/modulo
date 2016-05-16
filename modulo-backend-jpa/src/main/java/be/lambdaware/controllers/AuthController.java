package be.lambdaware.controllers;

import be.lambdaware.response.Responses;
import be.lambdaware.security.APIAuthentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * {@link AuthController} is the controller that is mapped to /auth and is used to verify credentials.
 * Will be used by mobile application to check initial log-in.
 *
 * @author Hendrik Lievens - 1130921
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    APIAuthentication authentication;

    Logger log = Logger.getLogger(getClass());

    // ===================================================================================
    // GET methods
    // ===================================================================================

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> checkCredentials(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth) {
        log.info("Getting authentication from "+auth);
        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        return new ResponseEntity<>(authentication.getAuthenticatedUser(), HttpStatus.OK);
    }
}
