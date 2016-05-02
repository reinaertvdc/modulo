package be.lambdaware.controllers;

import be.lambdaware.dao.CertificateDAO;
import be.lambdaware.dao.ClassDAO;
import be.lambdaware.models.Certificate;
import be.lambdaware.models.Clazz;
import be.lambdaware.response.ErrorMessages;
import be.lambdaware.response.StringMessage;
import be.lambdaware.security.APIAuthentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This file is part of the project modulobackend.
 * <p>
 * Author: Hendrik
 * Date: 2/05/16
 */
@RestController
@RequestMapping("/class")
@CrossOrigin
public class ClassController {

    @Autowired
    ClassDAO classDAO;

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

        List<Clazz> classes = classDAO.findAll();

        if (classes.size() == 0) {
            return StringMessage.asEntity("No classes found.", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(classes, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,@PathVariable long id) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        Clazz clazz = classDAO.findById(id);

        if (clazz == null) {
            return StringMessage.asEntity(String.format("No class with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(clazz, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/id/{id}/teacher", method = RequestMethod.GET)
    public ResponseEntity<?> getTeacherFromClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,@PathVariable long id) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        Clazz clazz = classDAO.findById(id);

        if (clazz == null) {
            return StringMessage.asEntity(String.format("No class with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else {
            if(clazz.getTeacher()==null){
                return StringMessage.asEntity(String.format("Class with ID=%d has no teacher.", id), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(clazz.getTeacher(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/id/{id}/students", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentsFromClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,@PathVariable long id) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        Clazz clazz = classDAO.findById(id);

        if (clazz == null) {
            return StringMessage.asEntity(String.format("No class with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else {
            if(clazz.getStudents().size()==0){
                return StringMessage.asEntity(String.format("Class with ID=%d has no students.", id), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(clazz.getStudents(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/id/{id}/certificate", method = RequestMethod.GET)
    public ResponseEntity<?> getCertificateFromClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,@PathVariable long id) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        Clazz clazz = classDAO.findById(id);

        if (clazz == null) {
            return StringMessage.asEntity(String.format("No class with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else {
            if(clazz.getCertificate()==null){
                return StringMessage.asEntity(String.format("Class with ID=%d has no certificate.", id), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(clazz.getCertificate(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/id/{id}/grade", method = RequestMethod.GET)
    public ResponseEntity<?> getGradeFromClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,@PathVariable long id) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        Clazz clazz = classDAO.findById(id);

        if (clazz == null) {
            return StringMessage.asEntity(String.format("No class with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else {
            if(clazz.getGrade()==null){
                return StringMessage.asEntity(String.format("Class with ID=%d has no grade.", id), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(clazz.getGrade(), HttpStatus.OK);
        }
    }
}
