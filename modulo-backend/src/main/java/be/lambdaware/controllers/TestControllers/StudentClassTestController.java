package be.lambdaware.controllers.TestControllers;

import be.lambdaware.dao.StudentClassDAO;
import be.lambdaware.entities.StudentClassEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by martijn on 07/04/16.
 */
@RestController
@RequestMapping("/student_classEntity")
public class StudentClassTestController {

    @Autowired
    private StudentClassDAO studentClassDAO;
    /*
    @Autowired
    private StudentInfoDAO studentInfoDAO;
    @Autowired
    private ClassDAO classesDAO;
    */


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<StudentClassEntity> create(@RequestBody StudentClassEntity studentClass) {

        //TODO process when dao.create fails with SQL Exception

        // create an entity using the DAO
        studentClassDAO.create(studentClass);

        // return entity
        return new ResponseEntity<StudentClassEntity>(studentClass, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<StudentClassEntity> get(@RequestParam(value="studentInfoId") Integer studentInfoId, @RequestParam(value="classId") Integer classId) {

        StudentClassEntity studentClass = studentClassDAO.get(studentInfoId, classId);

        return new ResponseEntity<StudentClassEntity>(studentClass, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(path = "/all",method = RequestMethod.GET)
    public ResponseEntity<List<StudentClassEntity>> get() {

        List<StudentClassEntity> studentClassList = studentClassDAO.getAll();

        return new ResponseEntity<List<StudentClassEntity>>(studentClassList, HttpStatus.OK);
    }
}
