package be.lambdaware.controllers;

import be.lambdaware.dao.ClassDAO;
import be.lambdaware.dao.TaskDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.enums.ClassType;
import be.lambdaware.enums.UserRole;
import be.lambdaware.models.Clazz;
import be.lambdaware.models.Task;
import be.lambdaware.models.TaskScore;
import be.lambdaware.models.User;
import be.lambdaware.response.Responses;
import be.lambdaware.security.APIAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MichielVM on 4/05/2016.
 */

@RestController
@RequestMapping("/task")
@CrossOrigin
public class TaskController {

    @Autowired
    TaskDAO taskDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    ClassDAO classDAO;

    @Autowired
    APIAuthentication authentication;

    // ===================================================================================
    // GET methods
    // ===================================================================================

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Task task = taskDAO.findById(id);

        if (task == null) return Responses.TASK_NOT_FOUND;
        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        Task task = taskDAO.findById(id);
        if (task == null) return Responses.TASK_NOT_FOUND;

        taskDAO.delete(id);
        return Responses.TASK_DELETED;
    }


    @RequestMapping(value = "/teacher/{teacher}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTeachedTasks(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long teacher) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        // get all PAV classes for this teacher
        User user = userDAO.findById(teacher);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.TEACHER) return Responses.USER_NOT_TEACHER;

        List<Clazz> classes = user.getTeachedClasses();
        if (classes.size() == 0) return Responses.USER_NO_TEACHING;

        // get all associated tasks for PAV classes
        List<Task> teachedTasks = new ArrayList<Task>();
        for (Clazz clazz : classes) {
            if (clazz.getType() == ClassType.PAV) {
                teachedTasks.addAll(taskDAO.findAllByClazz(clazz));
            }
        }

        if (teachedTasks.size() == 0) return Responses.TASKS_NOT_FOUND;
        return new ResponseEntity<>(teachedTasks, HttpStatus.OK);
    }
}
