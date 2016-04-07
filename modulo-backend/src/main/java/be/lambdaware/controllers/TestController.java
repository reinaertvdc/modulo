package be.lambdaware.controllers;

import be.lambdaware.dao.ClassesDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.entities.ClassEntity;
import be.lambdaware.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hendrik on 05/04/16.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ClassesDAO classesDAO;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ClassEntity> create(@RequestBody ClassEntity entity) {

        //TODO process when dao.create fails with SQL Exception

        // create an entity using the DAO
        int entityID = classesDAO.create(entity);
        // id gets assigned after creation
        entity.setId(entityID);
        // return entity
        return new ResponseEntity<ClassEntity>(entity, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClassEntity>> get(@RequestParam(value="id") int teacherId) {

        // get a teacher
        UserEntity teacher = userDAO.get(teacherId);

        // get all classes of said teacher
        List<ClassEntity> classes = classesDAO.getAllByTeacher(teacher);

        return new ResponseEntity<List<ClassEntity>>(classes, HttpStatus.OK);
    }

}
