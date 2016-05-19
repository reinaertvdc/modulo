package be.lambdaware.controllers;

import be.lambdaware.dao.*;
import be.lambdaware.models.*;
import be.lambdaware.response.Responses;
import be.lambdaware.security.APIAuthentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    CourseTopicDAO courseTopicDAO;
    @Autowired
    GradeDAO gradeDAO;
    @Autowired
    ClassDAO classDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    ObjectiveDAO objectiveDAO;
    @Autowired
    PAVScoreDAO pavScoreDAO;

    @Autowired
    APIAuthentication authentication;


    // ===================================================================================
    // GET methods
    // ===================================================================================

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        List<CourseTopic> classes = courseTopicDAO.findAll();

        if (classes.size() == 0) return Responses.COURSE_TOPICS_NOT_FOUND;
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/students", method = RequestMethod.GET)
    public ResponseEntity<?> getStudents(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,
                                         @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        CourseTopic courseTopic = courseTopicDAO.findById(id);

        if (courseTopic == null) return Responses.COURSE_TOPICS_NOT_FOUND;
        return new ResponseEntity<>(courseTopic.getStudents(), HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/objectives", method = RequestMethod.GET)
    public ResponseEntity<?> getObjectives(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,
                                           @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        CourseTopic courseTopic = courseTopicDAO.findById(id);

        if (courseTopic == null) return Responses.COURSE_TOPICS_NOT_FOUND;
        return new ResponseEntity<>(courseTopic.getObjectives(), HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/pavscores", method = RequestMethod.GET)
    public ResponseEntity<?> getPavScores(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,
                                           @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        CourseTopic courseTopic = courseTopicDAO.findById(id);

        if (courseTopic == null) return Responses.COURSE_TOPICS_NOT_FOUND;
        return new ResponseEntity<>(courseTopic.getPavScores(), HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable
            long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        CourseTopic courseTopic = courseTopicDAO.findById(id);

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

        CourseTopic topic = new CourseTopic();

        for (User student : courseTopic.getStudents()) {
            User stud = userDAO.findById(student.getId());
            topic.addStudent(stud);
        }

        Clazz clazz = classDAO.findById(courseTopic.getClazz().getId());
        Grade grade = gradeDAO.findById(courseTopic.getGrade().getId());

        topic.setResit(courseTopic.isResit());
        topic.setName(courseTopic.getName());
        topic.setDescription(courseTopic.getDescription());
        topic.setGrade(grade);
        topic.setClazz(clazz);

        for (Objective object: courseTopic.getObjectives()) {
            Objective obj = objectiveDAO.findById(object.getId());
            topic.addObjective(obj);
        }


        courseTopicDAO.saveAndFlush(topic);

        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    @RequestMapping(value = "/students/{courseTopicId}", method = RequestMethod.POST)
    public ResponseEntity<?> setStudents(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,
                                         @RequestBody ArrayList<User> students, @PathVariable long courseTopicId) {
        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        CourseTopic courseTopic = courseTopicDAO.findById(courseTopicId);
        for (User student : students) {
            User stud = userDAO.findById(student.getId());
            courseTopic.addStudent(stud);
        }

        courseTopicDAO.saveAndFlush(courseTopic);

        return new ResponseEntity<>(courseTopic, HttpStatus.OK);
    }


    @RequestMapping(value = "/objectives/{courseTopicId}", method = RequestMethod.POST)
    public ResponseEntity<?> setObjectives(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,
                                           @RequestBody ArrayList<Objective> objectives, @PathVariable long
                                                       courseTopicId) {
        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        CourseTopic courseTopic = courseTopicDAO.findById(courseTopicId);
        for (Objective obj : objectives) {
            Objective objective = objectiveDAO.findById(obj.getId());
            courseTopic.addObjective(objective);
        }

        courseTopicDAO.saveAndFlush(courseTopic);

        return new ResponseEntity<>(courseTopic, HttpStatus.OK);
    }


    // ===================================================================================
    // PUT methods
    // ===================================================================================

    @Transactional
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,
                                    @RequestBody CourseTopic courseTopic) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        CourseTopic topic = new CourseTopic();
        CourseTopic oldTopic = courseTopicDAO.findById(courseTopic.getId());
        List<User> students = oldTopic.getStudents();
        List<Objective> objectives = oldTopic.getObjectives();

       /* List<User> removeStud = new ArrayList<>();
        List<Objective> removeObj = new ArrayList<>();
        boolean found = false;
        /*for(User stud: students){
            for(User s: courseTopic.getStudents()){
                if(stud.getId() == s.getId())
                    found = true;

            }

            if(found)
                removeStud.add(stud);
            found = false;
        }

        for(Objective obj: objectives){
            for(Objective o: courseTopic.getObjectives()){
                if(obj.getId() == o.getId())
                    found = true;

            }

            if(found)
                removeObj.add(obj);
            found = false;
        }

        for(Objective o: removeObj){
            courseTopic.removeObjective(o);
        }

        for(User s: removeStud){
            courseTopic.removeStudent(s);
        }*/

        for (User student : courseTopic.getStudents()) {
            User stud = userDAO.findById(student.getId());
            topic.addStudent(stud);
        }

        Clazz clazz = classDAO.findById(courseTopic.getClazz().getId());
        Grade grade = gradeDAO.findById(courseTopic.getGrade().getId());

        topic.setId(courseTopic.getId());
        topic.setResit(courseTopic.isResit());
        topic.setName(courseTopic.getName());
        topic.setDescription(courseTopic.getDescription());
        topic.setGrade(grade);
        topic.setClazz(clazz);

        for (Objective object: courseTopic.getObjectives()) {
            Objective obj = objectiveDAO.findById(object.getId());
            topic.addObjective(obj);
        }

        for (PAVScore score: courseTopic.getPavScores()) {
            PAVScore PAV = pavScoreDAO.findById(score.getId());
            topic.addPavScore(PAV);
        }

        courseTopicDAO.saveAndFlush(topic);

        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    @RequestMapping(value = "/students/{courseTopicId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudents(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,
                                         @RequestBody ArrayList<User> students, @PathVariable long courseTopicId) {
        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        for (User student : students) {
            System.out.print(student.getId() + "\n");
        }

        CourseTopic courseTopic = courseTopicDAO.findById(courseTopicId);

        for (User student : students) {
            User stud = userDAO.findById(student.getId());
            courseTopic.addStudent(stud);
        }

        boolean found = false;
        List<User> studentList = courseTopic.getStudents();
        for(User user : studentList){
            for (User student : students) {
                if(user.getId() == student.getId())
                    found = true;
            }

            if(!found) {
                courseTopic.removeStudent(user);
                System.out.print("Remove: " + user.getId());
            }

            found = false;
        }


        courseTopicDAO.saveAndFlush(courseTopic);

        return new ResponseEntity<>(courseTopic, HttpStatus.OK);
    }


    @RequestMapping(value = "/objectives/{courseTopicId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateObjectives(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth,
                                           @RequestBody ArrayList<Objective> objectives, @PathVariable long courseTopicId) {
        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        CourseTopic courseTopic = courseTopicDAO.findById(courseTopicId);

        courseTopicDAO.saveAndFlush(courseTopic);

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

        CourseTopic courseTopic = courseTopicDAO.findById(id);
        if (courseTopic == null) return Responses.COURSE_TOPIC_NOT_FOUND;

        CourseTopic topic = courseTopicDAO.findOne(id);
        log.info("Trying to delete " + topic);
        log.info("Course topic has " + topic.getPavScores().size() + " scores");

        for(PAVScore score : topic.getPavScores()){
            PAVScore pavScore = pavScoreDAO.getOne(score.getId());
            pavScore.setStudentInfo(null);
            log.info("Deleting " + pavScore);
            pavScoreDAO.saveAndFlush(pavScore);
        }

        courseTopicDAO.delete(id);
        return Responses.COURSE_TOPIC_DELETED;
    }
}