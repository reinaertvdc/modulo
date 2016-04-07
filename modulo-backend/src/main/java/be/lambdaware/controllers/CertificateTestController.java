package be.lambdaware.controllers;

import be.lambdaware.dao.CertificateDAO;
import be.lambdaware.entities.CertificateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by martijn on 07/04/16.
 */
@RestController
@RequestMapping("/certificate")
public class CertificateTestController {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private CertificateDAO certificateDAO;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CertificateEntity> create(@RequestBody CertificateEntity certificate) {

        //TODO process when dao.create fails with SQL Exception

        // create an entity using the DAO
        int entityID = certificateDAO.create(certificate);
        // id gets assigned after creation
        certificate.setId(entityID);
        // return entity
        return new ResponseEntity<CertificateEntity>(certificate, HttpStatus.OK);
    }
}
