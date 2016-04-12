package be.lambdaware.dao;

import be.lambdaware.entities.CourseTopicEntity;
import be.lambdaware.entities.GradeEntity;
import be.lambdaware.entities.ObjectiveEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by jensv on 07-Apr-16.
 */
public interface ObjectiveDAO {
    /**
     * Create an entity in the database.
     * @param entity the entity to create.
     * @return the id of the newly created entity.
     */
    public int create(ObjectiveEntity entity) throws DataAccessException;

    /**
     * Get an entity, based on it's id.
     * @param id the entity's id.
     * @return the entity.
     */
    public ObjectiveEntity get(Integer id) throws DataAccessException;

    public List<ObjectiveEntity> getByGradeId(Integer gradeId) throws DataAccessException;

    public List<ObjectiveEntity> getByCourseTopicId(Integer courseTopicId) throws DataAccessException;

    /**
     * Get a list of all the entities.
     * @return a list containing all entities.
     */
    public List<ObjectiveEntity> getAll() throws DataAccessException;

    /**
     * Delete a certain entity.
     * @param id the entity's id
     */
    public void delete(Integer id) throws DataAccessException;

    /**
     * Updates an entity/
     * @param entity the entity with the new values.
     */
    public void update(ObjectiveEntity entity) throws DataAccessException;
}
