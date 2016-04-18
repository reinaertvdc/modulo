package be.lambdaware.controllers;

import be.lambdaware.dao.GradeDAO;
import be.lambdaware.dao.ObjectiveDAO;
import be.lambdaware.model.GradeModel;
import be.lambdaware.model.ObjectiveModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by MichielVM on 15/04/2016.
 */
@RestController
@RequestMapping("/objectivemodel")
public class ObjectiveModelTestController {

    @Autowired
    private ObjectiveDAO objectiveDAO;
    @Autowired
    private GradeDAO gradeDAO;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ObjectiveModel> create(@RequestBody ObjectiveModel objectiveModel) {
        objectiveModel.setObjectiveDAO(objectiveDAO);
        objectiveModel.createInDB();
        return new ResponseEntity<ObjectiveModel>(objectiveModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{objectiveId}", method = RequestMethod.GET)
    public ResponseEntity<ObjectiveModel> get(@PathVariable Integer objectiveId) {
        ObjectiveModel objectiveModel = new ObjectiveModel(objectiveDAO);
        objectiveModel.getFromDB(objectiveId);
        return new ResponseEntity<ObjectiveModel>(objectiveModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{objectiveId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer objectiveId) {
        ObjectiveModel objectiveModel = new ObjectiveModel(objectiveDAO);
        objectiveModel.getFromDB(objectiveId);
        objectiveModel.deleteFromDB();
    }

    @CrossOrigin
    @RequestMapping(value = "/grade/{objectiveId}", method = RequestMethod.GET)
    public ResponseEntity<GradeModel> getGrade(@PathVariable Integer objectiveId) {
        ObjectiveModel objectiveModel = new ObjectiveModel(objectiveDAO);
        objectiveModel.getFromDB(objectiveId);
        GradeModel grade = objectiveModel.getGrade(gradeDAO);
        return new ResponseEntity<GradeModel>(grade, HttpStatus.OK);
    }
}
