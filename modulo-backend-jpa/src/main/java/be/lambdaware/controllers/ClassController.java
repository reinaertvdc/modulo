package be.lambdaware.controllers;

import be.lambdaware.repos.*;
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

import java.util.ArrayList;
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
    ClassRepo classRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    CertificateRepo certificateRepo;
    @Autowired
    GradeRepo gradeRepo;
    @Autowired
    StudentInfoRepo studentInfoRepo;
    @Autowired
    APIAuthentication authentication;

    // ===================================================================================
    // GET methods
    // ===================================================================================

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        List<Clazz> classes = classRepo.findAll();

        if (classes.size() == 0) return Responses.CLASSES_NOT_FOUND;
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @RequestMapping(value = "/type/{type}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByType(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable ClassType type) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        List<Clazz> classes = classRepo.findAllByType(type);

        if (classes.size() == 0) return Responses.CLASSES_NOT_FOUND;
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classRepo.findById(id);

        if (clazz == null) return Responses.CLASS_NOT_FOUND;
        return new ResponseEntity<>(clazz, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/teacher", method = RequestMethod.GET)
    public ResponseEntity<?> getTeacherFromClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classRepo.findById(id);

        if (clazz == null) return Responses.CLASS_NOT_FOUND;

        User teacher = clazz.getTeacher();
        if (teacher == null) return Responses.USER_NOT_FOUND;
        return new ResponseEntity<>(teacher, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/students", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentsFromClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classRepo.findById(id);

        if (clazz == null) return Responses.CLASS_NOT_FOUND;

        List<User> students = clazz.getStudents();

        if (students.size() == 0) return Responses.USERS_NOT_FOUND;
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/certificate", method = RequestMethod.GET)
    public ResponseEntity<?> getCertificateFromClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classRepo.findById(id);

        if (clazz == null) return Responses.CLASS_NOT_FOUND;

        Certificate certificate = clazz.getCertificate();

        if (certificate == null) return Responses.CERTIFICATE_NOT_FOUND;
        return new ResponseEntity<>(certificate, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/grade", method = RequestMethod.GET)
    public ResponseEntity<?> getGradeFromClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classRepo.findById(id);

        if (clazz == null) return Responses.CLASS_NOT_FOUND;

        Grade grade = clazz.getGrade();

        if (grade == null) return Responses.CERTIFICATE_NOT_FOUND;
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/type", method = RequestMethod.GET)
    public ResponseEntity<?> getTypeFromClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classRepo.findById(id);

        if (clazz == null) return Responses.CLASS_NOT_FOUND;

        ClassType type = clazz.getType();

        if (type == null) return Responses.CLASS_TYPE_NOT_FOUND;
        return new ResponseEntity<>(type, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/coursetopics", method = RequestMethod.GET)
    public ResponseEntity<?> getCourseTopicsFromClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classRepo.findById(id);

        if (clazz == null) return Responses.CLASS_NOT_FOUND;

        List<CourseTopic> courseTopics = clazz.getCourseTopics();

        if (courseTopics.size() == 0) return Responses.COURSE_TOPICS_NOT_FOUND;
        return new ResponseEntity<>(courseTopics, HttpStatus.OK);
    }

    // ===================================================================================
    // POST methods
    // ===================================================================================

    @RequestMapping(value = "/teacher/id/{teacherId}/certificate/id/{certificateId}", method = RequestMethod.POST)
    public ResponseEntity<?> createClassByTeacherAndCertificate(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long teacherId, @PathVariable long certificateId, @RequestBody Clazz clazz) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        User teacher = userRepo.findById(teacherId);

        if(teacher == null)
            return Responses.USERS_NOT_FOUND;
        if(teacher.getRole() != UserRole.TEACHER)
            return Responses.USER_NOT_TEACHER;

        Certificate certificate = certificateRepo.findById(certificateId);
        if(certificate == null)
            return Responses.CERTIFICATE_NOT_FOUND;

        if(clazz.getType() != ClassType.BGV)
            return Responses.CLASS_TYPE_NOT_CORRECT;

        Clazz newClazz = new Clazz(clazz.getName(), clazz.getType());
        newClazz.setTeacher(teacher);
        newClazz.setCertificate(certificate);
        classRepo.saveAndFlush(newClazz);

        return new ResponseEntity<>(newClazz, HttpStatus.OK);
    }

    @RequestMapping(value = "/teacher/id/{teacherId}/grade/id/{gradeId}", method = RequestMethod.POST)
    public ResponseEntity<?> createClassByTeacherAndGrade(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long teacherId, @PathVariable long gradeId, @RequestBody Clazz clazz) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        User teacher = userRepo.findById(teacherId);

        if(teacher == null)
            return Responses.USERS_NOT_FOUND;
        if(teacher.getRole() != UserRole.TEACHER)
            return Responses.USER_NOT_TEACHER;

        Grade grade = gradeRepo.findById(gradeId);
        if(grade == null)
            return Responses.GRADE_NOT_FOUND;

        if(clazz.getType() != ClassType.PAV)
            return Responses.CLASS_TYPE_NOT_CORRECT;

        Clazz newClazz = new Clazz(clazz.getName(), clazz.getType());
        newClazz.setTeacher(teacher);
        newClazz.setGrade(grade);
        classRepo.saveAndFlush(newClazz);

        return new ResponseEntity<>(newClazz, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/student/{studentId}", method = RequestMethod.POST)
    public ResponseEntity<?> addStudentToClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id, @PathVariable long studentId) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classRepo.findById(id);
        if (clazz == null) return Responses.CLASS_NOT_FOUND;

        User user = userRepo.findById(studentId);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        if (!clazz.getStudents().contains(user)) {
            clazz.getStudents().add(user);
            classRepo.saveAndFlush(clazz);
        }
        else{
            log.info("Student with ID: " + user.getId() + " already in class");
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/students/certificate/id/{certificateId}", method = RequestMethod.POST)
    public ResponseEntity<?> addStudentsFromClassByCertificate(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id, @PathVariable long certificateId) {
        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classRepo.findById(id);
        if (clazz == null) return Responses.CLASS_NOT_FOUND;

        Certificate certificate = certificateRepo.findById(certificateId);

        if (certificate == null) return Responses.CERTIFICATE_NOT_FOUND;

        List<StudentInfo> studentInfoList = studentInfoRepo.findAllByCertificate(certificate);

        if (studentInfoList.size() == 0) return Responses.STUDENT_INFO_NOT_FOUND;

        List<User> students = new ArrayList<>();
        for (StudentInfo studentInfo: studentInfoList) {
            User student = studentInfo.getUser();

            if (student == null) return Responses.USER_NOT_FOUND;

            students.add(student);
        }

        for (User student: students) {
            if (student.getRole() == UserRole.STUDENT) {

                if (!clazz.getStudents().contains(student)) {
                    clazz.getStudents().add(student);
                    classRepo.saveAndFlush(clazz);
                }
            }
        }

        return Responses.CLASS_ADDED_STUDENTS;
    }

    @RequestMapping(value = "/id/{id}/teacher/{teacherId}", method = RequestMethod.POST)
    public ResponseEntity<?> addTeacherToClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id, @PathVariable long teacherId) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classRepo.findById(id);
        if (clazz == null) return Responses.CLASS_NOT_FOUND;

        User teacher = userRepo.findById(teacherId);

        if (teacher == null) return Responses.USER_NOT_FOUND;
        if (teacher.getRole() != UserRole.TEACHER) return Responses.USER_NOT_TEACHER;

        clazz.setTeacher(teacher);
        classRepo.saveAndFlush(clazz);

        return Responses.CLASS_ADDED_TEACHER;
    }

    // ===================================================================================
    // PUT methods
    // ===================================================================================

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> updateClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @RequestBody Clazz clazz) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;


        Clazz databseClazz = classRepo.findById(clazz.getId());
        databseClazz.setName(clazz.getName());

        classRepo.saveAndFlush(databseClazz);

        return new ResponseEntity<>(databseClazz, HttpStatus.OK);
    }

    //TODO delete user from class

    // ===================================================================================
    // DELETE methods
    // ===================================================================================

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classRepo.findById(id);
        if (clazz == null) return Responses.CLASS_NOT_FOUND;

        clazz.clearStudents();
        classRepo.saveAndFlush(clazz);

        classRepo.delete(clazz);


        return Responses.CLASS_DELETED;
    }

    @RequestMapping(value = "/id/{id}/student/{studentId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStudentFromClass(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id, @PathVariable long studentId) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classRepo.findById(id);
        if (clazz == null) return Responses.CLASS_NOT_FOUND;

        User student = userRepo.findById(studentId);

        if (student == null) return Responses.USER_NOT_FOUND;
        if (student.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        if(clazz.getStudents().contains(student)){
            clazz.getStudents().remove(student);
            classRepo.saveAndFlush(clazz);

            student.getClasses().remove(clazz);
            userRepo.saveAndFlush(student);
        }

        return Responses.CLASS_DELETED_STUDENT;
    }


    @RequestMapping(value = "/id/{id}/students/certificate/id/{certificateId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStudentsFromClassByCertificate(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id, @PathVariable long certificateId) {
        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Clazz clazz = classRepo.findById(id);
        if (clazz == null) return Responses.CLASS_NOT_FOUND;

        Certificate certificate = certificateRepo.findById(certificateId);

        if (certificate == null) return Responses.CERTIFICATE_NOT_FOUND;

        List<StudentInfo> studentInfoList = studentInfoRepo.findAllByCertificate(certificate);

        if (studentInfoList.size() == 0) return Responses.STUDENT_INFO_NOT_FOUND;

        List<User> students = new ArrayList<>();
        for (StudentInfo studentInfo: studentInfoList) {
            User student = studentInfo.getUser();

            if (student == null) return Responses.USER_NOT_FOUND;

            students.add(student);
        }

        for (User student: students) {
            if (student.getRole() == UserRole.STUDENT) {

                if (clazz.getStudents().contains(student)) {
                    clazz.getStudents().remove(student);
                    classRepo.saveAndFlush(clazz);
                }
            }
        }

        return Responses.CLASS_DELETED_STUDENTS;
    }
}
