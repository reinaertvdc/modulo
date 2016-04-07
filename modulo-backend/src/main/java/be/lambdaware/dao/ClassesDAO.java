package be.lambdaware.dao;

import be.lambdaware.entities.ClassEntity;
import be.lambdaware.entities.User;

import java.util.List;

/**
 * Created by Vincent on 06/04/16.
 */
public interface ClassesDAO {
    // define functions that are not within AbstractDAO
    // custom functions e.g.
    // public List<User> getUsersByCertificate();

    int create(ClassEntity entity);

    List<ClassEntity> getAll();

    List<ClassEntity> getAllByTeacher(User teacher);
}
