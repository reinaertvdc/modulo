package be.lambdaware.dao;

import be.lambdaware.entities.StudentInfo;

import java.util.List;

/**
 * Created by Vincent on 06/04/16.
 */
public interface StudentInfoDAO {

    // define functions that are not within AbstractDAO
    // custom functions e.g.
    // public List<User> getUsersByCertificate();

    /**
     * Create an entity in the database.
     * @param entity the entity to create.
     * @return the id of the newly created entity.
     */
    public int create(StudentInfo entity);

    /**
     * Get an entity, based on it's id.
     * @param id the entity's id.
     * @return the entity.
     */
    public StudentInfo get(Integer id);

    /**
     * Get a list of all the entities.
     * @return a list containing all entities.
     */
    public List<StudentInfo> getAll();

    /**
     * Delete a certain entity.
     * @param id the entity's id
     */
    public void delete(Integer id);

    /**
     * Updates an entity/
     * @param entity the entity with the new values.
     */
    public void update(StudentInfo entity);
}
