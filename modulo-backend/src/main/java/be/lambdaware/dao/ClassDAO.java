package be.lambdaware.dao;

import be.lambdaware.entities.ClassEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by Vincent on 06/04/16.
 */
public interface ClassDAO {
    // define functions that are not within AbstractDAO
    // custom functions e.g.
    // public List<UserEntity> getUsersByCertificate();

    int create(ClassEntity entity) throws DataAccessException;

    ClassEntity get(Integer id) throws DataAccessException;

    void delete(Integer id) throws DataAccessException;

    void update(ClassEntity entity) throws DataAccessException;

    List<ClassEntity> getAll() throws DataAccessException;

    List<ClassEntity> getAllByTeacher(Integer teacherId) throws DataAccessException;
}
