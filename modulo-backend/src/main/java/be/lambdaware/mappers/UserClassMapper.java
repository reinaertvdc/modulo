package be.lambdaware.mappers;

import be.lambdaware.entities.UserClassEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by martijn on 07/04/16.
 */
public class UserClassMapper implements RowMapper<UserClassEntity> {

    @Override
    public UserClassEntity mapRow(ResultSet resultSet, int row) throws SQLException {
        UserClassEntity userClass = new UserClassEntity();
        userClass.setStudent_id(resultSet.getInt("student_id"));
        userClass.setClass_id(resultSet.getInt("class_id"));
        return userClass;
    }
}
