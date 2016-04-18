package be.lambdaware.controllers.TestControllers;

import be.lambdaware.dao.StudentPAVScoreDAO;
import be.lambdaware.model.PAVScoreModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by MichielVM on 15/04/2016.
 */
@RestController
@RequestMapping("/score/pav")
public class PAVScoreModelTestController {

    @Autowired
    private StudentPAVScoreDAO pavScoreDAO;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PAVScoreModel> create(@RequestBody PAVScoreModel pavScoreModel) {
        pavScoreModel.setStudentPAVScoreDAO(pavScoreDAO);
        pavScoreModel.createInDB();
        return new ResponseEntity<PAVScoreModel>(pavScoreModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{pavScoreId}", method = RequestMethod.GET)
    public ResponseEntity<PAVScoreModel> get(@PathVariable Integer pavScoreId) {
        PAVScoreModel pavScoreModel = new PAVScoreModel(pavScoreDAO);
        pavScoreModel.getFromDB(pavScoreId);
        return new ResponseEntity<PAVScoreModel>(pavScoreModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{pavScoreId}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable Integer pavScoreId) {
        PAVScoreModel pavScoreModel = new PAVScoreModel(pavScoreDAO);
        pavScoreModel.getFromDB(pavScoreId);
        pavScoreModel.deleteFromDB();
        return true;
    }
}
