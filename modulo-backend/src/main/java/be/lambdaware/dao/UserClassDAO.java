package be.lambdaware.dao;

import be.lambdaware.entities.UserClassEntity;

import java.util.List;

/**
 * Created by martijn on 07/04/16.
 */
public interface UserClassDAO {

    /**
     * Create an entity in the database.
     * @param entity the entity to create.
     * @return the id of the newly created entity.
     */
    public void create(UserClassEntity entity);

    /**
     * Get an entity, based on it's id.
     * @param student_id the students id
     * @param class_id the class id
     * @return the entity.
     */
    public UserClassEntity get(Integer student_id, Integer class_id);

    /**
     * Get a list of all the entities.
     * @return a list containing all entities.
     */
    public List<UserClassEntity> getAll();

    /**
     * Delete a certain entity.
     * @param student_id the students id
     * @param class_id the class id
     */
    public void delete(Integer student_id, Integer class_id);

    /**
     * Updates an entity/
     * @param entity the entity with the new values.
     */
    public void update(UserClassEntity entity);

}
