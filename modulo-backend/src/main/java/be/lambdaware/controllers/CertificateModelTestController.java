package be.lambdaware.controllers;

import be.lambdaware.dao.CertificateDAO;
import be.lambdaware.model.CertificateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by MichielVM on 14/04/2016.
 */
@RestController
@RequestMapping("/certificatemodel")
public class CertificateModelTestController {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private CertificateDAO certificateDAO;
//    @Autowired
//    private SubCertificateDAO subCertificateDAO;


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

//    @CrossOrigin
//    @RequestMapping(value="/subcertificates", method = RequestMethod.GET)
//    public ResponseEntity<ArrayList<SubCertificateCategoryModel>> getSubCertificateCategories(@RequestParam(value="id") Integer subCertificateId ) {
//        SubCertificateModel subCertificateModel = new SubCertificateModel(subCertificateDAO);
//        subCertificateModel.getFromDB(subCertificateId);
//        ArrayList<SubCertificateCategoryModel> subCertificateCategories = subCertificateModel.getSubCertificateCategories(subCertificateCategoryDAO);
//        return new ResponseEntity<ArrayList<SubCertificateCategoryModel>>(subCertificateCategories, HttpStatus.OK);
//    }
}
