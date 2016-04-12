package be.lambdaware.dao;

import be.lambdaware.entities.ClassTopicsEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by jensv on 08-Apr-16.
 */
public interface ClassTopicsDAO {
    /**
     * Create an entity in the database.
     * @param entity the entity to create.
     * @return the id of the newly created entity.
     */
    public void create(ClassTopicsEntity entity) throws DataAccessException ;

    /**
     * Get an entity, based on it's id.
     * @return the entity.
     */
    public ClassTopicsEntity get(Integer courseTopicId, Integer classId) throws DataAccessException ;

    /**
     * Get a list of all the entities.
     * @return a list containing all entities.
     */
    public List<ClassTopicsEntity> getAll() throws DataAccessException ;

    /**
     * Delete a certain entity.
     */
    public void delete(Integer courseTopicId, Integer classId) throws DataAccessException ;

}
