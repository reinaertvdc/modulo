package be.lambdaware.jdbc;

import be.lambdaware.dao.UserDAO;
import be.lambdaware.entities.User;
import be.lambdaware.mappers.UserMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author hendrik
 */
public class UserDAOImpl extends AbstractDAOImpl implements UserDAO {

    @Override
    public int create(User entity) {
        String SQL = "INSERT INTO `users` (`email`, `password`, `type`) VALUES (?, ?, ?)";

        //TODO process result our catch SQL Exception
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, entity.getEmail());
                statement.setString(2, entity.getPassword());
                statement.setString(3, entity.getType());
                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public User get(Integer id) {
        String SQL = "SELECT * FROM `users` WHERE `id` = ?";
        User entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new UserMapper());
        //TODO catch SQL Exception
        return entity;
    }

    @Override
    public List<User> getAll() {
        String SQL = "SELECT * FROM `users`";
        List<User> entities = jdbcTemplate.query(SQL, new UserMapper());
        //TODO catch SQL Exception
        return entities;
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM `users` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
        //TODO catch SQL Exception
    }

    @Override
    public void update(User entity) {
        String SQL = "UPDATE `users` SET email = ?, password = ?, type = ? where id = ?";
        jdbcTemplate.update(SQL, entity.getEmail(), entity.getPassword(), entity.getType(),entity.getId());
        //TODO catch SQL Exception
    }
}
