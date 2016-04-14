package be.lambdaware.controllers;

import be.lambdaware.dao.CompetencesDAO;
import be.lambdaware.model.CompetenceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by MichielVM on 14/04/2016.
 */
@RestController
@RequestMapping("/competencemodel")
public class CompetenceModelTestController {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private CompetencesDAO competencesDAO;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CompetenceModel> create(@RequestBody CompetenceModel competenceModel) {
        competenceModel.setCompetencesDAO(competencesDAO);
        competenceModel.createInDB();
        return new ResponseEntity<CompetenceModel>(competenceModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<CompetenceModel> get(@RequestParam(value="id") Integer competenceId ) {
        CompetenceModel competenceModel = new CompetenceModel(competencesDAO);
        competenceModel.getFromDB(competenceId);
        return new ResponseEntity<CompetenceModel>(competenceModel, HttpStatus.OK);
    }

}
