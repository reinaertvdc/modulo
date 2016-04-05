package be.lambdaware.dao;

import java.util.List;

/**
 * @author hendrik
 */
public interface AbstractDAO<T> {

    /**
     * Create an entity in the database.
     * @param entity the entity to create.
     * @return the id of the newly created entity.
     */
    public int create(T entity);

    /**
     * Get an entity, based on it's id.
     * @param id the entity's id.
     * @return the entity.
     */
    public T get(Integer id);

    /**
     * Get a list of all the entities.
     * @return a list containing all entities.
     */
    public List<T> getAll();

    /**
     * Delete a certain entity.
     * @param id the entity's id
     */
    public void delete(Integer id);

    /**
     * Updates an entity/
     * @param entity the entity with the new values.
     */
    public void update(T entity);
}
