package be.lambdaware.controllers.TestControllers;

import be.lambdaware.dao.GradeDAO;
import be.lambdaware.dao.ObjectiveDAO;
import be.lambdaware.model.GradeModel;
import be.lambdaware.model.ObjectiveModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by MichielVM on 15/04/2016.
 */
@RestController
@RequestMapping("/grademodel")
public class GradeModelTestController {

    @Autowired
    private GradeDAO gradeDAO;
    @Autowired
    private ObjectiveDAO objectiveDAO;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<GradeModel> create(@RequestBody GradeModel gradeModel) {
        gradeModel.setGradeDAO(gradeDAO);
        gradeModel.createInDB();
        return new ResponseEntity<GradeModel>(gradeModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{gradeId}", method = RequestMethod.GET)
    public ResponseEntity<GradeModel> get(@PathVariable Integer gradeId) {
        GradeModel gradeModel = new GradeModel(gradeDAO);
        gradeModel.getFromDB(gradeId);
        return new ResponseEntity<GradeModel>(gradeModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{gradeId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer gradeId) {
        GradeModel gradeModel = new GradeModel(gradeDAO);
        gradeModel.getFromDB(gradeId);
        gradeModel.deleteFromDB();
    }

    @CrossOrigin
    @RequestMapping(value = "/objectives/{gradeId}", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<ObjectiveModel>> getObjectives(@PathVariable Integer gradeId) {
        GradeModel gradeModel = new GradeModel(gradeDAO);
        gradeModel.getFromDB(gradeId);
        ArrayList<ObjectiveModel> objectives = gradeModel.getObjectives(objectiveDAO);
        return new ResponseEntity<ArrayList<ObjectiveModel>>(objectives, HttpStatus.OK);
    }
}
