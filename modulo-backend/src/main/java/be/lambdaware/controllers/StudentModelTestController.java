package be.lambdaware.controllers;

import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.model.StudentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by MichielVM on 12/04/2016.
 */
@RestController
@RequestMapping("/user/student")
public class StudentModelTestController {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private StudentInfoDAO studentInfoDAO;


//    {"userEntity":{"email":"michiel@hotmail.com", "password":"michielmichielmichiel", "type":"STUDENT"},
//        "studentInfoEntity":{"parent":"1", "firstName":"Michiel", "lastName":"Vanmunster", "birthDate":"1995-07-25", "birthPlace":"Leuven", "nationality":"Belgium", "nationalIdentificationNumber":"1234567890", "street":"WAALhostraat", "houseNumber":"1", "postalCode":"3401", "city":"Walshoutem", "phoneParent":"123", "phoneCell":"123", "bankAccount":"BE67-123"}}
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<StudentModel> create(@RequestBody StudentModel studentModel) {
        studentModel.setUserDAO(userDAO);
        studentModel.setStudentInfoDAO(studentInfoDAO);
        if (studentModel.getUserEntity().getId() == null)
            studentModel.createInDB();
        else
            studentModel.updateInDB();
        return new ResponseEntity<StudentModel>(studentModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<StudentModel> get(@RequestParam(value="userID") Integer userId ) {
        StudentModel studentModel = new StudentModel(userDAO, studentInfoDAO);
        studentModel.getFromDB(userId);
        return new ResponseEntity<StudentModel>(studentModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE)
    public boolean delete(@RequestParam(value="userID") Integer userId ) {
        StudentModel studentModel = new StudentModel(userDAO, studentInfoDAO);
        studentModel.getFromDB(userId);
        return studentModel.deleteFromDB();
    }
}
