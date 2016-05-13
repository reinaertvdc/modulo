package be.lambdaware.controllers;

import be.lambdaware.dao.CertificateDAO;
import be.lambdaware.dao.ClassDAO;
import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.models.*;
import be.lambdaware.response.Responses;
import be.lambdaware.security.APIAuthentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/certificate")
@CrossOrigin
public class CertificateController {

    private static Logger log = Logger.getLogger(CertificateController.class);
    @Autowired
    CertificateDAO certificateDAO;
    @Autowired
    StudentInfoDAO studentInfoDAO;
    @Autowired
    ClassDAO classDAO;

    @Autowired
    APIAuthentication authentication;

    // ===================================================================================
    // GET methods
    // ===================================================================================

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        List<Certificate> certificates = certificateDAO.findAll();

        if (certificates.size() == 0) return Responses.CERTIFICATES_NOT_FOUND;

        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @RequestMapping(value = "/enabled/{enabled}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByEnabled(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable boolean enabled) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin() && !authentication.isTeacher()) return Responses.UNAUTHORIZED;

        List<Certificate> certificates = certificateDAO.findAllByEnabledOrderByNameAsc(enabled);

        if (certificates.size() == 0) return Responses.CERTIFICATES_NOT_FOUND;
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Certificate certificate = certificateDAO.findById(id);

        if (certificate == null) return Responses.CERTIFICATE_NOT_FOUND;
        return new ResponseEntity<>(certificate, HttpStatus.OK);

    }

    @RequestMapping(value = "/id/{id}/name", method = RequestMethod.GET)
    public ResponseEntity<?> getNameFromCertificate(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Certificate certificate = certificateDAO.findById(id);

        if (certificate == null) return Responses.CERTIFICATE_NOT_FOUND;
        return new ResponseEntity<>(certificate.getName(), HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/enabled", method = RequestMethod.GET)
    public ResponseEntity<?> getEnabledFromCertificate(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Certificate certificate = certificateDAO.findById(id);

        if (certificate == null) return Responses.CERTIFICATE_NOT_FOUND;
        return new ResponseEntity<>(certificate.isEnabled(), HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/students", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentsFromCertificate(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;


        Certificate certificate = certificateDAO.findById(id);

        if (certificate == null) return Responses.CERTIFICATE_NOT_FOUND;

        List<User> users = new ArrayList<>();

        for(StudentInfo studentInfo : certificate.getStudents())
            users.add(studentInfo.getUser());

        if (users.size()==0) return Responses.USERS_NOT_FOUND;

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/classes", method = RequestMethod.GET)
    public ResponseEntity<?> getClassesFromCertificate(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;


        Certificate certificate = certificateDAO.findById(id);

        if (certificate == null) return Responses.CERTIFICATE_NOT_FOUND;

        List<Clazz> classes = certificate.getClasses();

        if (classes.size()==0) return Responses.CLASSES_NOT_FOUND;

        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/subcertificates", method = RequestMethod.GET)
    public ResponseEntity<?> getSubCertificatesFromCertificate(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;


        Certificate certificate = certificateDAO.findById(id);

        if (certificate == null) return Responses.CERTIFICATE_NOT_FOUND;

        List<SubCertificate> subCertificates = certificate.getSubCertificates();

        if (subCertificates.size()==0) return Responses.SUBCERTIFICATES_NOT_FOUND;

        return new ResponseEntity<>(subCertificates, HttpStatus.OK);
    }

    // ===================================================================================
    // PUT methods
    // ===================================================================================

    @RequestMapping(value = "/id/{id}/enable", method = RequestMethod.PUT)
    public ResponseEntity<?> enabledCertificate(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        Certificate certificate = certificateDAO.findById(id);

        if (certificate == null) return Responses.CERTIFICATE_NOT_FOUND;

        certificate.setEnabled(true);
        certificateDAO.saveAndFlush(certificate);
        return Responses.CERTIFICATE_ENABLED;
    }

    @RequestMapping(value = "/id/{id}/disable", method = RequestMethod.PUT)
    public ResponseEntity<?> disableCertificate(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        Certificate certificate = certificateDAO.findById(id);

        if (certificate == null) return Responses.CERTIFICATE_NOT_FOUND;

        certificate.setEnabled(false);
        certificateDAO.saveAndFlush(certificate);
        return Responses.CERTIFICATE_DISABLED;
    }

    @RequestMapping(value = "/id/{id}/student/id/{studentId}", method = RequestMethod.PUT)
    public ResponseEntity<?> addStudentToCertificate(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id,@PathVariable long studentId) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
//        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        Certificate certificate = certificateDAO.findById(id);

        if (certificate == null) return Responses.CERTIFICATE_NOT_FOUND;

        StudentInfo info = studentInfoDAO.findOne(studentId);

        if(info == null) return Responses.STUDENT_INFO_NOT_FOUND;

        info.setCertificate(certificate);

        studentInfoDAO.saveAndFlush(info);
        return Responses.CERTIFICATE_STUDENT_ADD;
    }

    // ===================================================================================
    // Delete methods
    // ===================================================================================

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCertificate(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        Certificate certificate = certificateDAO.findById(id);

        if (certificate == null) return Responses.CERTIFICATE_NOT_FOUND;


        for(StudentInfo student : certificate.getStudents()){
            student.setCertificate(null);
            studentInfoDAO.saveAndFlush(student);
        }

        for(Clazz clazz : certificate.getClasses()){
            clazz.setCertificate(null);
            classDAO.saveAndFlush(clazz);
        }

        certificateDAO.delete(id);
        return Responses.CERTIFICATE_DELETED;

    }

}
