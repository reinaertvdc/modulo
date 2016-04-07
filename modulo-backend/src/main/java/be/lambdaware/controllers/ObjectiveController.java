package be.lambdaware.controllers;

import be.lambdaware.dao.CourseTopicDAO;
import be.lambdaware.dao.GradeDAO;
import be.lambdaware.dao.ObjectiveDAO;
import be.lambdaware.entities.CourseTopicEntity;
import be.lambdaware.entities.GradeEntity;
import be.lambdaware.entities.ObjectiveEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jensv on 07-Apr-16.
 */

@RestController
@RequestMapping("/objective")
public class ObjectiveController {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private ObjectiveDAO objectiveDAO;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ObjectiveEntity> create(@RequestBody ObjectiveEntity entity) {

        //TODO process when dao.create fails with SQL Exception

        // create an entity using the DAO
        int entityID = objectiveDAO.create(entity);
        // id gets assigned after creation
        entity.setId(entityID);
        // return entity
        return new ResponseEntity<ObjectiveEntity>(entity, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ObjectiveEntity> get(@RequestParam(value="id") Integer id) {

        // get a grade
        ObjectiveEntity objective= objectiveDAO.get(id);

        // return entity
        return new ResponseEntity<ObjectiveEntity>(objective, HttpStatus.OK);
    }
}
