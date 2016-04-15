package be.lambdaware.controllers;

import be.lambdaware.dao.StudentBGVScoreDAO;
import be.lambdaware.model.BGVScoreModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by MichielVM on 15/04/2016.
 */
@RestController
@RequestMapping("/score/bgv")
public class BGVScoreModelTestController {

    @Autowired
    private StudentBGVScoreDAO bgvScoreDAO;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BGVScoreModel> create(@RequestBody BGVScoreModel bgvScoreModel) {
        bgvScoreModel.setStudentBGVScoreDAO(bgvScoreDAO);
        bgvScoreModel.createInDB();
        return new ResponseEntity<BGVScoreModel>(bgvScoreModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{bgvScoreId}", method = RequestMethod.GET)
    public ResponseEntity<BGVScoreModel> get(@PathVariable Integer bgvScoreId) {
        BGVScoreModel bgvScoreModel = new BGVScoreModel(bgvScoreDAO);
        bgvScoreModel.getFromDB(bgvScoreId);
        return new ResponseEntity<BGVScoreModel>(bgvScoreModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{bgvScoreId}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable Integer bgvScoreId) {
        BGVScoreModel bgvScoreModel = new BGVScoreModel(bgvScoreDAO);
        bgvScoreModel.getFromDB(bgvScoreId);
        bgvScoreModel.deleteFromDB();
        return true;
    }
}
