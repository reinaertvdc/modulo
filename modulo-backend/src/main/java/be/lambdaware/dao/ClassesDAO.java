package be.lambdaware.dao;

import be.lambdaware.entities.ClassEntity;
import be.lambdaware.entities.UserEntity;

import java.util.List;

/**
 * Created by Vincent on 06/04/16.
 */
public interface ClassesDAO {
    // define functions that are not within AbstractDAO
    // custom functions e.g.
    // public List<UserEntity> getUsersByCertificate();

    int create(ClassEntity entity);

    ClassEntity get(Integer id);

    List<ClassEntity> getAll();

    List<ClassEntity> getAllByTeacher(UserEntity teacher);
}
