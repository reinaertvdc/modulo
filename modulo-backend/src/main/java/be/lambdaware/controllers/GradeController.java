package be.lambdaware.controllers;

import be.lambdaware.dao.GradeDAO;
import be.lambdaware.model.GradeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by MichielVM on 28/04/2016.
 */
@RestController
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeDAO gradeDAO;


    @CrossOrigin
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<GradeModel>> getAll() {
        ArrayList<GradeModel> grades = GradeModel.getAll(gradeDAO);
        return new ResponseEntity<ArrayList<GradeModel>>(grades, HttpStatus.OK);
    }
}
