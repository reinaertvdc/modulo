package be.lambdaware.mappers;

import be.lambdaware.entities.ClassEntity;
import be.lambdaware.entities.User;
import be.lambdaware.model.AbstractClass;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vincent on 06/04/16.
 */
public class ClassesMapper implements RowMapper<ClassEntity> {

    // TODO implement mapRow
    @Override
    public ClassEntity mapRow(ResultSet resultSet, int row) throws SQLException {
//        User user = new User();
//        user.setId(resultSet.getInt("id"));
//        user.setEmail(resultSet.getString("email"));
//        user.setPassword(resultSet.getString("password"));
//        return user;
//        AbstractClass abstrClss = new AbstractClass() {};
        return null;
    }
}
