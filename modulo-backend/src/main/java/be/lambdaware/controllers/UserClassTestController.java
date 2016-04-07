package be.lambdaware.controllers;

import be.lambdaware.dao.ClassesDAO;
import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.dao.UserClassDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//        @CrossOrigin
//        @RequestMapping(method = RequestMethod.POST)
//        public ResponseEntity<UserClassEntity> create(@RequestParam(value="student_id") Integer student_id, @RequestParam(value="class_id") Integer class_id) {
//
//            //TODO process when dao.create fails with SQL Exception
//
//            StudentInfoEntity studentInfo = studentInfoDAO..get(student_id);
//            ClassEntity classEntity = classesDAO.get(class_id);
//
//            UserClassEntity userClass = new UserClassEntity(studentInfo, classEntity);
//
//            // create an entity using the DAO
//            userClassDAO.create(userClass);
//
//            // return entity
//            return new ResponseEntity<UserClassEntity>(userClass, HttpStatus.OK);
//        }

//        @CrossOrigin
//        @RequestMapping(method = RequestMethod.GET)
//        public ResponseEntity<UserClassEntity> get(@RequestParam(value="student_id") Integer student_id, @RequestParam(value="class_id") Integer class_id) {
//
//            StudentInfoEntity studentInfo = studentInfoDAO.get(student_id);
//            ClassEntity classEntity = classesDAO.get(class_id);
//
//            UserClassEntity userClass = userClassDAO.get(studentInfo, classEntity);
//
//            return new ResponseEntity<UserClassEntity>(userClass, HttpStatus.OK);
//        }
    /*
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserClassEntity>> get() {

        List<UserClassEntity> certificates = userClassDAO.getAll();

        return new ResponseEntity<List<UserClassEntity>>(certificates, HttpStatus.OK);
    }
    */
}
