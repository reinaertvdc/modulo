package be.lambdaware.dao;

import be.lambdaware.entities.StudentClassEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by martijn on 07/04/16.
 */
public interface StudentClassDAO {


    public void create(StudentClassEntity entity) throws DataAccessException;

    public StudentClassEntity get(Integer studentInfoId, Integer classId) throws DataAccessException;

    public List<StudentClassEntity> getByClass(Integer classEntityId) throws DataAccessException;

    public List<StudentClassEntity> getAll() throws DataAccessException;

    public void delete(Integer studentInfoId, Integer classId) throws DataAccessException;

}
