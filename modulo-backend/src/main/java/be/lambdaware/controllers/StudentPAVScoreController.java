package be.lambdaware.controllers;

import be.lambdaware.dao.StudentPAVScoreDAO;
import be.lambdaware.entities.StudentPAVScoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jensv on 07-Apr-16.
 */

@RestController
@RequestMapping("/pav")
public class StudentPAVScoreController {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private StudentPAVScoreDAO studentPAVScoreDAO;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<StudentPAVScoreEntity> create(@RequestBody StudentPAVScoreEntity entity) {

        //TODO process when dao.create fails with SQL Exception

        // create an entity using the DAO
        int entityID = studentPAVScoreDAO.create(entity);
        // id gets assigned after creation
        entity.setId(entityID);
        // return entity
        return new ResponseEntity<StudentPAVScoreEntity>(entity, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<StudentPAVScoreEntity> get(@RequestParam(value="id") Integer id) {

        // get a grade
        StudentPAVScoreEntity stundentPAVScore= studentPAVScoreDAO.get(id);

        // return entity
        return new ResponseEntity<StudentPAVScoreEntity>(stundentPAVScore, HttpStatus.OK);
    }
}
