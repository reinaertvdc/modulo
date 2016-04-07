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
    public ResponseEntity<ClassCertificateEntity> create(@RequestParam(value = "class_id") Integer class_id, @RequestParam(value = "certificate_id") Integer certificate_id) {
        //TODO process when dao.create fails with SQL Exception

        ClassEntity classEntity = classesDAO.get(class_id);
        CertificateEntity certificateEntity = certificateDAO.get(certificate_id);

        ClassCertificateEntity classCertificateEntity = new ClassCertificateEntity(certificateEntity, classEntity);

        // create an entity using the DAO
        classCertificateDAO.create(classCertificateEntity);

        // return entity
        return new ResponseEntity<ClassCertificateEntity>(classCertificateEntity, HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClassCertificateEntity>> get(@RequestParam(value = "class_id") Integer class_id, @RequestParam(value = "certificate_id") Integer certificate_id) {
        //TODO process when dao.create fails with SQL Exception

        ClassEntity classEntity = classesDAO.get(class_id);
        CertificateEntity certificateEntity = certificateDAO.get(certificate_id);

        List<ClassCertificateEntity> classCertificateEntity = classCertificateDAO.get(classEntity, certificateEntity);

        return new ResponseEntity<List<ClassCertificateEntity>>(classCertificateEntity, HttpStatus.OK);
    }
}
