package be.lambdaware.controllers;

import be.lambdaware.dao.CertificateDAO;
import be.lambdaware.dao.SubCertificateCategoryDAO;
import be.lambdaware.dao.SubCertificateDAO;
import be.lambdaware.model.CertificateModel;
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
@RequestMapping("/subcertificatemodel")
public class SubCertificateModelTestController {

    @Autowired
    private SubCertificateDAO subCertificateDAO;
    @Autowired
    private SubCertificateCategoryDAO subCertificateCategoryDAO;
    @Autowired
    private CertificateDAO certificateDAO;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SubCertificateModel> create(@RequestBody SubCertificateModel subCertificateModel) {
        subCertificateModel.setSubCertificateDAO(subCertificateDAO);
        subCertificateModel.createInDB();
        return new ResponseEntity<SubCertificateModel>(subCertificateModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<SubCertificateModel> get(@RequestParam(value="id") Integer subCertificateId ) {
        SubCertificateModel subCertificateModel = new SubCertificateModel(subCertificateDAO);
        subCertificateModel.getFromDB(subCertificateId);
        return new ResponseEntity<SubCertificateModel>(subCertificateModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value="/subcertificatecategories", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<SubCertificateCategoryModel>> getSubCertificateCategories(@RequestParam(value="id") Integer subCertificateId ) {
        SubCertificateModel subCertificateModel = new SubCertificateModel(subCertificateDAO);
        subCertificateModel.getFromDB(subCertificateId);
        ArrayList<SubCertificateCategoryModel> subCertificateCategories = subCertificateModel.getSubCertificateCategories(subCertificateCategoryDAO);
        return new ResponseEntity<ArrayList<SubCertificateCategoryModel>>(subCertificateCategories, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value="/certificate", method = RequestMethod.GET)
    public ResponseEntity<CertificateModel> getCertificate(@RequestParam(value="id") Integer certificateId ) {
        SubCertificateModel subCertificateModel = new SubCertificateModel(subCertificateDAO);
        subCertificateModel.getFromDB(certificateId);
        CertificateModel certificate = subCertificateModel.getCertificate(certificateDAO);
        return new ResponseEntity<CertificateModel>(certificate, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE)
    public boolean delete(@RequestParam(value="id") Integer subCertificateId ) {
        SubCertificateModel subCertificateModel = new SubCertificateModel(subCertificateDAO);
        subCertificateModel.getFromDB(subCertificateId);
        return subCertificateModel.deleteFromDB();
    }
}
