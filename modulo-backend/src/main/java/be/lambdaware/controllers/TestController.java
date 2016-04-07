package be.lambdaware.controllers;

import be.lambdaware.dao.ClassesDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.entities.ClassEntity;
import be.lambdaware.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hendrik on 05/04/16.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ClassesDAO classesDAO;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> create(@RequestBody User user) {

        //TODO process when dao.create fails with SQL Exception

        // create an entity using the DAO
        int entityID = userDAO.create(user);
        // id gets assigned after creation
        user.setId(entityID);
        // return entity
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClassEntity>> get() {
        List<ClassEntity> classes = classesDAO.getAll();

        System.out.println(classes.size());

        return new ResponseEntity<List<ClassEntity>>(classes, HttpStatus.OK);
    }

}
