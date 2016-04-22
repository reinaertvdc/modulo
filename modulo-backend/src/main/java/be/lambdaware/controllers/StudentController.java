package be.lambdaware.controllers;

import be.lambdaware.dao.CertificateDAO;
import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.dao.SubCertificateDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.model.StudentModel;
import be.lambdaware.model.StudentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by MichielVM on 18/04/2016.
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentInfoDAO studentInfoDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CertificateDAO certificateDAO;

    @CrossOrigin
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<StudentModel>> getAll() {
        ArrayList<StudentModel> students = StudentModel.getAll(studentInfoDAO, userDAO, certificateDAO);
        return new ResponseEntity<ArrayList<StudentModel>>(students, HttpStatus.OK);
    }
}
