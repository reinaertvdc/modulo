package be.lambdaware.jdbc;

import be.lambdaware.dao.CourseTopicDAO;
import be.lambdaware.entities.CourseTopicEntity;
import be.lambdaware.mappers.CourseTopicMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by jensv on 07-Apr-16.
 */
public class CourseTopicDAOImpl extends AbstractDAOImpl implements CourseTopicDAO {

    public int create(CourseTopicEntity entity) throws DataAccessException {

        String SQL = "INSERT INTO `course_topics`(`name`) VALUES (?)";

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


    public CourseTopicEntity get(Integer id) throws DataAccessException {
        String SQL = "SELECT * FROM `course_topics` WHERE `id` = ?";
        CourseTopicEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new CourseTopicMapper());

        return entity;
    }


    public List<CourseTopicEntity> getAll() throws DataAccessException {
        String SQL = "SELECT * FROM `course_topics`";
        List<CourseTopicEntity> entities = jdbcTemplate.query(SQL, new CourseTopicMapper());
        return entities;
    }


    public void delete(Integer id) throws DataAccessException {
        String SQL = "DELETE FROM `course_topics` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
    }


    public void update(CourseTopicEntity entity) throws DataAccessException {
        String SQL = "UPDATE `course_topics` SET `name` = ? where id = ?";
        jdbcTemplate.update(SQL, entity.getName(), entity.getId());
    }
}
