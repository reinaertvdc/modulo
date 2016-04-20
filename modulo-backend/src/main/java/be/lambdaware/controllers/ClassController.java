package be.lambdaware.controllers;

import be.lambdaware.dao.CertificateDAO;
import be.lambdaware.dao.ClassCertificateDAO;
import be.lambdaware.dao.ClassDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.model.ClassModel;
import be.lambdaware.model.TeacherModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by MichielVM on 20/04/2016.
 */
@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ClassDAO classDAO;
    @Autowired
    private CertificateDAO certificateDAO;
    @Autowired
    private ClassCertificateDAO classCertificateDAO;


    @CrossOrigin
    @RequestMapping(value = "/teacher/{teacherId}", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<ClassModel>> getClasses(@PathVariable Integer teacherId) {
        TeacherModel teacherModel = new TeacherModel(userDAO);
        teacherModel.getFromDB(teacherId);
        ArrayList<ClassModel> classes = teacherModel.getClasses(classDAO, certificateDAO, classCertificateDAO);
        return new ResponseEntity<ArrayList<ClassModel>>(classes, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{classId}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable Integer classId ) {
        ClassModel clazz = new ClassModel(classDAO);
        clazz.getFromDB(classId);
        clazz.deleteFromDB();
        return true;
    }
}
