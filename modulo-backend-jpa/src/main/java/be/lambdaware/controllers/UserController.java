package be.lambdaware.controllers;

import be.lambdaware.repos.*;
import be.lambdaware.enums.ClassType;
import be.lambdaware.enums.Sex;
import be.lambdaware.enums.UserRole;
import be.lambdaware.models.*;
import be.lambdaware.response.Responses;
import be.lambdaware.response.StringMessage;
import be.lambdaware.security.APIAuthentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link UserController} is the controller that is mapped to "/user" and handles all user related API calls.
 *
 * @author Hendrik Lievens - 1130921
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {


    @Autowired
    UserRepo userRepo;
    @Autowired
    ClassRepo classRepo;
    @Autowired
    StudentInfoRepo studentInfoRepo;
    @Autowired
    BGVScoreRepo bgvScoreRepo;
    @Autowired
    PAVScoreRepo pavScoreRepo;


    @Autowired
    APIAuthentication authentication;

    private static Logger log = Logger.getLogger(UserController.class);

    // ===================================================================================
    // GET methods
    // ===================================================================================

    /**
     * Find all {@link User} objects.
     *
     * @param auth a base64 encoding of "email:password" for authentication.
     * @return a {@link ResponseEntity} or {@link List<User>}.
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        List<User> users = userRepo.findAll();

        if (users.size() == 0) return Responses.USERS_NOT_FOUND;
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Find a {@link User} by id.
     *
     * @param auth a base64 encoding of "email:password" for authentication.
     * @param id   the user's id.
     * @return a {@link ResponseEntity} or {@link User}.
     */
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        User user = userRepo.findById(id);

        if (user == null) return Responses.USER_NOT_FOUND;
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Find a {@link User} by email.
     *
     * @param auth  a base64 encoding of "email:password" for authentication.
     * @param email the user's email.
     * @return a {@link ResponseEntity} or {@link User}.
     */
    @RequestMapping(value = "/email/{email:.+}", method = RequestMethod.GET)
    public ResponseEntity<?> getByEmail(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable String email) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        User user = userRepo.findByEmail(email);

        if (user == null) return Responses.USER_NOT_FOUND;
        else return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Find all {@link User} objects by first name.
     *
     * @param auth      a base64 encoding of "email:password" for authentication.
     * @param firstName the user's first name.
     * @return a {@link ResponseEntity} or {@link List<User>}.
     */
    @RequestMapping(value = "/firstname/{firstName}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByFirstName(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable String firstName) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        List<User> users = userRepo.findAllByFirstNameIgnoreCase(firstName);

        if (users.size() == 0) return Responses.USERS_NOT_FOUND;
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Find all {@link User} objects by last name.
     *
     * @param auth     a base64 encoding of "email:password" for authentication.
     * @param lastName the user's last name.
     * @return a {@link ResponseEntity} or {@link List<User>}.
     */
    @RequestMapping(value = "/lastname/{lastName}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByLastName(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable String lastName) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        List<User> users = userRepo.findAllByLastNameIgnoreCase(lastName);

        if (users.size() == 0) return Responses.USERS_NOT_FOUND;
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Find all {@link User} objects by role.
     *
     * @param auth a base64 encoding of "email:password" for authentication.
     * @param role the user's role.
     * @return a {@link ResponseEntity} or {@link List<User>}.
     */
    @RequestMapping(value = "/role/{role}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByRole(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable UserRole role) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        List<User> users = userRepo.findAllByRole(role);

        if (users.size() == 0) return Responses.USERS_NOT_FOUND;
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Find all {@link User} objects by enabled status.
     *
     * @param auth    a base64 encoding of "email:password" for authentication.
     * @param enabled the user's enabled status.
     * @return a {@link ResponseEntity} or {@link List<User>}.
     */
    @RequestMapping(value = "/enabled/{enabled}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByEnabled(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable boolean enabled) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        List<User> users = userRepo.findAllByEnabled(enabled);

        if (users.size() == 0) return Responses.USERS_NOT_FOUND;
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Find all {@link User} objects by parent.
     *
     * @param auth     a base64 encoding of "email:password" for authentication.
     * @param parentId the parent's id.
     * @return a {@link ResponseEntity} or {@link List<User>}.
     */
    @RequestMapping(value = "/id/{parentId}/children", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByParent(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long parentId) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        User user = userRepo.findById(parentId);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.PARENT) return Responses.USER_NOT_PARENT;

        List<User> users = user.getChildren();

        if (users.size() == 0) return Responses.USERS_NOT_FOUND;
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Find a {@link User} object by child.
     *
     * @param auth    a base64 encoding of "email:password" for authentication.
     * @param childId the user's id.
     * @return a {@link ResponseEntity} or {@link User}.
     */
    @RequestMapping(value = "/id/{childId}/parent", method = RequestMethod.GET)
    public ResponseEntity<?> getParentFromUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long childId) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        User user = userRepo.findById(childId);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        User parent = user.getParent();

        if (parent == null) return Responses.USER_NOT_FOUND;

        return new ResponseEntity<>(parent, HttpStatus.OK);
    }

    /**
     * Find a {@link StudentInfo} object by user.
     *
     * @param auth a base64 encoding of "email:password" for authentication.
     * @param id   the user's id.
     * @return a {@link ResponseEntity} or {@link StudentInfo} object.
     */
    @RequestMapping(value = "/id/{id}/info", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentInfoFromUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable int id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        User user = userRepo.findById(id);

        log.info(user);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        StudentInfo info = user.getStudentInfo();

        log.info(info);


        if (info == null) return Responses.USER_NO_INFO;
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    /**
     * Find all {@link Clazz} objects by user.
     *
     * @param auth a base64 encoding of "email:password" for authentication.
     * @param id   the user's id.
     * @return a {@link ResponseEntity} or {@link List<Clazz>}.
     */
    @RequestMapping(value = "/id/{id}/classes", method = RequestMethod.GET)
    public ResponseEntity<?> getClassesFromUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        User user = userRepo.findById(id);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        List<Clazz> classes = user.getClasses();

        if (classes.size() == 0) return Responses.USER_NO_CLASSES;
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    /**
     * Find a {@link Clazz} objects by user.
     *
     * @param auth a base64 encoding of "email:password" for authentication.
     * @param id   the class's id.
     * @return a {@link ResponseEntity} or {@link List<Clazz>}.
     */
    @RequestMapping(value = "/id/{id}/teaching", method = RequestMethod.GET)
    public ResponseEntity<?> getTeachedClasses(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        User user = userRepo.findById(id);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.TEACHER) return Responses.USER_NOT_TEACHER;

        List<Clazz> classes = user.getTeachedClasses();

        if (classes.size() == 0) return Responses.USER_NO_TEACHING;
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }


    @RequestMapping(value = "/id/{id}/teaching/type/{type}", method = RequestMethod.GET)
    public ResponseEntity<?> getTeachedClassesType(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id, @PathVariable ClassType type) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        User user = userRepo.findById(id);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.TEACHER) return Responses.USER_NOT_TEACHER;

        List<Clazz> classes = user.getTeachedClasses();
        if (classes.size() == 0) return Responses.USER_NO_TEACHING;

        List<Clazz> typeClasses = new ArrayList<Clazz>();
        for(Clazz clazz : classes) {
            if(clazz.getType() == type)
                typeClasses.add(clazz);
        }

        return new ResponseEntity<>(typeClasses, HttpStatus.OK);
    }

    /**
     * Finds a {@link User} by query.
     *
     * @param auth  a base64 encoding of "email:password" for authentication.
     * @param query a search query.
     * @return a {@link ResponseEntity} or {@link User} object.
     */
    @RequestMapping(value = "/search/{query}", method = RequestMethod.GET)
    public ResponseEntity<?> findUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable String query) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        List<User> records = new ArrayList<>();
        records.addAll(userRepo.findAllByFirstNameContainingIgnoreCase(query));
        records.addAll(userRepo.findAllByLastNameContainingIgnoreCase(query));

        if (records.size() == 0) return Responses.USERS_NOT_FOUND;
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/grade", method = RequestMethod.GET)
    public ResponseEntity<?> getGradeFromUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin() && !authentication.isTeacher()) return Responses.UNAUTHORIZED;

        User user = userRepo.findById(id);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        StudentInfo info = user.getStudentInfo();

        if (info == null) return Responses.STUDENT_INFO_NOT_FOUND;

        Grade grade = info.getGrade();

        if (grade == null) return Responses.GRADE_NOT_FOUND;

        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/certificate", method = RequestMethod.GET)
    public ResponseEntity<?> getCertificateFromUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin() && !authentication.isTeacher()) return Responses.UNAUTHORIZED;

        User user = userRepo.findById(id);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        StudentInfo info = user.getStudentInfo();

        if (info == null) return Responses.STUDENT_INFO_NOT_FOUND;

        Certificate certificate = info.getCertificate();

        if (certificate == null) return Responses.CERTIFICATE_NOT_FOUND;

        return new ResponseEntity<>(certificate, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/gradeId", method = RequestMethod.GET)
    public ResponseEntity<?> getGradeIdFromUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin() && !authentication.isTeacher()) return Responses.UNAUTHORIZED;

        User user = userRepo.findById(id);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        StudentInfo info = user.getStudentInfo();

        if (info == null) return Responses.STUDENT_INFO_NOT_FOUND;

        Grade grade = info.getGrade();

        if (grade == null) return Responses.GRADE_NOT_FOUND;

        return new ResponseEntity<>(grade.getId(), HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/certificateId", method = RequestMethod.GET)
    public ResponseEntity<?> getCertificateIdFromUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin() && !authentication.isTeacher()) return Responses.UNAUTHORIZED;

        User user = userRepo.findById(id);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        StudentInfo info = user.getStudentInfo();

        if (info == null) return Responses.STUDENT_INFO_NOT_FOUND;

        Certificate certificate = info.getCertificate();

        if (certificate == null) return Responses.CERTIFICATE_NOT_FOUND;

        return new ResponseEntity<>(certificate.getId(), HttpStatus.OK);
    }

    // ===================================================================================
    // POST methods
    // ===================================================================================


    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public ResponseEntity<?> createAdmin(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @RequestBody MultiValueMap<String, String> form) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        String email = form.getFirst("email").toLowerCase();
        String firstName = form.getFirst("firstName");
        String lastName = form.getFirst("lastName");
        String password = form.getFirst("password");
        Sex sex = form.getFirst("sex").equals("MALE") ? Sex.MALE : Sex.FEMALE;
        UserRole role = UserRole.ADMIN;

        if (userRepo.findByEmail(email) != null) return Responses.USER_EMAIL_EXISTS;

        User user = new User(email, authentication.SHA512(password), firstName, lastName, sex, role, true);
        userRepo.saveAndFlush(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/parent", method = RequestMethod.POST)
    public ResponseEntity<?> createParent(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @RequestBody MultiValueMap<String, String> form) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        String email = form.getFirst("email").toLowerCase();
        String firstName = form.getFirst("firstName");
        String lastName = form.getFirst("lastName");
        String password = form.getFirst("password");
        Sex sex = form.getFirst("sex").equals("MALE") ? Sex.MALE : Sex.FEMALE;
        UserRole role = UserRole.PARENT;

        if (userRepo.findByEmail(email) != null) return Responses.USER_EMAIL_EXISTS;

        User user = new User(email, authentication.SHA512(password), firstName, lastName, sex, role, true);
        userRepo.saveAndFlush(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/teacher", method = RequestMethod.POST)
    public ResponseEntity<?> createTeacher(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @RequestBody MultiValueMap<String, String> form) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        String email = form.getFirst("email").toLowerCase();
        String firstName = form.getFirst("firstName");
        String lastName = form.getFirst("lastName");
        String password = form.getFirst("password");
        Sex sex = form.getFirst("sex").equals("MALE") ? Sex.MALE : Sex.FEMALE;
        UserRole role = UserRole.TEACHER;

        if (userRepo.findByEmail(email) != null) return Responses.USER_EMAIL_EXISTS;

        User user = new User(email, authentication.SHA512(password), firstName, lastName, sex, role, true);
        userRepo.saveAndFlush(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createStudent(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @RequestBody User user) {
        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        User newUser = new User(user.getEmail(), authentication.SHA512(user.getPassword()), user.getFirstName(), user.getLastName(), user.getSex(), user.getRole(), true);
        newUser.setParent(user.getParent());
        userRepo.saveAndFlush(newUser);

        if(user.getRole() != UserRole.STUDENT) return new ResponseEntity<>(newUser, HttpStatus.OK);

        StudentInfo info = user.getStudentInfo();
        info.setUser(newUser);
        studentInfoRepo.saveAndFlush(info);
        newUser.setStudentInfo(info);

        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }


    // ===================================================================================
    // PUT methods
    // ===================================================================================

    @RequestMapping(value = "/id/{id}/enable", method = RequestMethod.PUT)
    public ResponseEntity<?> enableUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;
        User user = userRepo.findById(id);



        if (user == null) return Responses.USER_NOT_FOUND;

        user.setEnabled(true);
        userRepo.saveAndFlush(user);
        return Responses.USER_ENABLED;
    }

    @RequestMapping(value = "/id/{id}/disable", method = RequestMethod.PUT)
    public ResponseEntity<?> disableUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;
        User user = userRepo.findById(id);

        if (user == null) return Responses.USER_NOT_FOUND;
        //TODO add to Responses
        if (user.getId() == authentication.getAuthenticatedUser().getId()) return StringMessage.asEntity("You can not disable yourself",HttpStatus.BAD_REQUEST);

        user.setEnabled(false);
        userRepo.saveAndFlush(user);
        return Responses.USER_DISABLED;
    }


    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @RequestBody User newUser) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        User oldUser = userRepo.findById(newUser.getId());

        // If not empty, update password
        if (newUser.getPassword() != null && !newUser.getPassword().equals("")) {
            log.info("A new password was provided.");
            oldUser.setPassword(authentication.SHA512(newUser.getPassword()));
        }

        if(newUser.getParent() != null) {
            User parent = userRepo.findById(newUser.getParent().getId());
            // set the parent
            oldUser.setParent(parent);
        }else{
            // set the parent to null
            oldUser.setParent(null);
        }


        // If the user's role is being changed
        if(oldUser.getRole() != newUser.getRole()){
            log.info(String.format("Role of %s is different from %s",oldUser,newUser));
            if(oldUser.getRole() == UserRole.STUDENT){
                log.info("Old user was a student. Remove student from possible classes.");
                for(Clazz clazz : oldUser.getClasses()){
                    clazz.getStudents().remove(oldUser);
                }

                log.info("Remove student from possible course toipcs.");
                for(CourseTopic courseTopic : oldUser.getCourseTopics()){
                    courseTopic.getStudents().remove(oldUser);
                }

                log.info("Delete BGV scores");
                for(BGVScore bgvScore : oldUser.getStudentInfo().getBgvScores()){
                    bgvScoreRepo.delete(bgvScore);
                }

                log.info("Delete PAV scores");
                for(PAVScore pavScore : oldUser.getStudentInfo().getPavScores()){
                    pavScoreRepo.delete(pavScore);
                }

                log.info("Removed parent");
                oldUser.setParent(null);

                log.info("Delete user's student info.");
                studentInfoRepo.delete(oldUser.getStudentInfo());
            }
            if(oldUser.getRole() == UserRole.PARENT){
                log.info("Old user was a parent. Remove parent from his children.");
                for(User child : oldUser.getChildren()){
                    child.setParent(null);
                    userRepo.saveAndFlush(child);
                }
            }
            if(oldUser.getRole() == UserRole.TEACHER){
                log.info("Old user was a teacher. Remove teacher from his classes.");
                for(Clazz clazz : oldUser.getTeachedClasses()) {
                    clazz.setTeacher(null);
                    classRepo.saveAndFlush(clazz);
                }
            }

            if(newUser.getRole() == UserRole.STUDENT) {
                log.info("The new user is a student. Create student info.");
                StudentInfo info = newUser.getStudentInfo();
                studentInfoRepo.save(info);
                oldUser.setStudentInfo(info);
            }

            log.info(String.format("Updated role to %s.",oldUser,newUser.getRole()));
            oldUser.setRole(newUser.getRole());
        }

        log.info("Saving user " + oldUser);
        userRepo.saveAndFlush(oldUser);

        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    // ===================================================================================
    // DELETE methods
    // ===================================================================================

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        User user = userRepo.findById(id);

        if (user == null) return Responses.USER_NOT_FOUND;

        if (user.getRole() == UserRole.PARENT) {
            // unreference children when parent is deleted
            for (User child : user.getChildren()) {
                child.setParent(null);
                userRepo.saveAndFlush(child);
            }
        }
        if (user.getRole() == UserRole.STUDENT) {
            // remove this student from the classes first
            for (Clazz clazz : user.getClasses()) {
                clazz.getStudents().remove(user);
                classRepo.saveAndFlush(clazz);
            }
        }
        if (user.getRole() == UserRole.TEACHER) {
            // remove this teacher from the classes first
            for (Clazz clazz : user.getTeachedClasses()) {
                clazz.setTeacher(null);
                classRepo.saveAndFlush(clazz);
            }
        }

        userRepo.delete(id);
        return Responses.USER_DELETED;

    }

}
