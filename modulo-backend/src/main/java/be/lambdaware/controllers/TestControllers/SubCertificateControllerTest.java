package be.lambdaware.controllers.TestControllers;

import be.lambdaware.dao.CertificateDAO;
import be.lambdaware.dao.SubCertificateDAO;
import be.lambdaware.entities.CertificateEntity;
import be.lambdaware.entities.SubCertificateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by MichielVM on 7/04/2016.
 */
@RestController
@RequestMapping("/subcertificateEntity")
public class SubCertificateControllerTest {

    @Autowired
    private SubCertificateDAO subCertificateDAO;
    @Autowired
    private CertificateDAO certificateDAO;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<SubCertificateEntity> get(@RequestParam(value="id") Integer id) {

        //TODO process when dao.create fails with SQL Exception

        // create an entity using the DAO
        SubCertificateEntity subcertificate = subCertificateDAO.get(id);

        // return entity
        return new ResponseEntity<SubCertificateEntity>(subcertificate, HttpStatus.OK);
    }



    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SubCertificateEntity> create(@RequestBody SubCertificateEntity subcertificate) {

        //TODO process when dao.create fails with SQL Exception
        // create an entity using the DAO
        int entityID = subCertificateDAO.create(subcertificate);
        // id gets assigned after creation
        subcertificate.setId(entityID);

        // return entity
        return new ResponseEntity<SubCertificateEntity>(subcertificate, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value="/all", method = RequestMethod.GET)
    public ResponseEntity<List<SubCertificateEntity>> getAllByCertificate(@RequestParam(value="certificateId") Integer certificateId) {

        // get the certificate
        CertificateEntity certificate = certificateDAO.get(certificateId);

        // get all subcertificates belonging to this certificate
        List<SubCertificateEntity> subs = subCertificateDAO.getAllByCertificate(certificateId);

        // return entity
        return new ResponseEntity<List<SubCertificateEntity>>(subs, HttpStatus.OK);
    }


}