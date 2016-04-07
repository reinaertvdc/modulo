package be.lambdaware.dao;

import be.lambdaware.entities.StudentPAVScoreEntity;

import java.util.List;

/**
 * Created by jensv on 07-Apr-16.
 */
public interface StudentPAVScoreDAO {


    /**
     * Create an entity in the database.
     * @param entity the entity to create.
     * @return the id of the newly created entity.
     */
    public int create(StudentPAVScoreEntity entity);

    /**
     * Get an entity, based on it's id.
     * @param id the entity's id.
     * @return the entity.
     */
    public StudentPAVScoreEntity get(Integer id);

    /**
     * Get a list of all the entities.
     * @return a list containing all entities.
     */
    public List<StudentPAVScoreEntity> getAll();

    /**
     * Delete a certain entity.
     * @param id the entity's id
     */
    public void delete(Integer id);

    /**
     * Updates an entity/
     * @param entity the entity with the new values.
     */
    public void update(StudentPAVScoreEntity entity);

}
