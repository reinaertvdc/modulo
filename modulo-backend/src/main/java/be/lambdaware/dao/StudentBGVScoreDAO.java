package be.lambdaware.dao;

import be.lambdaware.entities.StudentBGVScoreEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by jensv on 08-Apr-16.
 */
public interface StudentBGVScoreDAO {

    /**
     * Create an entity in the database.
     * @param entity the entity to create.
     * @return the id of the newly created entity.
     */
    public int create(StudentBGVScoreEntity entity) throws DataAccessException;

    /**
     * Get an entity, based on it's id.
     * @param id the entity's id.
     * @return the entity.
     */
    public StudentBGVScoreEntity get(Integer id) throws DataAccessException;

    StudentBGVScoreEntity getByStudentAndCompetence(Integer studentId, Integer competenceId);

    /**
     * Get a list of all the entities.
     * @return a list containing all entities.
     */
    public List<StudentBGVScoreEntity> getAll() throws DataAccessException;

    /**
     * Delete a certain entity.
     * @param id the entity's id
     */
    public void delete(Integer id) throws DataAccessException;

    /**
     * Updates an entity/
     * @param entity the entity with the new values.
     */
    public void update(StudentBGVScoreEntity entity) throws DataAccessException;
}
