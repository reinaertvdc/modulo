package be.lambdaware.controllers;

import be.lambdaware.dao.CompetenceDAO;
import be.lambdaware.entities.CompetenceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by martijn on 08/04/16.
 */
@RestController
@RequestMapping("/competences")
public class CompetenceTestController {

    @Autowired
    private CompetenceDAO competenceDAO;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CompetenceEntity> create(@RequestBody CompetenceEntity certificate) {

        //TODO process when dao.create fails with SQL Exception

        // create an entity using the DAO
        int entityID = competenceDAO.create(certificate);
        // id gets assigned after creation
        certificate.setId(entityID);
        // return entity
        return new ResponseEntity<CompetenceEntity>(certificate, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<CompetenceEntity> get(@RequestParam(value="id") Integer id) {

        CompetenceEntity competences = competenceDAO.get(id);

        return new ResponseEntity<CompetenceEntity>(competences, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(path = "/subCertificateCategory",method = RequestMethod.GET)
    public ResponseEntity<List<CompetenceEntity>> getBySubCertificateCategoryId(@RequestParam(value="subCertificateCategoryId") Integer subCertificateCategoryId) {

        List<CompetenceEntity> competences = competenceDAO.getBySubCertificateCategory(subCertificateCategoryId);

        return new ResponseEntity<List<CompetenceEntity>>(competences, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(path = "/all",method = RequestMethod.GET)
    public ResponseEntity<List<CompetenceEntity>> getAll() {

        List<CompetenceEntity> competences = competenceDAO.getAll();

        return new ResponseEntity<List<CompetenceEntity>>(competences, HttpStatus.OK);
    }
}


