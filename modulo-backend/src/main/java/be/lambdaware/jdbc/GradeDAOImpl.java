package be.lambdaware.jdbc;

import be.lambdaware.dao.GradeDAO;
import be.lambdaware.entities.GradeEntity;
import be.lambdaware.mappers.GradeMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by Vincent on 07/04/16.
 */
public class GradeDAOImpl extends AbstractDAOImpl implements GradeDAO {
    @Override
    public int create(GradeEntity entity) throws DataAccessException {
        String SQL = "INSERT INTO `grades` (`name`) VALUES (?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, entity.getName());

                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public GradeEntity get(Integer id) throws DataAccessException {
        String SQL = "SELECT * FROM `grades` WHERE `id` = ?";
        GradeEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new GradeMapper());
        return entity;
    }

    @Override
    public List<GradeEntity> getAll() throws DataAccessException {
        String SQL = "SELECT * FROM `grades`";
        List<GradeEntity> grades = jdbcTemplate.query(SQL, new GradeMapper());
        return grades;
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        String SQL = "DELETE FROM `grades` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
    }

    @Override
    public void update(GradeEntity entity) throws DataAccessException {
        String SQL = "UPDATE `grades` SET name = ? WHERE id = ?";
        jdbcTemplate.update(SQL, entity.getName(), entity.getId());
    }
}
