package be.lambdaware.jdbc;

import be.lambdaware.dao.UserDAO;
import be.lambdaware.entities.UserEntity;
import be.lambdaware.mappers.UserMapper;
import org.springframework.dao.DataAccessException;
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
    public int create(UserEntity entity) throws DataAccessException {
        String SQL = "INSERT INTO `users` (`email`, `password`, `first_name`, `last_name`, `type`) VALUES (?, ?, ?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, entity.getEmail());
                statement.setString(2, entity.getPassword());
                statement.setString(3, entity.getFirstName());
                statement.setString(4, entity.getLastName());
                statement.setString(5, entity.getType());
                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public UserEntity get(Integer id) throws DataAccessException {
        String SQL = "SELECT * FROM `users` WHERE `id` = ?";
        UserEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new UserMapper());
        return entity;
    }

    @Override
    public UserEntity getByEmail(String email) throws DataAccessException {
        String SQL = "SELECT * FROM `users` WHERE `email` = ?";
        UserEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{email}, new UserMapper());
        return entity;
    }

    @Override
    public List<UserEntity> getAll() throws DataAccessException {
        String SQL = "SELECT * FROM `users`";
        List<UserEntity> entities = jdbcTemplate.query(SQL, new UserMapper());
        return entities;
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        String SQL = "DELETE FROM `users` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
    }

    @Override
    public void update(UserEntity entity) throws DataAccessException {
        String SQL = "UPDATE `users` SET email = ?, password = ?, first_name = ?, last_name = ?, type = ? where id = ?";
        jdbcTemplate.update(SQL, entity.getEmail(), entity.getPassword(), entity.getFirstName(), entity.getLastName(), entity.getType(),entity.getId());
    }
}
