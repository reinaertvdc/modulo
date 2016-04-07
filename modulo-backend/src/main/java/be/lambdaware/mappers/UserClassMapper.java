package be.lambdaware.mappers;

import be.lambdaware.entities.ClassEntity;
import be.lambdaware.entities.StudentInfo;
import be.lambdaware.entities.UserClassEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by martijn on 07/04/16.
 */
public class UserClassMapper implements RowMapper<UserClassEntity> {

    private StudentInfoMapper studentInfoMapper;
    private ClassesMapper classesMapper;


    @Override
    public UserClassEntity mapRow(ResultSet resultSet, int row) throws SQLException {
        UserClassEntity userClass = new UserClassEntity();

        this.studentInfoMapper = new StudentInfoMapper();
        this.classesMapper = new ClassesMapper(new UserMapper());

        StudentInfo studentInfo = this.studentInfoMapper.mapRow(resultSet, row);
        ClassEntity classEntity = this.classesMapper.mapRow(resultSet, row);

        userClass.setStudentInfo(studentInfo);
        userClass.setClassEntity(classEntity);

        return userClass;
    }
}
