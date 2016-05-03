package be.lambdaware.controllers;

import be.lambdaware.dao.ClassDAO;
import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.enums.Sex;
import be.lambdaware.enums.UserRole;
import be.lambdaware.models.*;
import be.lambdaware.response.Responses;
import be.lambdaware.security.APIAuthentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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
    UserDAO userDAO;
    @Autowired
    ClassDAO classDAO;
    @Autowired
    StudentInfoDAO studentInfoDAO;

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

        List<User> users = userDAO.findAll();

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

        User user = userDAO.findById(id);

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

        User user = userDAO.findByEmail(email);

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

        List<User> users = userDAO.findAllByFirstNameIgnoreCase(firstName);

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

        List<User> users = userDAO.findAllByLastNameIgnoreCase(lastName);

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

        List<User> users = userDAO.findAllByRole(role);

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

        List<User> users = userDAO.findAllByEnabled(enabled);

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
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        User user = userDAO.findById(parentId);

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

        User user = userDAO.findById(childId);

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

        User user = userDAO.findById(id);

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
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        User user = userDAO.findById(id);

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

        User user = userDAO.findById(id);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.TEACHER) return Responses.USER_NOT_TEACHER;

        List<Clazz> classes = user.getTeachedClasses();

        if (classes.size() == 0) return Responses.USER_NO_TEACHING;
        return new ResponseEntity<>(classes, HttpStatus.OK);
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
        records.addAll(userDAO.findAllByFirstNameContainingIgnoreCase(query));
        records.addAll(userDAO.findAllByLastNameContainingIgnoreCase(query));

        if (records.size() == 0) return Responses.USERS_NOT_FOUND;
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}/grade", method = RequestMethod.GET)
    public ResponseEntity<?> getGradeFromUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        User user = userDAO.findById(id);

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
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        User user = userDAO.findById(id);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.STUDENT) return Responses.USER_NOT_STUDENT;

        StudentInfo info = user.getStudentInfo();

        if (info == null) return Responses.STUDENT_INFO_NOT_FOUND;

        Certificate certificate = info.getCertificate();

        if (certificate == null) return Responses.CERTIFICATE_NOT_FOUND;

        return new ResponseEntity<>(certificate, HttpStatus.OK);
    }

    // ===================================================================================
    // POST methods
    // ===================================================================================

    //TODO: example method to create a user with x-www-form-urlencoded parameters. Check http://stackoverflow.com/questions/11442632/how-can-i-post-data-as-form-data-instead-of-a-request-payload for angularjs impl.
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

        if (userDAO.findByEmail(email) != null) return Responses.USER_EMAIL_EXISTS;

        User user = new User(email, authentication.SHA512(password), firstName, lastName, sex, role, true);
        userDAO.saveAndFlush(user);
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

        if (userDAO.findByEmail(email) != null) return Responses.USER_EMAIL_EXISTS;

        User user = new User(email, authentication.SHA512(password), firstName, lastName, sex, role, true);
        userDAO.saveAndFlush(user);
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

        if (userDAO.findByEmail(email) != null) return Responses.USER_EMAIL_EXISTS;

        User user = new User(email, authentication.SHA512(password), firstName, lastName, sex, role, true);
        userDAO.saveAndFlush(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/student", method = RequestMethod.POST)
    public ResponseEntity<?> createStudent(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @RequestBody MultiValueMap<String, String> form) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;


        String email = form.getFirst("email").toLowerCase();
        String firstName = form.getFirst("firstName");
        String lastName = form.getFirst("lastName");
        String password = form.getFirst("password");
        Sex sex = form.getFirst("sex").equals("MALE") ? Sex.MALE : Sex.FEMALE;
        UserRole role = UserRole.STUDENT;
        Date birthDate = Date.valueOf(form.getFirst("birthDate"));
        String birthPlace = form.getFirst("birthPlace");
        String nationality = form.getFirst("nationality");
        String nationalIdentificationNumber = form.getFirst("nationalIdentificationNumber");
        String street = form.getFirst("street");
        String houseNumber = form.getFirst("houseNumber");
        String postalCode = form.getFirst("postalCode");
        String city = form.getFirst("city");
        String phoneNumber = form.getFirst("phoneNumber");
        String emergencyNumber = form.getFirst("emergencyNumber");
        String bankAccount = form.getFirst("bankAccount");

        if (userDAO.findByEmail(email) != null) return Responses.USER_EMAIL_EXISTS;

        User user = new User(email, authentication.SHA512(password), firstName, lastName, sex, role, true);
        StudentInfo studentInfo = new StudentInfo(birthDate, birthPlace, nationality, nationalIdentificationNumber, street, houseNumber, postalCode, city, phoneNumber, emergencyNumber, bankAccount);
        userDAO.saveAndFlush(user);
        user.setStudentInfo(studentInfo);
        studentInfoDAO.saveAndFlush(studentInfo);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    // ===================================================================================
    // PUT methods
    // ===================================================================================

    @RequestMapping(value = "/id/{id}/enable", method = RequestMethod.PUT)
    public ResponseEntity<?> enableUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;
        User user = userDAO.findById(id);

        if (user == null) return Responses.USER_NOT_FOUND;

        user.setEnabled(true);
        userDAO.saveAndFlush(user);
        return Responses.USER_ENABLED;
    }

    @RequestMapping(value = "/id/{id}/disable", method = RequestMethod.PUT)
    public ResponseEntity<?> disableUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;
        User user = userDAO.findById(id);

        if (user == null) return Responses.USER_NOT_FOUND;

        user.setEnabled(false);
        userDAO.saveAndFlush(user);
        return Responses.USER_DISABLED;
    }


    @RequestMapping(value = "/id/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudent(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id, @RequestBody User user) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        User dataBaseUser = userDAO.findById(user.getId());

        // Compare if a new password was set, if not, set the old password.
        if (user.getPassword() == null || user.getPassword().equals(""))
            user.setPassword(dataBaseUser.getPassword());
        else
            user.setPassword(authentication.SHA512(user.getPassword()));


        //TODO validate conversions
        if (user.getRole() == UserRole.STUDENT) {

            StudentInfo studentInfo = user.getStudentInfo();
            // setId(0) to identify that we need a new object.
            studentInfo.setId(0);
            studentInfoDAO.saveAndFlush(studentInfo);
            if (dataBaseUser.getRole() == UserRole.PARENT) {
                for (User child : dataBaseUser.getChildren()) {
                    child.setParent(null);
                    userDAO.saveAndFlush(child);
                }
            }
            else if (dataBaseUser.getRole() == UserRole.ADMIN) {
                // nothing specific
            }
            else if (dataBaseUser.getRole() == UserRole.TEACHER) {
                for (Clazz clazz : dataBaseUser.getTeachedClasses()) {
                    clazz.setTeacher(null);
                    classDAO.saveAndFlush(clazz);
                }
            }
        } else if (user.getRole() == UserRole.PARENT) {
            if (dataBaseUser.getRole() == UserRole.STUDENT) {
                studentInfoDAO.delete(dataBaseUser.getStudentInfo());
                user.setParent(null);
            } else if (dataBaseUser.getRole() == UserRole.ADMIN) {
                // nothing
            } else if (dataBaseUser.getRole() == UserRole.TEACHER) {
                for (Clazz clazz : dataBaseUser.getTeachedClasses()) {
                    clazz.setTeacher(null);
                    classDAO.saveAndFlush(clazz);
                }
            }
        } else if (user.getRole() == UserRole.ADMIN) {
            if (dataBaseUser.getRole() == UserRole.STUDENT) {
                studentInfoDAO.delete(dataBaseUser.getStudentInfo());
                user.setParent(null);
            } else if (dataBaseUser.getRole() == UserRole.PARENT) {
                for (User child : dataBaseUser.getChildren()) {
                    child.setParent(null);
                    userDAO.saveAndFlush(child);
                }
            } else if (dataBaseUser.getRole() == UserRole.TEACHER) {
                for (Clazz clazz : dataBaseUser.getTeachedClasses()) {
                    clazz.setTeacher(null);
                    classDAO.saveAndFlush(clazz);
                }
            }
        } else if (user.getRole() == UserRole.TEACHER) {
            if (dataBaseUser.getRole() == UserRole.STUDENT) {
                studentInfoDAO.delete(dataBaseUser.getStudentInfo());
                user.setParent(null);
            } else if (dataBaseUser.getRole() == UserRole.PARENT) {
                for (Clazz clazz : dataBaseUser.getTeachedClasses()) {
                    clazz.setTeacher(null);
                    classDAO.saveAndFlush(clazz);
                }
            } else if (dataBaseUser.getRole() == UserRole.ADMIN) {
                //nothing
            }
        }

        userDAO.saveAndFlush(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // ===================================================================================
    // DELETE methods
    // ===================================================================================

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isAdmin()) return Responses.UNAUTHORIZED;

        User user = userDAO.findById(id);

        if (user == null) return Responses.USER_NOT_FOUND;

        if (user.getRole() == UserRole.PARENT) {
            // unreference children when parent is deleted
            for (User child : user.getChildren()) {
                child.setParent(null);
                userDAO.saveAndFlush(child);
            }
        }
        if (user.getRole() == UserRole.STUDENT) {
            // remove this student from the classes first
            for (Clazz clazz : user.getClasses()) {
                clazz.getStudents().remove(user);
                classDAO.saveAndFlush(clazz);
            }
        }
        if (user.getRole() == UserRole.TEACHER) {
            // remove this teacher from the classes first
            for (Clazz clazz : user.getTeachedClasses()) {
                clazz.setTeacher(null);
                classDAO.saveAndFlush(clazz);
            }
        }

        userDAO.delete(id);
        return Responses.USER_DELETED;

    }

}
