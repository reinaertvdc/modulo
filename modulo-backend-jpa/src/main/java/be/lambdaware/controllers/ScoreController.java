package be.lambdaware.controllers;

import be.lambdaware.enums.ScoreType;
import be.lambdaware.enums.UserRole;
import be.lambdaware.models.*;
import be.lambdaware.repos.*;
import be.lambdaware.response.Responses;
import be.lambdaware.security.APIAuthentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/score")
@CrossOrigin
public class ScoreController {

    private static Logger log = Logger.getLogger(CertificateController.class);
    @Autowired
    CertificateRepo certificateRepo;
    @Autowired
    StudentInfoRepo studentInfoRepo;
    @Autowired
    ClassRepo classRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    CourseTopicRepo courseTopicRepo;

    @Autowired
    BGVScoreRepo bgvScoreRepo;
    @Autowired
    PAVScoreRepo pavScoreRepo;
    @Autowired
    TaskScoreRepo taskScoreRepo;

    @Autowired
    CompetenceRepo competenceRepo;
    @Autowired
    ObjectiveRepo objectiveRepo;

    @Autowired
    APIAuthentication authentication;

    // ===================================================================================
    // GET methods
    // ===================================================================================

    @RequestMapping(value = "/id/{userId}/bgv")
    public ResponseEntity<?> getAllBGVScoresFromUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long userId) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        User user = userRepo.findById(userId);
        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;


        StudentInfo info = user.getStudentInfo();

        if (info == null) return Responses.STUDENT_INFO_NOT_FOUND;

        List<BGVScore> bgvScores = bgvScoreRepo.findAllByStudentInfoOrderByWeekAsc(info);

        if (bgvScores.size() == 0) return Responses.BGV_SCORES_NOT_FOUND;

        return new ResponseEntity<>(bgvScores, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{userId}/all")
    public ResponseEntity<?> getAllScores(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long userId) {
        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        User user = userRepo.findOne(userId);
        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        StudentInfo info = user.getStudentInfo();
        if (info == null) return Responses.STUDENT_INFO_NOT_FOUND;

        List<BGVScore> bgvScores = bgvScoreRepo.findAllByStudentInfoOrderByWeekAsc(info);
        List<PAVScore> pavScores = pavScoreRepo.findAllByStudentInfoOrderByWeekAsc(info);

        Certificate certificate = info.getCertificate();
        HashMap<Object, Object> subcertificates = new HashMap<>();
        for (SubCertificate subCertificate : certificate.getSubCertificates()) {
            HashMap<Object, Object> subcertificateInfo = new HashMap<>();
            int competenceCount = 0;
            int numPassed = 0;
            int totalAqcuired = 0;
            int totalOffered = 0;
            int totalPractied = 0;
            for (SubCertificateCategory certificateCategory : subCertificate.getSubCertificateCategories()) {
                for (Competence competence : certificateCategory.getCompetences()) {
                    competenceCount++;
                    HashMap<Object, Object> competenceInfo = new HashMap<>();
                    int acquiredCount = 0;
                    int practicedCount = 0;
                    int offeredCount = 0;

                    if (competence.getCustomName() != null) {
                        competenceInfo.put("name", competence.getSubCertificateCategory().getName() + " " + competence.getCustomName());
                    } else {
                        competenceInfo.put("name", competence.getSubCertificateCategory().getName() + " " + competence.getName());
                    }

                    for (BGVScore bgvScore : bgvScores) {
                        if (bgvScore.getCompetence().equals(competence)) {
                            if (bgvScore.getScore() == ScoreType.A) {
                                totalOffered++;
                                offeredCount++;
                            }
                            if (bgvScore.getScore() == ScoreType.I) {
                                totalPractied++;
                                practicedCount++;
                            }
                            if (bgvScore.getScore() == ScoreType.V) {
                                totalAqcuired++;
                                acquiredCount++;
                            }
                        }
                    }
                    if (acquiredCount > 0) {
                        log.info("Match on " + competence.getName() + " " + competence.getSubCertificateCategory().getSubCertificate().getName());
                        log.info("This competence : " + competence);
                    }

                    competenceInfo.put("A", offeredCount);
                    competenceInfo.put("I", practicedCount);
                    competenceInfo.put("V", acquiredCount);
                    if ((int) competenceInfo.get("V") >= 3) {
                        numPassed++;
                    }
//                    subcertificateInfo.put(competence.getId(), competenceInfo);
                }
            }
            subcertificateInfo.put("total", competenceCount);
            subcertificateInfo.put("passed", numPassed);
            subcertificateInfo.put("failed", (competenceCount - numPassed));
            subcertificateInfo.put("total_acquired", totalAqcuired);
            subcertificateInfo.put("total_practiced", totalPractied);
            subcertificateInfo.put("total_offered", totalOffered);
            subcertificates.put(subCertificate.getName(), subcertificateInfo);
        }


        HashMap<Object, Object> response = new HashMap<>();
        response.put("pav", pavScores);
        response.put("bgv", subcertificates);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{userId}/pav")
    public ResponseEntity<?> getAllPAVScoresFromUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long userId) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin() && !authentication.isTeacher()) return Responses.UNAUTHORIZED;

        User user = userRepo.findById(userId);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        StudentInfo info = user.getStudentInfo();

        if (info == null) return Responses.STUDENT_INFO_NOT_FOUND;

        List<PAVScore> pavScores = pavScoreRepo.findAllByStudentInfoOrderByWeekAsc(info);

        if (pavScores.size() == 0) return Responses.PAV_SCORES_NOT_FOUND;

        return new ResponseEntity<>(pavScores, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{userId}/tasks")
    public ResponseEntity<?> getAllTaskScoresFromUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long userId) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isStudent()) return Responses.UNAUTHORIZED;

        User user = userRepo.findById(userId);
        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        // get all tasks: first all without upload, then all with upload; both sorted on deadline
        List<TaskScore> taskScores = taskScoreRepo.findAllByUserOrderByTaskDeadlineAsc(user);
        List<TaskScore> uploaded = new ArrayList<TaskScore>();
        List<TaskScore> notUploaded = new ArrayList<TaskScore>();
        for (TaskScore score : taskScores) {
            if (score.getFileName() != null)
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

        User user = userRepo.getOne(userId);
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        Competence competence = competenceRepo.findById(competenceId);
        if (competence == null) return Responses.COMPETENCE_NOT_FOUND;

        BGVScore newBgvScore = new BGVScore(bgvScore.getScore(), bgvScore.getWeek(), bgvScore.getRemarks());
        newBgvScore.setCompetence(competence);
        newBgvScore.setStudentInfo(user.getStudentInfo());
        bgvScoreRepo.saveAndFlush(newBgvScore);

        return new ResponseEntity<>(newBgvScore, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{userId}/pav/{courseTopicId}/{objectiveId}", method = RequestMethod.POST)
    public ResponseEntity<?> addPAVScoreForUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long userId, @PathVariable long courseTopicId, @PathVariable long objectiveId, @RequestBody PAVScore pavScore) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin() && !authentication.isTeacher()) return Responses.UNAUTHORIZED;

        User user = userRepo.getOne(userId);
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        CourseTopic courseTopic = courseTopicRepo.findById(courseTopicId);
        if (courseTopic == null) return Responses.COURSE_TOPIC_NOT_FOUND;

        Objective objective = objectiveRepo.findById(objectiveId);
        if (objective == null) return Responses.OBJECTIVE_NOT_FOUND;

        PAVScore newPavScore = new PAVScore(pavScore.getScore(), pavScore.getWeek(), pavScore.getRemarks());
        newPavScore.setObjective(objective);
        newPavScore.setStudentInfo(user.getStudentInfo());
        pavScoreRepo.saveAndFlush(newPavScore);

        return new ResponseEntity<>(newPavScore, HttpStatus.OK);
    }
}
