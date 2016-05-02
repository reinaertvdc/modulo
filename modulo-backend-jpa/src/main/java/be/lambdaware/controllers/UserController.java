/*
 *  Created by Lambdaware as part of the course "Software Engineering" @ Hasselt University.
 */

package be.lambdaware.controllers;

import be.lambdaware.dao.ClassDAO;
import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.enums.SexType;
import be.lambdaware.enums.UserRole;
import be.lambdaware.models.Clazz;
import be.lambdaware.models.StudentInfo;
import be.lambdaware.models.User;
import be.lambdaware.response.ErrorMessages;
import be.lambdaware.response.StringMessage;
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
 * {@link UserController} is the controller that is mapped to /user and handles all user related API calls.
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

    Logger log = Logger.getLogger(getClass());

    // ===================================================================================
    // GET methods
    // ===================================================================================

    /**
     * Find all {@link User} objects.
     *
     * @param auth a base64 encoding of "email:password" for authentication.
     * @return a {@link ResponseEntity} or {@link List<User>}.
     * @url GET http://localhost:8080/user/all
     * @desc Get all users.
     * @header X-Auth: base64(email:password)
     * @response 200 OK API Call successful.
     * @response 403 Forbidden Not Logged in.
     * @response 401 Unauthorized Not authorized.
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        List<User> users = userDAO.findAll();

        if (users.size() == 0) {
            return StringMessage.asEntity("No users found.", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    /**
     * Find a {@link User} by id.
     *
     * @param auth a base64 encoding of "email:password" for authentication.
     * @param id   the user's id.
     * @return a {@link ResponseEntity} or {@link User}.
     * @url GET http://localhost:8080/user/id/{:id}
     * @desc Get a user by id.
     * @header X-Auth: base64(email:password)
     * @response 200 OK API Call successful.
     * @response 403 Forbidden Not Logged in.
     * @response 401 Unauthorized Not authorized.
     */
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        User user = userDAO.findById(id);

        if (user == null) {
            return StringMessage.asEntity(String.format("No user with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
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

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        User user = userDAO.findByEmail(email);

        if (user == null) {
            return StringMessage.asEntity(String.format("No user with email=%s found.", email), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
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

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        List<User> users = userDAO.findAllByFirstNameIgnoreCase(firstName);

        if (users.size() == 0) {
            return StringMessage.asEntity(String.format("No users with first name=%s found.", firstName), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
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

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        List<User> users = userDAO.findAllByLastNameIgnoreCase(lastName);

        if (users.size() == 0) {
            return StringMessage.asEntity(String.format("No users with last name=%s found.", lastName), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
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

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        List<User> users = userDAO.findAllByRole(role);

        if (users.size() == 0) {
            return StringMessage.asEntity(String.format("No users with role=%s found.", role), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
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

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        List<User> users = userDAO.findAllByEnabled(enabled);

        if (users.size() == 0) {
            return StringMessage.asEntity(String.format("No users with enabled=%b found.", enabled), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
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

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        User parent = userDAO.findById(parentId);
        if (parent == null) {
            return StringMessage.asEntity(String.format("No user with ID=%d found.", parentId), HttpStatus.NOT_FOUND);
        } else if (parent.getRole() != UserRole.PARENT) {
            return StringMessage.asEntity(String.format("User with ID=%d is not a parent.", parentId), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(userDAO.findAllByParent(parent), HttpStatus.OK);
        }
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

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        User child = userDAO.findById(childId);

        if (child == null) {
            return StringMessage.asEntity(String.format("No user with ID=%d found.", childId), HttpStatus.NOT_FOUND);
        } else if (child.getRole() != UserRole.STUDENT) {
            return StringMessage.asEntity(String.format("User with ID=%d is not a student.", childId), HttpStatus.BAD_REQUEST);
        } else {
            User parent = child.getParent();
            if (parent == null)
                return StringMessage.asEntity(String.format("User with ID=%d has no parent assigned.", childId), HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<>(child.getParent(), HttpStatus.OK);

        }
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

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        User user = userDAO.findById(id);

        if (user == null) {
            return StringMessage.asEntity(String.format("No user with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else if (user.getRole() != UserRole.STUDENT) {
            return StringMessage.asEntity(String.format("User with ID=%d is not a student.", id), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(user.getStudentInfo(), HttpStatus.OK);
        }
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

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        User user = userDAO.findById(id);

        if (user == null) {
            return StringMessage.asEntity(String.format("No user with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else if (user.getRole() != UserRole.STUDENT) {
            return StringMessage.asEntity(String.format("User with ID=%d is not a student.", id), HttpStatus.BAD_REQUEST);
        } else {
            if (user.getClasses().size() == 0)
                return StringMessage.asEntity(String.format("User with ID=%d is not enrolled in a class.", id), HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<>(user.getClasses(), HttpStatus.OK);
        }
    }

    /**
     * Find a {@link Clazz} objects by user.
     *
     * @param auth a base64 encoding of "email:password" for authentication.
     * @param id   the class's id.
     * @return a {@link ResponseEntity} or {@link List<Clazz>}.
     */
    @RequestMapping(value = "/id/{id}/teaching", method = RequestMethod.GET)
    public ResponseEntity<?> getTeachedClassed(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        User user = userDAO.findById(id);
        if (user == null) {
            return StringMessage.asEntity(String.format("No user with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else if (user.getRole() != UserRole.TEACHER) {
            return StringMessage.asEntity(String.format("User with ID=%d is not a teacher.", id), HttpStatus.BAD_REQUEST);
        } else {
            if (user.getTeachedClasses().size() == 0)
                return StringMessage.asEntity(String.format("User with ID=%d is not teaching any classes.", id), HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<>(user.getTeachedClasses(), HttpStatus.OK);
        }
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
        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        List<User> records = new ArrayList<>();
        records.addAll(userDAO.findAllByFirstNameContainingIgnoreCase(query));
        records.addAll(userDAO.findAllByLastNameContainingIgnoreCase(query));

        if (records.size() == 0)
            return StringMessage.asEntity(String.format("No users matching query=%s found.", query), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(records, HttpStatus.OK);
    }

    // ===================================================================================
    // POST methods
    // ===================================================================================

    //TODO: example method to create a user with x-www-form-urlencoded parameters. Check http://stackoverflow.com/questions/11442632/how-can-i-post-data-as-form-data-instead-of-a-request-payload for angularjs impl.
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public ResponseEntity<?> createAdmin(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @RequestBody MultiValueMap<String, String> form) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        // extract formdata
        String email = form.getFirst("email").toLowerCase();
        String firstName = form.getFirst("firstName");
        String lastName = form.getFirst("lastName");
        String password = form.getFirst("password");
        SexType sex = form.getFirst("sex").equals("MALE") ? SexType.MALE : SexType.FEMALE;
        UserRole role = UserRole.ADMIN;

        if(userDAO.findByEmail(email) != null)
            return StringMessage.asEntity(String.format("Could not create user. Email=%s already exists.", email), HttpStatus.BAD_REQUEST);
        else {
            User user = new User(email, authentication.SHA512(password), firstName, lastName, sex, role, true);
            userDAO.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/parent", method = RequestMethod.POST)
    public ResponseEntity<?> createParent(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @RequestBody MultiValueMap<String, String> form) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        // extract formdata
        String email = form.getFirst("email").toLowerCase();
        String firstName = form.getFirst("firstName");
        String lastName = form.getFirst("lastName");
        String password = form.getFirst("password");
        SexType sex = form.getFirst("sex").equals("MALE") ? SexType.MALE : SexType.FEMALE;
        UserRole role = UserRole.PARENT;

        if(userDAO.findByEmail(email) != null)
            return StringMessage.asEntity(String.format("Could not create user. Email=%s already exists.", email), HttpStatus.BAD_REQUEST);
        else {
            User user = new User(email, authentication.SHA512(password), firstName, lastName, sex, role, true);
            userDAO.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/teacher", method = RequestMethod.POST)
    public ResponseEntity<?> createTeacher(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @RequestBody MultiValueMap<String, String> form) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        // extract formdata
        String email = form.getFirst("email").toLowerCase();
        String firstName = form.getFirst("firstName");
        String lastName = form.getFirst("lastName");
        String password = form.getFirst("password");
        SexType sex = form.getFirst("sex").equals("MALE") ? SexType.MALE : SexType.FEMALE;
        UserRole role = UserRole.TEACHER;

        if(userDAO.findByEmail(email) != null)
            return StringMessage.asEntity(String.format("Could not create user. Email=%s already exists.", email), HttpStatus.BAD_REQUEST);
        else {
            User user = new User(email, authentication.SHA512(password), firstName, lastName, sex, role, true);
            userDAO.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/student", method = RequestMethod.POST)
    public ResponseEntity<?> createStudent(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @RequestBody MultiValueMap<String, String> form) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        // extract formdata for user
        String email = form.getFirst("email").toLowerCase();
        String firstName = form.getFirst("firstName");
        String lastName = form.getFirst("lastName");
        String password = form.getFirst("password");
        SexType sex = form.getFirst("sex").equals("MALE") ? SexType.MALE : SexType.FEMALE;
        UserRole role = UserRole.STUDENT;

        // extract formdata for student
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

        if(userDAO.findByEmail(email) != null)
            return StringMessage.asEntity(String.format("Could not create user. Email=%s already exists.", email), HttpStatus.BAD_REQUEST);
        else {
            User user = new User(email, authentication.SHA512(password), firstName, lastName, sex, role, true);
            StudentInfo studentInfo = new StudentInfo(birthDate,birthPlace,nationality,nationalIdentificationNumber,street,houseNumber,postalCode,city,phoneNumber,emergencyNumber,bankAccount);
            userDAO.save(user);
            user.setStudentInfo(studentInfo);
            studentInfoDAO.save(studentInfo);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }


    // ===================================================================================
    // PUT methods
    // ===================================================================================

    @RequestMapping(value = "/id/{id}/email/{email}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateEmail(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id, @PathVariable String email) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        User user = userDAO.findById(id);
        email = email.toLowerCase();

        if (user == null) {
            return StringMessage.asEntity(String.format("No user with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else if (userDAO.findByEmail(email) != null) {
            return StringMessage.asEntity(String.format("Email of user with ID=%d not updated. Email already exists.", id), HttpStatus.BAD_REQUEST);
        } else {
            user.setEmail(email);
            userDAO.saveAndFlush(user);
            return StringMessage.asEntity(String.format("Email of user with ID=%d updated to email=%s.", id, email), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/id/{id}/firstname/{firstName}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateFirstName(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id, @PathVariable String firstName) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        User user = userDAO.findById(id);

        if (user == null) {
            return StringMessage.asEntity(String.format("No user with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else {
            user.setFirstName(firstName);
            userDAO.saveAndFlush(user);
            return StringMessage.asEntity(String.format("Email of user with ID=%d updated to first name=%s.", id, firstName), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/id/{id}/lastname/{lastName}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLastName(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id, @PathVariable String lastName) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        User user = userDAO.findById(id);

        if (user == null) {
            return StringMessage.asEntity(String.format("No user with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else {
            user.setLastName(lastName);
            userDAO.saveAndFlush(user);
            return StringMessage.asEntity(String.format("Email of user with ID=%d updated to last name=%s.", id, lastName), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/id/{id}/password/{password}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePassword(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id, @PathVariable String password) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        User user = userDAO.findById(id);

        if (user == null) {
            return StringMessage.asEntity(String.format("No user with ID=%d found.", id), HttpStatus.NOT_FOUND);
        } else {
            String hashedPassword = authentication.SHA512(password);
            user.setPassword(hashedPassword);
            userDAO.saveAndFlush(user);
            return StringMessage.asEntity(String.format("Password of user with ID=%d updated.", id), HttpStatus.OK);
        }
    }


    // ===================================================================================
    // DELETE methods
    // ===================================================================================

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) {
            return StringMessage.asEntity(ErrorMessages.LOGIN_INVALID, HttpStatus.FORBIDDEN);
        } else if (!authentication.isAdmin()) {
            return StringMessage.asEntity(ErrorMessages.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        if(userDAO.exists(id)){
            userDAO.delete(id);
            StringMessage message = new StringMessage(String.format("User with id=%d deleted.", id));
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return StringMessage.asEntity(String.format("No user with ID=%d found.", id), HttpStatus.NOT_FOUND);
        }


    }

}
