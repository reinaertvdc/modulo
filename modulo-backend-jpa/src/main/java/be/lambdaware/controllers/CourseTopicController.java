package be.lambdaware.controllers;

import be.lambdaware.dao.CourseTopicDAO;
import be.lambdaware.models.Clazz;
import be.lambdaware.models.CourseTopic;
import be.lambdaware.response.Responses;
import be.lambdaware.security.APIAuthentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jensv on 11-May-16.
 */

@RestController
@RequestMapping("/coursetopic")
@CrossOrigin
public class CourseTopicController {

    private static Logger log = Logger.getLogger(GradeController.class);
    @Autowired
    CourseTopicDAO courseTopicDAO;

    @Autowired
    APIAuthentication authentication;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        List<CourseTopic> classes = courseTopicDAO.findAll();

        if (classes.size() == 0) return Responses.COURSE_TOPICS_NOT_FOUND;
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

}
