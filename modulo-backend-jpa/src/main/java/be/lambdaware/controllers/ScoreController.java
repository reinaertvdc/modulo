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

import java.util.ArrayList;
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
    CourseTopicDAO courseTopicDAO;


    @Autowired
    BGVScoreDAO bgvScoreDAO;
    @Autowired
    PAVScoreDAO pavScoreDAO;
    @Autowired
    TaskScoreDAO taskScoreDAO;

    @Autowired
    CompetenceDAO competenceDAO;
    @Autowired
    ObjectiveDAO objectiveDAO;

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
        if (!authentication.isStudent()) return Responses.UNAUTHORIZED;

        User user = userDAO.findById(userId);
        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        // get all tasks: first all without upload, then all with upload; both sorted on deadline
        List<TaskScore> taskScores = taskScoreDAO.findAllByUserOrderByTaskDeadlineAsc(user);
        List<TaskScore> uploaded = new ArrayList<TaskScore>();
        List<TaskScore> notUploaded = new ArrayList<TaskScore>();
        for (TaskScore score : taskScores) {
            if(score.getFileName() != null)
                uploaded.add(score);
            else
                notUploaded.add(score);
        }
        taskScores.clear();
        taskScores.addAll(notUploaded);
        taskScores.addAll(uploaded);

        return new ResponseEntity<>(taskScores, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{userId}/bgv/{competenceId}", method = RequestMethod.POST)
    public ResponseEntity<?> addBGVScoreForUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long userId, @PathVariable long competenceId, @RequestBody BGVScore bgvScore) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin() && !authentication.isTeacher()) return Responses.UNAUTHORIZED;

        User user = userDAO.getOne(userId);
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        Competence competence = competenceDAO.findById(competenceId);
        if (competence == null) return Responses.COMPETENCE_NOT_FOUND;

        BGVScore newBgvScore = new BGVScore(bgvScore.getScore(), bgvScore.getWeek(), bgvScore.getRemarks());
        newBgvScore.setCompetence(competence);
        newBgvScore.setStudentInfo(user.getStudentInfo());
        bgvScoreDAO.saveAndFlush(newBgvScore);

        return new ResponseEntity<>(newBgvScore, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{userId}/pav/{courseTopicId}/{objectiveId}", method = RequestMethod.POST)
    public ResponseEntity<?> addPAVScoreForUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long userId, @PathVariable long courseTopicId, @PathVariable long objectiveId, @RequestBody PAVScore pavScore) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin() && !authentication.isTeacher()) return Responses.UNAUTHORIZED;

        User user = userDAO.getOne(userId);
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        CourseTopic courseTopic = courseTopicDAO.findById(courseTopicId);
        if (courseTopic == null) return Responses.COURSE_TOPIC_NOT_FOUND;

        Objective objective = objectiveDAO.findById(objectiveId);
        if (objective == null) return Responses.OBJECTIVE_NOT_FOUND;

        PAVScore newPavScore = new PAVScore(pavScore.getScore(), pavScore.getWeek(), pavScore.getRemarks());
        newPavScore.setObjective(objective);
        newPavScore.setStudentInfo(user.getStudentInfo());
        pavScoreDAO.saveAndFlush(newPavScore);

        return new ResponseEntity<>(newPavScore, HttpStatus.OK);
    }
}
