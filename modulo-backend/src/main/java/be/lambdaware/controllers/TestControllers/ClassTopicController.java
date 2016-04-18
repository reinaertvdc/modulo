package be.lambdaware.controllers.TestControllers;

import be.lambdaware.dao.ClassTopicDAO;
import be.lambdaware.entities.ClassTopicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jensv on 08-Apr-16.
 */

@RestController
@RequestMapping("/classTopicsEntity")
public class ClassTopicController {

    @Autowired
    private ClassTopicDAO classTopicDAO;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ClassTopicEntity> create(@RequestBody ClassTopicEntity classTopics) {

        //TODO process when dao.create fails with SQL Exception

        // create an entity using the DAO
        classTopicDAO.create(classTopics);

        // return entity
        return new ResponseEntity<ClassTopicEntity>(classTopics, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ClassTopicEntity> get(@RequestParam(value="courseTopicId") Integer courseTopicId, @RequestParam(value="classId") Integer classId) {

        ClassTopicEntity studentClass = classTopicDAO.get(courseTopicId, classId);

        return new ResponseEntity<ClassTopicEntity>(studentClass, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(path = "/all",method = RequestMethod.GET)
    public ResponseEntity<List<ClassTopicEntity>> get() {
        List<ClassTopicEntity> topics = classTopicDAO.getAll();

        System.out.println(topics.size());

        return new ResponseEntity<List<ClassTopicEntity>>(topics, HttpStatus.OK);
    }
}
