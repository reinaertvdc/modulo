package be.lambdaware.controllers;

import be.lambdaware.dao.CertificatesDAO;
import be.lambdaware.entities.CertificatesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by martijn on 07/04/16.
 */
@RestController
@RequestMapping("/certificate")
public class CertificatesTestController {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private CertificatesDAO certificatesDAO;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CertificatesEntity> create(@RequestBody CertificatesEntity certificate) {

        //TODO process when dao.create fails with SQL Exception

        // create an entity using the DAO
        int entityID = certificatesDAO.create(certificate);
        // id gets assigned after creation
        certificate.setId(entityID);
        // return entity
        return new ResponseEntity<CertificatesEntity>(certificate, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CertificatesEntity>> get(@RequestParam(value="id") Integer certificateId ) {

        List<CertificatesEntity> certificates = certificatesDAO.getAll();

        return new ResponseEntity<List<CertificatesEntity>>(certificates, HttpStatus.OK);
    }
}
