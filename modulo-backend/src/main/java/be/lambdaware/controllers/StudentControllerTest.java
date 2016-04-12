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
public class StudentControllerTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private StudentInfoDAO studentInfoDAO;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<StudentModel> create(@RequestBody StudentModel studentModel) {
        studentModel.setUserDAO(userDAO);
        studentModel.setStudentInfoDAO(studentInfoDAO);
        studentModel.createInDB();
        return new ResponseEntity<StudentModel>(studentModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<StudentModel> get(@RequestParam(value="userID") Integer userId ) {
        StudentModel studentModel = new StudentModel(userDAO, studentInfoDAO);
        studentModel.getFromDB(userId);
        return new ResponseEntity<StudentModel>(studentModel, HttpStatus.OK);
    }
}
