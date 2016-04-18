package be.lambdaware.controllers.TestControllers;

import be.lambdaware.dao.*;
import be.lambdaware.model.TeacherModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Vincent on 15/04/16.
 */
@RestController
@RequestMapping("/user/teacher")
public class TeacherModelTestController {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ClassDAO classDAO;
    @Autowired
    private CertificateDAO certificateDAO;
    @Autowired
    private ClassCertificateDAO classCertificateDAO;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TeacherModel> create(@RequestBody TeacherModel teacherModel) {
        teacherModel.setUserDAO(userDAO);
        teacherModel.createInDB();


        return new ResponseEntity<TeacherModel>(teacherModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{teacherId}", method = RequestMethod.GET)
    public ResponseEntity<TeacherModel> get(@PathVariable Integer teacherId) {
        TeacherModel teacherModel = new TeacherModel(userDAO);
        teacherModel.getFromDB(teacherId);

        System.out.println(teacherModel);
        System.out.println(teacherModel.getClasses(classDAO, certificateDAO, classCertificateDAO));

        return new ResponseEntity<TeacherModel>(teacherModel, HttpStatus.OK);
//        return null;
    }
}
