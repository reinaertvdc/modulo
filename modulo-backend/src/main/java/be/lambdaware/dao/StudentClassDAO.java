package be.lambdaware.dao;

import be.lambdaware.entities.ClassEntity;
import be.lambdaware.entities.StudentClassEntity;
import be.lambdaware.entities.StudentInfoEntity;

import java.util.List;

/**
 * Created by martijn on 07/04/16.
 */
public interface StudentClassDAO {


    public void create(StudentClassEntity entity);

    public StudentClassEntity get(Integer studentInfoId, Integer classId);

    public List<StudentClassEntity> getAll();

    public void delete(Integer studentInfoId, Integer classId);

}
