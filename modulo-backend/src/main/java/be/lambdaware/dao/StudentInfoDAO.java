package be.lambdaware.dao;

import be.lambdaware.entities.StudentInfoEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by Vincent on 06/04/16.
 */
public interface StudentInfoDAO {

    // define functions that are not within AbstractDAO
    // custom functions e.g.
    // public List<UserEntity> getUsersByCertificate();

    /**
     * Create an entity in the database.
     * @param entity the entity to create.
     * @return the id of the newly created entity.
     */
    int create(StudentInfoEntity entity) throws DataAccessException;

    StudentInfoEntity get(Integer id) throws DataAccessException;

    /**
     * Get an entity, based on it's id.
     * @param id the entity's id.
     * @return the entity.
     */
    StudentInfoEntity getByUserId(Integer id) throws DataAccessException;

    List<StudentInfoEntity> getByParentId(Integer id) throws DataAccessException;

    List<StudentInfoEntity> getAll() throws DataAccessException;

    /**
     * Updates an entity/
     * @param entity the entity with the new values.
     */
    public void update(StudentInfoEntity entity) throws DataAccessException;

    /**
     * Delete a certain entity.
     * @param id the entity's id
     */
    public void delete(Integer id) throws DataAccessException;

}
