package be.lambdaware.controllers;

import be.lambdaware.dao.SubCertificateCategoryDAO;
import be.lambdaware.dao.SubCertificateDAO;
import be.lambdaware.entities.SubCertificateCategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by MichielVM on 8/04/2016.
 */
@RestController
@RequestMapping("/subcertificatecategory")
public class SubCertificateCategoryControllerTest {

    @Autowired
    private SubCertificateCategoryDAO subCertificateCategoryDAO;
    @Autowired
    private SubCertificateDAO subCertificateDAO;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<SubCertificateCategoryEntity> get(@RequestParam(value="id") Integer id) {

        //TODO process when dao.create fails with SQL Exception

        // create an entity using the DAO
        SubCertificateCategoryEntity subCertificateCategory = subCertificateCategoryDAO.get(id);

        // return entity
        return new ResponseEntity<SubCertificateCategoryEntity>(subCertificateCategory, HttpStatus.OK);
    }



    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SubCertificateCategoryEntity> create(@RequestBody SubCertificateCategoryEntity subCertificateCategory) {

        //TODO process when dao.create fails with SQL Exception
        // create an entity using the DAO
        int entityID = subCertificateCategoryDAO.create(subCertificateCategory);
        // id gets assigned after creation
        subCertificateCategory.setId(entityID);

        // return entity
        return new ResponseEntity<SubCertificateCategoryEntity>(subCertificateCategory, HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(value="/all", method = RequestMethod.GET)
    public ResponseEntity<List<SubCertificateCategoryEntity>> getAllBySubCertificate(@RequestParam(value="subCertificateId") Integer subCertificateId) {

        // get all subcertificates belonging to this certificate
        List<SubCertificateCategoryEntity> cats = subCertificateCategoryDAO.getAllBySubCertificate(subCertificateId);

        // return entity
        return new ResponseEntity<List<SubCertificateCategoryEntity>>(cats, HttpStatus.OK);
    }


}