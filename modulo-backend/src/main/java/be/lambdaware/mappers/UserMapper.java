package be.lambdaware.mappers;

import be.lambdaware.entities.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author hendrik
 */
public class UserMapper implements RowMapper<User> {


    @Override
    public User mapRow(ResultSet resultSet, int row) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("users.id"));
        user.setEmail(resultSet.getString("users.email"));
        user.setPassword(resultSet.getString("users.password"));
        user.setType(resultSet.getString("users.type"));
        return user;
    }
}
