package be.lambdaware.mappers;

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
        StudentClassEntity userClass = new StudentClassEntity();
        userClass.setStudentInfoId(resultSet.getInt("student_info_id"));
        userClass.setClassId(resultSet.getInt("class_id"));

        return userClass;
    }
}
