package be.lambdaware.controllers.TestControllers;

import be.lambdaware.dao.CompetenceDAO;
import be.lambdaware.dao.SubCertificateCategoryDAO;
import be.lambdaware.dao.SubCertificateDAO;
import be.lambdaware.model.CompetenceModel;
import be.lambdaware.model.SubCertificateCategoryModel;
import be.lambdaware.model.SubCertificateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by MichielVM on 14/04/2016.
 */
@RestController
@RequestMapping("/subcertificatecategorymodel")
public class SubCertificateCategoryModelTestController {

    @Autowired
    private SubCertificateCategoryDAO subCertificateCategoryDAO;
    @Autowired
    private CompetenceDAO competenceDAO;
    @Autowired
    private SubCertificateDAO subCertificateDAO;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SubCertificateCategoryModel> create(@RequestBody SubCertificateCategoryModel competenceModel) {
        competenceModel.setSubCertificateCategoryDAO(subCertificateCategoryDAO);
        competenceModel.createInDB();
        return new ResponseEntity<SubCertificateCategoryModel>(competenceModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{subCertificateCategoryId}", method = RequestMethod.GET)
    public ResponseEntity<SubCertificateCategoryModel> get(@PathVariable Integer subCertificateCategoryId ) {
        SubCertificateCategoryModel subCertificateCategoryModel = new SubCertificateCategoryModel(subCertificateCategoryDAO);
        subCertificateCategoryModel.getFromDB(subCertificateCategoryId);
        return new ResponseEntity<SubCertificateCategoryModel>(subCertificateCategoryModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value="/competences/{subCertificateCategoryId}", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<CompetenceModel>> getCompetences(@PathVariable Integer subCertificateCategoryId ) {
        SubCertificateCategoryModel subCertificateCategoryModel = new SubCertificateCategoryModel(subCertificateCategoryDAO);
        subCertificateCategoryModel.getFromDB(subCertificateCategoryId);
        ArrayList<CompetenceModel> competences = subCertificateCategoryModel.getCompetences(competenceDAO);
        return new ResponseEntity<ArrayList<CompetenceModel>>(competences, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value="/subcertificate/{subCertificateCategoryId}", method = RequestMethod.GET)
    public ResponseEntity<SubCertificateModel> getSubCertificateCategory(@PathVariable Integer subCertificateCategoryId ) {
        SubCertificateCategoryModel subCertificateCategoryModel = new SubCertificateCategoryModel(subCertificateCategoryDAO);
        subCertificateCategoryModel.getFromDB(subCertificateCategoryId);
        SubCertificateModel subCertificate = subCertificateCategoryModel.getSubCertificate(subCertificateDAO);
        return new ResponseEntity<SubCertificateModel>(subCertificate, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{subCertificateCategoryId}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable Integer subCertificateCategoryId ) {
        SubCertificateCategoryModel subCertificateCategoryModel = new SubCertificateCategoryModel(subCertificateCategoryDAO);
        subCertificateCategoryModel.getFromDB(subCertificateCategoryId);
        subCertificateCategoryModel.deleteFromDB();
        return true;
    }
}
