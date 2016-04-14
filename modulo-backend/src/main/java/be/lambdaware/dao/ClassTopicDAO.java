package be.lambdaware.dao;

import be.lambdaware.entities.ClassTopicEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by jensv on 08-Apr-16.
 */
public interface ClassTopicDAO {
    /**
     * Create an entity in the database.
     * @param entity the entity to create.
     * @return the id of the newly created entity.
     */
    public void create(ClassTopicEntity entity) throws DataAccessException ;

    /**
     * Get an entity, based on it's id.
     * @return the entity.
     */
    public ClassTopicEntity get(Integer courseTopicId, Integer classId) throws DataAccessException ;

    /**
     * Get a list of all the entities.
     * @return a list containing all entities.
     */
    public List<ClassTopicEntity> getAll() throws DataAccessException ;

    /**
     * Delete a certain entity.
     */
    public void delete(Integer courseTopicId, Integer classId) throws DataAccessException ;

}
