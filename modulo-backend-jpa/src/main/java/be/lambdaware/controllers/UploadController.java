package be.lambdaware.controllers;

import be.lambdaware.response.Responses;
import be.lambdaware.response.StringMessage;
import be.lambdaware.security.APIAuthentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * {@link UploadController} is the controller that is mapped to "/upload" and is used to handle uploads.
 *
 * @author Hendrik Lievens - 1130921
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    APIAuthentication authentication;

    Logger log = Logger.getLogger(getClass());

    // ===================================================================================
    // GET methods
    // ===================================================================================

    //TODO proof of concept method. File uploading works.

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @RequestParam MultipartFile file) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return Responses.LOGIN_INVALID;
        } else if (!authentication.isAdmin()) {
            return Responses.UNAUTHORIZED;
        }

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("Test File")));
                stream.write(bytes);
                stream.close();
                return StringMessage.asEntity("File uploaded.", HttpStatus.OK);
            } catch (Exception e) {
                return StringMessage.asEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return StringMessage.asEntity("File was empty.", HttpStatus.BAD_REQUEST);
        }
    }
}
