package be.lambdaware.controllers;

import be.lambdaware.dao.StudentBGVScoreDAO;
import be.lambdaware.entities.StudentBGVScoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jensv on 08-Apr-16.
 */

@RestController
@RequestMapping("/bgv")
public class StudentBGVScoreController {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private StudentBGVScoreDAO studentBGVScoreDAO;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<StudentBGVScoreEntity> create(@RequestBody StudentBGVScoreEntity entity) {

        //TODO process when dao.create fails with SQL Exception

        // create an entity using the DAO
        int entityID = studentBGVScoreDAO.create(entity);
        // id gets assigned after creation
        entity.setId(entityID);
        // return entity
        return new ResponseEntity<StudentBGVScoreEntity>(entity, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<StudentBGVScoreEntity> get(@RequestParam(value="id") Integer id) {

        // get a grade
        StudentBGVScoreEntity stundentBGVScore= studentBGVScoreDAO.get(id);

        // return entity
        return new ResponseEntity<StudentBGVScoreEntity>(stundentBGVScore, HttpStatus.OK);
    }
}
