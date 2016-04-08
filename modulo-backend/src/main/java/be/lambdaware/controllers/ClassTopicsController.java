package be.lambdaware.controllers;

import be.lambdaware.dao.ClassTopicsDAO;
import be.lambdaware.entities.ClassTopicsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jensv on 08-Apr-16.
 */

@RestController
@RequestMapping("/classTopics")
public class ClassTopicsController {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private ClassTopicsDAO classTopicsDAO;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ClassTopicsEntity> create(@RequestBody ClassTopicsEntity classTopics) {

        //TODO process when dao.create fails with SQL Exception

        // create an entity using the DAO
        classTopicsDAO.create(classTopics);

        // return entity
        return new ResponseEntity<ClassTopicsEntity>(classTopics, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ClassTopicsEntity> get(@RequestParam(value="courseTopicId") Integer courseTopicId, @RequestParam(value="classId") Integer classId) {

        ClassTopicsEntity studentClass = classTopicsDAO.get(courseTopicId, classId);

        return new ResponseEntity<ClassTopicsEntity>(studentClass, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(path = "/all",method = RequestMethod.GET)
    public ResponseEntity<List<ClassTopicsEntity>> get() {
        List<ClassTopicsEntity> topics = classTopicsDAO.getAll();

        System.out.println(topics.size());

        return new ResponseEntity<List<ClassTopicsEntity>>(topics, HttpStatus.OK);
    }
}
