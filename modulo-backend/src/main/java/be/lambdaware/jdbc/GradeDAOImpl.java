package be.lambdaware.jdbc;

import be.lambdaware.dao.GradeDAO;
import be.lambdaware.entities.GradeEntity;
import be.lambdaware.mappers.GradeMapper;
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
    public int create(GradeEntity entity) {
        String SQL = "INSERT INTO `grades` (`name`) VALUES (?)";

        //TODO process result our catch SQL Exception
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
    public GradeEntity get(Integer id) {
        String SQL = "SELECT * FROM `grades` WHERE `id` = ?";
        GradeEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new GradeMapper());
        //TODO catch SQL Exception
        return entity;
    }

    @Override
    public List<GradeEntity> getAll() {
        String SQL = "SELECT * FROM `grades`";
        List<GradeEntity> grades = jdbcTemplate.query(SQL, new GradeMapper());
        //TODO catch SQL Exception
        return grades;
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM `grades` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
        //TODO catch SQL Exception
    }

    @Override
    public void update(GradeEntity entity) {
        String SQL = "UPDATE `grades` SET name = ? WHERE id = ?";
        jdbcTemplate.update(SQL, entity.getName(), entity.getId());
        //TODO catch SQL Exception
    }
}
