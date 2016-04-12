package be.lambdaware.dao;

import be.lambdaware.entities.CourseTopicEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by jensv on 07-Apr-16.
 */
public interface CourseTopicDAO{

    /**
     * Create an entity in the database.
     * @param entity the entity to create.
     * @return the id of the newly created entity.
     */
    public int create(CourseTopicEntity entity) throws DataAccessException;

    /**
     * Get an entity, based on it's id.
     * @param id the entity's id.
     * @return the entity.
     */
    public CourseTopicEntity get(Integer id) throws DataAccessException;

    /**
     * Get a list of all the entities.
     * @return a list containing all entities.
     */
    public List<CourseTopicEntity> getAll() throws DataAccessException;

    /**
     * Delete a certain entity.
     * @param id the entity's id
     */
    public void delete(Integer id) throws DataAccessException;

    /**
     * Updates an entity/
     * @param entity the entity with the new values.
     */
    public void update(CourseTopicEntity entity) throws DataAccessException;
}
