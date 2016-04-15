package be.lambdaware.controllers;

import be.lambdaware.dao.CompetenceDAO;
import be.lambdaware.dao.SubCertificateCategoryDAO;
import be.lambdaware.model.CompetenceModel;
import be.lambdaware.model.SubCertificateCategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CompetenceDAO competenceDAO;
    @Autowired
    private SubCertificateCategoryDAO subCertificateCategoryDAO;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CompetenceModel> create(@RequestBody CompetenceModel competenceModel) {
        competenceModel.setCompetenceDAO(competenceDAO);
        competenceModel.createInDB();
        return new ResponseEntity<CompetenceModel>(competenceModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<CompetenceModel> get(@RequestParam(value="id") Integer competenceId ) {
        CompetenceModel competenceModel = new CompetenceModel(competenceDAO);
        competenceModel.getFromDB(competenceId);
        return new ResponseEntity<CompetenceModel>(competenceModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value="/subcertificatecategory", method = RequestMethod.GET)
    public ResponseEntity<SubCertificateCategoryModel> getSubCertificateCategory(@RequestParam(value="id") Integer competenceId ) {
        CompetenceModel competenceModel = new CompetenceModel(competenceDAO);
        competenceModel.getFromDB(competenceId);
        SubCertificateCategoryModel subCertificateCategory = competenceModel.getSubCertificateCategory(subCertificateCategoryDAO);
        return new ResponseEntity<SubCertificateCategoryModel>(subCertificateCategory, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE)
    public boolean delete(@RequestParam(value="id") Integer competenceId ) {
        CompetenceModel competenceModel = new CompetenceModel(competenceDAO);
        competenceModel.getFromDB(competenceId);
        return competenceModel.deleteFromDB();
    }
}
