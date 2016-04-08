package be.lambdaware.dao;

import be.lambdaware.entities.StudentInfoEntity;

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
    int create(StudentInfoEntity entity);

    /**
     * Get an entity, based on it's id.
     * @param id the entity's id.
     * @return the entity.
     */
    StudentInfoEntity getByUserId(Integer id);

    StudentInfoEntity getById(Integer id);

    /**
     * Updates an entity/
     * @param entity the entity with the new values.
     */
    public void update(StudentInfoEntity entity);

    /**
     * Delete a certain entity.
     * @param id the entity's id
     */
    public void delete(Integer id);

}
