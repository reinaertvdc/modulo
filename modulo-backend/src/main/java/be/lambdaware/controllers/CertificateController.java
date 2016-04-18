package be.lambdaware.controllers;

import be.lambdaware.dao.CertificateDAO;
import be.lambdaware.dao.SubCertificateDAO;
import be.lambdaware.model.CertificateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by MichielVM on 18/04/2016.
 */
@RestController
@RequestMapping("/certificate")
public class CertificateController {

    @Autowired
    private CertificateDAO certificateDAO;
    @Autowired
    private SubCertificateDAO subCertificateDAO;



    @CrossOrigin
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<CertificateModel>> getAll() {
        ArrayList<CertificateModel> certificates = CertificateModel.getAll(certificateDAO);
        return new ResponseEntity<ArrayList<CertificateModel>>(certificates, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<CertificateModel> update(@RequestBody CertificateModel certificateModel) {
        certificateModel.setCertificateDAO(certificateDAO);
        certificateModel.updateInDB();
        return new ResponseEntity<CertificateModel>(certificateModel, HttpStatus.OK);
    }

}
