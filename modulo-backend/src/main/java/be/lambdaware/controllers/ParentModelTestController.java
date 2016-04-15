package be.lambdaware.controllers;

import be.lambdaware.dao.ParentInfoDAO;
import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.model.ParentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Vincent on 12/04/16.
 */
@RestController
@RequestMapping("/user/parent")
public class ParentModelTestController {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ParentInfoDAO parentInfoDAO;
    @Autowired
    private StudentInfoDAO studentInfoDAO;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ParentModel> create(@RequestBody ParentModel parentModel) {
        parentModel.setUserDAO(userDAO);
        parentModel.setParentInfoDAO(parentInfoDAO);
        parentModel.createInDB();
        return new ResponseEntity<ParentModel>(parentModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<ParentModel> get(@PathVariable Integer userId ) {
        ParentModel parentModel = new ParentModel(userDAO, parentInfoDAO);
        parentModel.getFromDB(userId);
        return new ResponseEntity<ParentModel>(parentModel, HttpStatus.OK);
    }
}
