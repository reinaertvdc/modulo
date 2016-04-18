package be.lambdaware.controllers.TestControllers;

import be.lambdaware.dao.CourseTopicDAO;
import be.lambdaware.dao.ObjectiveDAO;
import be.lambdaware.model.CourseTopicModel;
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
@RequestMapping("/coursetopicmodel")
public class CourseTopicModelTestController {

    @Autowired
    private CourseTopicDAO courseTopicDAO;
    @Autowired
    private ObjectiveDAO objectiveDAO;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CourseTopicModel> create(@RequestBody CourseTopicModel courseTopicModel) {
        courseTopicModel.setCourseTopicDAO(courseTopicDAO);
        courseTopicModel.createInDB();
        return new ResponseEntity<CourseTopicModel>(courseTopicModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{courseTopicId}", method = RequestMethod.GET)
    public ResponseEntity<CourseTopicModel> get(@PathVariable Integer courseTopicId) {
        CourseTopicModel courseTopicModel = new CourseTopicModel(courseTopicDAO);
        courseTopicModel.getFromDB(courseTopicId);
        return new ResponseEntity<CourseTopicModel>(courseTopicModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{courseTopicId}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable Integer courseTopicId) {
        CourseTopicModel courseTopicModel = new CourseTopicModel(courseTopicDAO);
        courseTopicModel.getFromDB(courseTopicId);
        courseTopicModel.deleteFromDB();
        return true;
    }

    @CrossOrigin
    @RequestMapping(value = "/objectives/{courseTopicId}", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<ObjectiveModel>> getObjectives(@PathVariable Integer courseTopicId) {
        CourseTopicModel courseTopicModel = new CourseTopicModel(courseTopicDAO);
        courseTopicModel.getFromDB(courseTopicId);
        ArrayList<ObjectiveModel> objectives = courseTopicModel.getObjectives(objectiveDAO);
        return new ResponseEntity<ArrayList<ObjectiveModel>>(objectives, HttpStatus.OK);
    }
}
