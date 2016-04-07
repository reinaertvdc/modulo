package be.lambdaware.controllers;

import be.lambdaware.dao.CertificateDAO;
import be.lambdaware.dao.ClassCertificateDAO;
import be.lambdaware.dao.ClassesDAO;
import be.lambdaware.entities.CertificateEntity;
import be.lambdaware.entities.ClassCertificateEntity;
import be.lambdaware.entities.ClassEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Vincent on 07/04/16.
 */
@RestController
@RequestMapping("/class_certificate")
public class ClassCertificateTestController {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private ClassCertificateDAO classCertificateDAO;
    @Autowired
    private ClassesDAO classesDAO;
    @Autowired
    private CertificateDAO certificateDAO;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ClassCertificateEntity> create(@RequestBody ClassCertificateEntity classCertificateEntity) {
        //TODO process when dao.create fails with SQL Exception


        // create an entity using the DAO
        classCertificateDAO.create(classCertificateEntity);

        // return entity
        return new ResponseEntity<ClassCertificateEntity>(classCertificateEntity, HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ClassCertificateEntity> get(@RequestParam(value = "classId") Integer classId, @RequestParam(value = "certificateId") Integer certificateId) {
        //TODO process when dao.create fails with SQL Exception

        ClassCertificateEntity classCertificateEntity = classCertificateDAO.get(classId, certificateId);

        return new ResponseEntity<ClassCertificateEntity>(classCertificateEntity, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET,path = "/get_by_class")
    public ResponseEntity<List<ClassCertificateEntity>> getByClass(@RequestParam(value = "classId") Integer classId) {
        //TODO process when dao.create fails with SQL Exception

        List<ClassCertificateEntity> classCertificateEntity = classCertificateDAO.getByClass(classId);

        return new ResponseEntity<List<ClassCertificateEntity>>(classCertificateEntity, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET,path = "/get_by_certificate")
    public ResponseEntity<List<ClassCertificateEntity>> getByCertificate(@RequestParam(value = "certificateId") Integer certificateId) {
        //TODO process when dao.create fails with SQL Exception

        List<ClassCertificateEntity> classCertificateEntity = classCertificateDAO.getByCertificate(certificateId);

        return new ResponseEntity<List<ClassCertificateEntity>>(classCertificateEntity, HttpStatus.OK);
    }
}
