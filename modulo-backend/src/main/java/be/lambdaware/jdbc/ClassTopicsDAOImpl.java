package be.lambdaware.jdbc;

import be.lambdaware.dao.ClassTopicsDAO;
import be.lambdaware.entities.ClassTopicsEntity;
import be.lambdaware.mappers.ClassTopicsMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by jensv on 08-Apr-16.
 */
public class ClassTopicsDAOImpl extends AbstractDAOImpl implements ClassTopicsDAO {

    @Override
    public void create(ClassTopicsEntity entity) throws DataAccessException {
        String SQL = "INSERT INTO `class_topics`(`course_topic_id`, `class_id`) VALUES (?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getCourseTopicId());
                statement.setInt(2, entity.getClassId());
                return statement;
            }
        }, holder);

    }

    @Override
    public ClassTopicsEntity get(Integer courseTopicId, Integer classId) throws DataAccessException {
        String SQL = "SELECT * FROM `class_topics` WHERE `course_topic_id` = ? AND `class_id` = ?";
        ClassTopicsEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{courseTopicId, classId}, new ClassTopicsMapper());
        return entity;
    }

    @Override
    public List<ClassTopicsEntity> getAll() throws DataAccessException {
        String SQL = "SELECT * FROM `class_topics`";
        List<ClassTopicsEntity> entities = jdbcTemplate.query(SQL, new ClassTopicsMapper());
        return entities;
    }

    @Override
    public void delete(Integer courseTopicId, Integer classId) throws DataAccessException {
        String SQL = "DELETE FROM `class_topics` WHERE `course_topic_id` = ? AND class_id = ?";
        jdbcTemplate.update(SQL, courseTopicId, classId);
    }
}
