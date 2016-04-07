package be.lambdaware.controllers;

import be.lambdaware.dao.ClassesDAO;
import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.dao.UserClassDAO;
import be.lambdaware.entities.ClassEntity;
import be.lambdaware.entities.StudentInfoEntity;
import be.lambdaware.entities.UserClassEntity;
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
@RequestMapping("/user_class")
public class UserClassTestController {


        @Autowired
        private ApplicationContext context;
        @Autowired
        private UserClassDAO userClassDAO;
        @Autowired
        private StudentInfoDAO studentInfoDAO;
        @Autowired
        private ClassesDAO classesDAO;



    @CrossOrigin
        @RequestMapping(method = RequestMethod.POST)
        public ResponseEntity<UserClassEntity> create(@RequestParam(value="studentInfoId") Integer studentInfoId, @RequestParam(value="classId") Integer classId) {

            //TODO process when dao.create fails with SQL Exception

            StudentInfoEntity studentInfo = studentInfoDAO.getById(studentInfoId);
            ClassEntity classEntity = classesDAO.get(classId);

            UserClassEntity userClass = new UserClassEntity(studentInfo, classEntity);

            // create an entity using the DAO
            userClassDAO.create(userClass);

            // return entity
            return new ResponseEntity<UserClassEntity>(userClass, HttpStatus.OK);
        }

        @CrossOrigin
        @RequestMapping(method = RequestMethod.GET)
        public ResponseEntity<UserClassEntity> get(@RequestParam(value="studentInfoId") Integer studentInfoId, @RequestParam(value="classId") Integer classId) {

            StudentInfoEntity studentInfo = studentInfoDAO.getById(studentInfoId);
            ClassEntity classEntity = classesDAO.get(classId);

            UserClassEntity userClass = userClassDAO.get(studentInfo, classEntity);

            return new ResponseEntity<UserClassEntity>(userClass, HttpStatus.OK);
        }
    
    @CrossOrigin
    @RequestMapping(path = "/all",method = RequestMethod.GET)
    public ResponseEntity<List<UserClassEntity>> get() {

        List<UserClassEntity> certificates = userClassDAO.getAll();

        return new ResponseEntity<List<UserClassEntity>>(certificates, HttpStatus.OK);
    }
}
