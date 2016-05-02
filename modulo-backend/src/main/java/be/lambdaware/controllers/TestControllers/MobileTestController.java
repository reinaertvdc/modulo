package be.lambdaware.controllers.TestControllers;

import be.lambdaware.dao.*;
import be.lambdaware.model.StudentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hendrik
 */
@RestController
@RequestMapping("/mobile")
public class MobileTestController {

    @Autowired
    SubCertificateDAO subCertificateDAO;

    @Autowired
    SubCertificateCategoryDAO subCertificateCategoryDAO;

    @Autowired
    CompetenceDAO competenceDAO;

    @Autowired
    ClassDAO classDAO;

    @Autowired
    CertificateDAO certificateDAO;

    @Autowired
    StudentInfoDAO studentInfoDAO;

    @Autowired
    UserDAO userDAO;
    @Autowired
    StudentBGVScoreDAO studentBGVScoreDAO;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public StudentModel get() {
        StudentModel studentModel = new StudentModel(userDAO, studentInfoDAO);
        studentModel.getFromDB(11);
        return studentModel;
    }
}
