package be.lambdaware.mappers;

import be.lambdaware.entities.ClassEntity;
import be.lambdaware.entities.StudentInfoEntity;
import be.lambdaware.entities.StudentClassEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by martijn on 07/04/16.
 */
public class StudentClassMapper implements RowMapper<StudentClassEntity> {

    //private StudentInfoMapper studentInfoMapper;
    //private ClassesMapper classesMapper;

    @Override
    public StudentClassEntity mapRow(ResultSet resultSet, int row) throws SQLException {
        /*StudentClassEntity userClass = new StudentClassEntity();

        this.studentInfoMapper = new StudentInfoMapper();
        this.classesMapper = new ClassesMapper();

        StudentInfoEntity studentInfo = this.studentInfoMapper.mapRow(resultSet, row);
        ClassEntity classEntity = this.classesMapper.mapRow(resultSet, row);

        userClass.setStudentInfo(studentInfo);
        userClass.setClassEntity(classEntity);
        */
        StudentClassEntity userClass = new StudentClassEntity();
        userClass.setStudentInfoId(resultSet.getInt("student_id"));
        userClass.setClassId(resultSet.getInt("class_id"));

        return userClass;
    }
}
