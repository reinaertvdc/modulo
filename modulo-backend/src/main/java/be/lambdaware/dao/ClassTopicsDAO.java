package be.lambdaware.dao;

import be.lambdaware.entities.ClassTopicsEntity;

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
    public void create(ClassTopicsEntity entity);

    /**
     * Get an entity, based on it's id.
     * @return the entity.
     */
    public ClassTopicsEntity get(Integer course_topic_id, Integer class_id);

    /**
     * Get a list of all the entities.
     * @return a list containing all entities.
     */
    public List<ClassTopicsEntity> getAll();

    /**
     * Delete a certain entity.
     */
    public void delete(Integer course_topic_id, Integer class_id);

}
