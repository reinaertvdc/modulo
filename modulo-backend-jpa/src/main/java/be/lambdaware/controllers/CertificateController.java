package be.lambdaware.controllers;

import be.lambdaware.dao.CertificateDAO;
import be.lambdaware.models.Certificate;
import be.lambdaware.response.ErrorMessages;
import be.lambdaware.response.StringMessage;
import be.lambdaware.security.APIAuthentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/certificate")
@CrossOrigin
public class CertificateController {

    @Autowired
    CertificateDAO certificateDAO;

    @Autowired
    APIAuthentication authentication;

    Logger log = Logger.getLogger(getClass());

    // ===================================================================================
    // GET methods
    // ===================================================================================

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        List<Certificate> certificates = certificateDAO.findAll();

        if (certificates.size() == 0) {
            return StringMessage.asEntity("No certificates found.", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(certificates, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,@PathVariable long id) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        Certificate certificate = certificateDAO.findById(id);

        if (certificate == null) {
            return StringMessage.asEntity(String.format("No certificate with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(certificate, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/id/{id}/name", method = RequestMethod.GET)
    public ResponseEntity<?> getNameFromCertificate(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,@PathVariable long id) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        Certificate certificate = certificateDAO.findById(id);

        if (certificate == null) {
            return StringMessage.asEntity(String.format("No certificate with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else {
            return StringMessage.asEntity(certificate.getName(),HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/id/{id}/enabled", method = RequestMethod.GET)
    public ResponseEntity<?> getEnabledFromCertificate(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,@PathVariable long id) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        Certificate certificate = certificateDAO.findById(id);

        if (certificate == null) {
            return StringMessage.asEntity(String.format("No certificate with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else {
            return StringMessage.asEntity(String.valueOf(certificate.isEnabled()),HttpStatus.OK);
        }
    }

    // ===================================================================================
    // PUT methods
    // ===================================================================================

    @RequestMapping(value = "/id/{id}/enable", method = RequestMethod.PUT)
    public ResponseEntity<?> enabledCertificate(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,@PathVariable long id) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        Certificate certificate = certificateDAO.findById(id);

        if (certificate == null) {
            return StringMessage.asEntity(String.format("No certificate with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else {
            certificate.setEnabled(true);
            certificateDAO.save(certificate);
            return StringMessage.asEntity(String.format("Certificate with ID=%d is enabled.", id), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/id/{id}/disable", method = RequestMethod.PUT)
    public ResponseEntity<?> disableCertificate(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,@PathVariable long id) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        Certificate certificate = certificateDAO.findById(id);

        if (certificate == null) {
            return StringMessage.asEntity(String.format("No certificate with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else {
            certificate.setEnabled(false);
            certificateDAO.save(certificate);
            return StringMessage.asEntity(String.format("Certificate with ID=%d is disabled.", id), HttpStatus.OK);
        }
    }

    // ===================================================================================
    // Delete methods
    // ===================================================================================

    @RequestMapping(value = "/id/{id}/disable", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCertificate(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,@PathVariable long id) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        if (!certificateDAO.exists(id)) {
            return StringMessage.asEntity(String.format("No certificate with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else {
            certificateDAO.delete(id);
            return StringMessage.asEntity(String.format("Certificate with ID=%d is disabled.", id), HttpStatus.OK);
        }
    }

}
