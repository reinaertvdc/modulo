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
        user.setId(resultSet.getInt("id"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }
}
