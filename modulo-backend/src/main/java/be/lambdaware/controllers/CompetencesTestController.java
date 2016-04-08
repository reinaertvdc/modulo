package be.lambdaware.controllers;

import be.lambdaware.dao.CompetencesDAO;
import be.lambdaware.entities.CompetencesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by martijn on 08/04/16.
 */
@RestController
@RequestMapping("/competences")
public class CompetencesTestController {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private CompetencesDAO competencesDAO;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CompetencesEntity> create(@RequestBody CompetencesEntity certificate) {

        //TODO process when dao.create fails with SQL Exception

        // create an entity using the DAO
        int entityID = competencesDAO.create(certificate);
        // id gets assigned after creation
        certificate.setId(entityID);
        // return entity
        return new ResponseEntity<CompetencesEntity>(certificate, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<CompetencesEntity> get(@RequestParam(value="id") Integer id) {

        CompetencesEntity competences = competencesDAO.get(id);

        return new ResponseEntity<CompetencesEntity>(competences, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(path = "/subCertificateCategory",method = RequestMethod.GET)
    public ResponseEntity<List<CompetencesEntity>> getBySubCertificateCategoryId(@RequestParam(value="subCertificateCategoryId") Integer subCertificateCategoryId) {

        List<CompetencesEntity> competences = competencesDAO.getBySubCertificateCategory(subCertificateCategoryId);

        return new ResponseEntity<List<CompetencesEntity>>(competences, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(path = "/all",method = RequestMethod.GET)
    public ResponseEntity<List<CompetencesEntity>> getAll() {

        List<CompetencesEntity> competences = competencesDAO.getAll();

        return new ResponseEntity<List<CompetencesEntity>>(competences, HttpStatus.OK);
    }
}


