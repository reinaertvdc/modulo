package be.lambdaware.controllers;

import be.lambdaware.dao.GradeDAO;
import be.lambdaware.entities.GradeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Vincent on 07/04/16.
 */
@RestController
@RequestMapping("/grade")
public class GradeTestController {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private GradeDAO gradeDAO;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<GradeEntity> create(@RequestBody GradeEntity grade) {

        //TODO process when dao.create fails with SQL Exception

        // create an entity using the DAO
        int entityID = gradeDAO.create(grade);

        // id gets assigned after creation
        grade.setId(entityID);

        // return entity
        return new ResponseEntity<GradeEntity>(grade, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<GradeEntity> get(@RequestParam(value="id") Integer id) {

        // get a grade
        GradeEntity grade = gradeDAO.get(id);

        // return entity
        return new ResponseEntity<GradeEntity>(grade, HttpStatus.OK);
    }
}
