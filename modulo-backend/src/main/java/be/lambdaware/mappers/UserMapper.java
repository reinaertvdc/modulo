package be.lambdaware.mappers;

import be.lambdaware.entities.UserEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author hendrik
 */
public class UserMapper implements RowMapper<UserEntity> {


    @Override
    public UserEntity mapRow(ResultSet resultSet, int row) throws SQLException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(resultSet.getInt("users.id"));
        userEntity.setEmail(resultSet.getString("users.email"));
        userEntity.setPassword(resultSet.getString("users.password"));
        userEntity.setType(resultSet.getString("users.type"));
        return userEntity;
    }
}
