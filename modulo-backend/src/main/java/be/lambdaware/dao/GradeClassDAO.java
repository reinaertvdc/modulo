package be.lambdaware.dao;

import be.lambdaware.entities.GradeClassEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by martijn on 07/04/16.
 */
public interface GradeClassDAO {


    public void create(GradeClassEntity entity) throws DataAccessException;

    public GradeClassEntity get(Integer gradeId, Integer classId) throws DataAccessException;

    public List<GradeClassEntity> getByClass(Integer classId) throws DataAccessException;

    public List<GradeClassEntity> getByGrade(Integer gradeId) throws DataAccessException;

    public List<GradeClassEntity> getAll() throws DataAccessException;

    public void delete(Integer gradeId, Integer classId) throws DataAccessException;

}