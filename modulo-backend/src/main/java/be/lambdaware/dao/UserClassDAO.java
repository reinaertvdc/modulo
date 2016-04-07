package be.lambdaware.dao;

import be.lambdaware.entities.ClassEntity;
import be.lambdaware.entities.StudentInfo;
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
     * @param studentInfo the students info
     * @param classEntity the class
     * @return the entity.
     */
    public UserClassEntity get(StudentInfo studentInfo, ClassEntity classEntity);

    /**
     * Get a list of all the entities.
     * @return a list containing all entities.
     */
    public List<UserClassEntity> getAll();

    /**
     * Delete a certain entity.
     * @param studentInfo the students info
     * @param classEntity the class
     */
    public void delete(StudentInfo studentInfo, ClassEntity classEntity);

}
