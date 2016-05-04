package be.lambdaware.controllers;

import be.lambdaware.dao.ClassDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.enums.ClassType;
import be.lambdaware.enums.UserRole;
import be.lambdaware.models.*;
import be.lambdaware.response.Responses;
import be.lambdaware.security.APIAuthentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * {@link ClassController} is the controller that is mapped to "/class" and handles all class related API calls.
 *
 * @author Hendrik Lievens - 1130921
 */
@RestController
@RequestMapping("/class")
@CrossOrigin
public class ClassController {

    private static Logger log = Logger.getLogger(ClassController.class);
    @Autowired
    ClassDAO classDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    APIAuthentication authentication;

    // ===================================================================================
    // GET methods
    // ===================================================================================

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        List<Clazz> classes = classDAO.findAll();

        if (classes.size() == 0) return Responses.CLASSES_NOT_FOUND;
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @RequestMapping(value = "/type/{type}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByType(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable ClassType type) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        List<Clazz> classes = classDAO.findAllByType(type);

        if (classes.size() == 0) return Responses.CLASSES_NOT_FOUND;
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classDAO.findById(id);

        if (clazz == null) return Responses.CLASS_NOT_FOUND;
        return new ResponseEntity<>(clazz, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/teacher", method = RequestMethod.GET)
    public ResponseEntity<?> getTeacherFromClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classDAO.findById(id);

        if (clazz == null) return Responses.CLASS_NOT_FOUND;

        User teacher = clazz.getTeacher();
        if (teacher == null) return Responses.USER_NOT_FOUND;
        return new ResponseEntity<>(teacher, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/students", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentsFromClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classDAO.findById(id);

        if (clazz == null) return Responses.CLASS_NOT_FOUND;

        List<User> students = clazz.getStudents();

        if (students.size() == 0) return Responses.USERS_NOT_FOUND;
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/certificate", method = RequestMethod.GET)
    public ResponseEntity<?> getCertificateFromClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classDAO.findById(id);

        if (clazz == null) return Responses.CLASS_NOT_FOUND;

        Certificate certificate = clazz.getCertificate();

        if (certificate == null) return Responses.CERTIFICATE_NOT_FOUND;
        return new ResponseEntity<>(certificate, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/grade", method = RequestMethod.GET)
    public ResponseEntity<?> getGradeFromClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classDAO.findById(id);

        if (clazz == null) return Responses.CLASS_NOT_FOUND;

        Grade grade = clazz.getGrade();

        if (grade == null) return Responses.CERTIFICATE_NOT_FOUND;
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    // ===================================================================================
    // POST methods
    // ===================================================================================

    @RequestMapping(value = "/id/{id}/student/{studentId}", method = RequestMethod.POST)
    public ResponseEntity<?> addStudentToClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id, @PathVariable long studentId) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classDAO.findById(id);
        if (clazz == null) return Responses.CLASS_NOT_FOUND;

        User user = userDAO.findById(studentId);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        if (!clazz.getStudents().contains(user)) {
            clazz.getStudents().add(user);
            classDAO.saveAndFlush(clazz);
        }

        return Responses.CLASS_ADDED_STUDENT;
    }

    @RequestMapping(value = "/id/{id}/teacher/{teacherId}", method = RequestMethod.POST)
    public ResponseEntity<?> addTeacherToClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id, @PathVariable long teacherId) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classDAO.findById(id);
        if (clazz == null) return Responses.CLASS_NOT_FOUND;

        User teacher = userDAO.findById(teacherId);

        if (teacher == null) return Responses.USER_NOT_FOUND;
        if (teacher.getRole() != UserRole.TEACHER) return Responses.USER_NOT_TEACHER;

        clazz.setTeacher(teacher);
        classDAO.saveAndFlush(clazz);

        return Responses.CLASS_ADDED_TEACHER;
    }
}
