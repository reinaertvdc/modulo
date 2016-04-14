package be.lambdaware.controllers;

import be.lambdaware.dao.CourseTopicDAO;
import be.lambdaware.entities.CourseTopicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jensv on 07-Apr-16.
 */

@RestController
@RequestMapping("/courseTopic")
public class CourseTopicController {

    @Autowired
    private CourseTopicDAO courseTopicDAO;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CourseTopicEntity> create(@RequestBody CourseTopicEntity courseTopic) {

        //TODO process when dao.create fails with SQL Exception

        // create an entity using the DAO
        int entityID = courseTopicDAO.create(courseTopic);
        // id gets assigned after creation
        courseTopic.setId(entityID);
        // return entity
        return new ResponseEntity<CourseTopicEntity>(courseTopic, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CourseTopicEntity>> get() {
        List<CourseTopicEntity> topics = courseTopicDAO.getAll();

        System.out.println(topics.size());

        return new ResponseEntity<List<CourseTopicEntity>>(topics, HttpStatus.OK);
    }
}
