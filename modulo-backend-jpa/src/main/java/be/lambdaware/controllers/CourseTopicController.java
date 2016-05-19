package be.lambdaware.controllers;

import be.lambdaware.repos.*;
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

/**
 * Created by jensv on 11-May-16.
 */

@RestController
@RequestMapping("/coursetopic")
@CrossOrigin
public class CourseTopicController {

    private static Logger log = Logger.getLogger(CourseTopicController.class);
    @Autowired
    CourseTopicRepo courseTopicRepo;
    @Autowired
    GradeRepo gradeRepo;
    @Autowired
    ClassRepo classRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    ObjectiveRepo objectiveRepo;
    @Autowired
    PAVScoreRepo pavScoreRepo;

    @Autowired
    APIAuthentication authentication;


    // ===================================================================================
    // GET methods
    // ===================================================================================

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        List<CourseTopic> classes = courseTopicRepo.findAll();

        if (classes.size() == 0) return Responses.COURSE_TOPICS_NOT_FOUND;
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/students", method = RequestMethod.GET)
    public ResponseEntity<?> getStudents(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,
                                         @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        CourseTopic courseTopic = courseTopicRepo.findById(id);

        if (courseTopic == null) return Responses.COURSE_TOPICS_NOT_FOUND;
        return new ResponseEntity<>(courseTopic.getStudents(), HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/objectives", method = RequestMethod.GET)
    public ResponseEntity<?> getObjectives(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,
                                           @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        CourseTopic courseTopic = courseTopicRepo.findById(id);

        if (courseTopic == null) return Responses.COURSE_TOPICS_NOT_FOUND;
        return new ResponseEntity<>(courseTopic.getObjectives(), HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable
            long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        CourseTopic courseTopic = courseTopicRepo.findById(id);

        if (courseTopic == null) return Responses.COURSE_TOPICS_NOT_FOUND;
        return new ResponseEntity<>(courseTopic, HttpStatus.OK);
    }


    // ===================================================================================
    // POST methods
    // ===================================================================================

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createCourseTopic(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,
                                               @RequestBody CourseTopic courseTopic) {
        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        Clazz clazz = classRepo.findById(courseTopic.getClazz().getId());
        Grade grade = gradeRepo.findById(courseTopic.getGrade().getId());

        courseTopic.setClazz(clazz);
        courseTopic.setGrade(grade);

        courseTopicRepo.saveAndFlush(courseTopic);

        return new ResponseEntity<>(courseTopic, HttpStatus.OK);
    }

    @RequestMapping(value = "/students/{courseTopicId}", method = RequestMethod.POST)
    public ResponseEntity<?> setStudents(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,
                                         @RequestBody ArrayList<User> students, @PathVariable long courseTopicId) {
        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        CourseTopic courseTopic = courseTopicRepo.findById(courseTopicId);
        for (User student : students) {
            User stud = userRepo.findById(student.getId());
            courseTopic.addStudent(stud);
        }

        courseTopicRepo.saveAndFlush(courseTopic);

        return new ResponseEntity<>(courseTopic, HttpStatus.OK);
    }


    @RequestMapping(value = "/objectives/{courseTopicId}", method = RequestMethod.POST)
    public ResponseEntity<?> setObjectives(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,
                                           @RequestBody ArrayList<Objective> objectives, @PathVariable long
                                                       courseTopicId) {
        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        CourseTopic courseTopic = courseTopicRepo.findById(courseTopicId);
        for (Objective obj : objectives) {
            Objective objective = objectiveRepo.findById(obj.getId());
            courseTopic.addObjective(objective);
        }

        courseTopicRepo.saveAndFlush(courseTopic);

        return new ResponseEntity<>(courseTopic, HttpStatus.OK);
    }


    // ===================================================================================
    // DELETE methods
    // ===================================================================================

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,
                                    @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()  && !authentication.isAdmin()) return Responses.UNAUTHORIZED;

        CourseTopic courseTopic = courseTopicRepo.findById(id);
        if (courseTopic == null) return Responses.COURSE_TOPIC_NOT_FOUND;

        CourseTopic topic = courseTopicRepo.findOne(id);
        log.info("Trying to delete " + topic);
        log.info("Course topic has " + topic.getPavScores().size() + " scores");

        for(PAVScore score : topic.getPavScores()){
            PAVScore pavScore = pavScoreRepo.getOne(score.getId());
            pavScore.setStudentInfo(null);
            log.info("Deleting " + pavScore);
            pavScoreRepo.saveAndFlush(pavScore);
        }

        courseTopicRepo.delete(id);
        return Responses.COURSE_TOPIC_DELETED;
    }
}