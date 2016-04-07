package be.lambdaware.dao;

import be.lambdaware.entities.UserEntity;

import java.util.List;

/**
 * @author hendrik
 */
public interface UserDAO {

    /**
     * Create an entity in the database.
     * @param entity the entity to create.
     * @return the id of the newly created entity.
     */
    public int create(UserEntity entity);

    /**
     * Get an entity, based on it's id.
     * @param id the entity's id.
     * @return the entity.
     */
    public UserEntity get(Integer id);

    /**
     * Get a list of all the entities.
     * @return a list containing all entities.
     */
    public List<UserEntity> getAll();

    /**
     * Delete a certain entity.
     * @param id the entity's id
     */
    public void delete(Integer id);

    /**
     * Updates an entity/
     * @param entity the entity with the new values.
     */
    public void update(UserEntity entity);

}
