package be.lambdaware.controllers;

import be.lambdaware.dao.CertificateDAO;
import be.lambdaware.dao.SubCertificateDAO;
import be.lambdaware.model.CertificateModel;
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
@RequestMapping("/certificatemodel")
public class CertificateModelTestController {

    @Autowired
    private CertificateDAO certificateDAO;
    @Autowired
    private SubCertificateDAO subCertificateDAO;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CertificateModel> create(@RequestBody CertificateModel certificateModel) {
        certificateModel.setCertificateDAO(certificateDAO);
        certificateModel.createInDB();
        return new ResponseEntity<CertificateModel>(certificateModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<CertificateModel> get(@RequestParam(value="id") Integer certificateId ) {
        CertificateModel certificateModel = new CertificateModel(certificateDAO);
        certificateModel.getFromDB(certificateId);
        return new ResponseEntity<CertificateModel>(certificateModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value="/subcertificates", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<SubCertificateModel>> getSubCertificates(@RequestParam(value="id") Integer certificateId ) {
        CertificateModel certificateModel = new CertificateModel(certificateDAO);
        certificateModel.getFromDB(certificateId);
        ArrayList<SubCertificateModel> subCertificates = certificateModel.getSubCertificates(subCertificateDAO);
        return new ResponseEntity<ArrayList<SubCertificateModel>>(subCertificates, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE)
    public boolean delete(@RequestParam(value="id") Integer certificateId ) {
        CertificateModel certificateModel = new CertificateModel(certificateDAO);
        certificateModel.getFromDB(certificateId);
        return certificateModel.deleteFromDB();
    }
}
