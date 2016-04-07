package be.lambdaware.controllers;

import be.lambdaware.dao.UserClassDAO;
import be.lambdaware.entities.UserClassEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by martijn on 07/04/16.
 */
@RestController
@RequestMapping("/user_class")
public class UserClassTestController {


        @Autowired
        private ApplicationContext context;
        @Autowired
        private UserClassDAO userClassDAO;

        @CrossOrigin
        @RequestMapping(method = RequestMethod.POST)
        public ResponseEntity<UserClassEntity> create(@RequestBody UserClassEntity userClass) {

            //TODO process when dao.create fails with SQL Exception

            // create an entity using the DAO
            userClassDAO.create(userClass);

            // return entity
            return new ResponseEntity<UserClassEntity>(userClass, HttpStatus.OK);
        }

        @CrossOrigin
        @RequestMapping(method = RequestMethod.GET)
        public ResponseEntity<UserClassEntity> get(@RequestParam(value="student_id") Integer student_id, @RequestParam(value="class_id") Integer class_id) {

            UserClassEntity userClass = userClassDAO.get(student_id, class_id);

            return new ResponseEntity<UserClassEntity>(userClass, HttpStatus.OK);
        }
    /*
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserClassEntity>> get() {

        List<UserClassEntity> certificates = userClassDAO.getAll();

        return new ResponseEntity<List<UserClassEntity>>(certificates, HttpStatus.OK);
    }
    */
}
