/*
 *  Created by Lambdaware as part of the course "Software Engineering" @ Hasselt University.
 */

package be.lambdaware.controllers;

import be.lambdaware.response.ErrorMessages;
import be.lambdaware.response.StringMessage;
import be.lambdaware.security.APIAuthentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link AuthController} is the controller that is mapped to /auth and is used to verify credentials.
 * Will be used by mobile application to check initial log-in.
 *
 * @author Hendrik Lievens - 1130921
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    APIAuthentication authentication;

    Logger log = Logger.getLogger(getClass());

    // ===================================================================================
    // GET methods
    // ===================================================================================

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> checkCredentials(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth) {
        log.info("test");
        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(authentication.getAuthenticatedUser(), HttpStatus.OK);
        }
    }
}
