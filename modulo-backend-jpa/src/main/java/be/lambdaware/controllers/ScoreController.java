package be.lambdaware.controllers;

import be.lambdaware.dao.*;
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

@RestController
@RequestMapping("/score")
@CrossOrigin
public class ScoreController {

    private static Logger log = Logger.getLogger(CertificateController.class);
    @Autowired
    CertificateDAO certificateDAO;
    @Autowired
    StudentInfoDAO studentInfoDAO;
    @Autowired
    ClassDAO classDAO;
    @Autowired
    UserDAO userDAO;


    @Autowired
    BGVScoreDAO bgvScoreDAO;
    @Autowired
    PAVScoreDAO pavScoreDAO;
    @Autowired
    TaskScoreDAO taskScoreDAO;

    @Autowired
    APIAuthentication authentication;

    // ===================================================================================
    // GET methods
    // ===================================================================================

    @RequestMapping(value = "/id/{userId}/bgv")
    public ResponseEntity<?> getAllBGVScoresFromUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long userId) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin() && !authentication.isTeacher()) return Responses.UNAUTHORIZED;

        User user = userDAO.findById(userId);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;




        StudentInfo info = user.getStudentInfo();

        if (info == null) return Responses.STUDENT_INFO_NOT_FOUND;

        List<BGVScore> bgvScores = bgvScoreDAO.findAllByStudentInfoOrderByWeekAsc(info);

        if (bgvScores.size() == 0) return Responses.BGV_SCORES_NOT_FOUND;

        return new ResponseEntity<>(bgvScores, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{userId}/pav")
    public ResponseEntity<?> getAllPAVScoresFromUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long userId) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin() && !authentication.isTeacher()) return Responses.UNAUTHORIZED;

        User user = userDAO.findById(userId);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        StudentInfo info = user.getStudentInfo();

        if (info == null) return Responses.STUDENT_INFO_NOT_FOUND;

        List<PAVScore> pavScores = pavScoreDAO.findAllByStudentInfoOrderByWeekAsc(info);

        if (pavScores.size() == 0) return Responses.PAV_SCORES_NOT_FOUND;

        return new ResponseEntity<>(pavScores, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{userId}/tasks")
    public ResponseEntity<?> getAllTaskScoresFromUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long userId) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        User user = userDAO.findById(userId);
        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        List<TaskScore> taskScores = taskScoreDAO.findAllByUser(user);
        return new ResponseEntity<>(taskScores, HttpStatus.OK);
    }
}
