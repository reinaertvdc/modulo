package be.lambdaware.jdbc;

import be.lambdaware.dao.ObjectiveDAO;
import be.lambdaware.entities.ObjectiveEntity;
import be.lambdaware.mappers.ObjectiveMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.*;
import java.util.List;

/**
 * Created by jensv on 07-Apr-16.
 */
public class ObjectiveDAOImpl extends AbstractDAOImpl implements ObjectiveDAO {
    @Override
    public int create(ObjectiveEntity entity) throws DataAccessException  {
        String SQL = "INSERT INTO `objectives`(`grade_id`, `course_topic_id`, `name`, `custom_name`) VALUES (?,?,?,?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                System.out.println(entity);
                statement.setInt(1, entity.getGradeId());
                if(entity.getCourseTopicId() == null)
                    statement.setNull(2, Types.INTEGER);
                else
                    statement.setInt(2, entity.getCourseTopicId());
                statement.setString(3, entity.getName());
                statement.setString(4, entity.getCustomName());
                return statement;
            }
        }, holder);
        return holder.getKey().intValue();
    }

    @Override
    public ObjectiveEntity get(Integer id) throws DataAccessException {
        String SQL = "SELECT * FROM `objectives` WHERE `id` = ?";
        ObjectiveEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new ObjectiveMapper());
        return entity;
    }

    @Override
    public List<ObjectiveEntity> getAll() throws DataAccessException {
        //String SQL = "SELECT * FROM `objectives` JOIN `grades` ON `objectives`.`grade_id` = `grades`.`id` JOIN `course_topic` ON `objectives`.`course_topic_id` = `course_topic`.`id`";
        String SQL = "SELECT * FROM `objectives`";
        List<ObjectiveEntity> entities = jdbcTemplate.query(SQL, new ObjectiveMapper());
        return entities;
    }

    @Override
    public List<ObjectiveEntity> getByGradeId(Integer gradeId) throws DataAccessException {
        //String SQL = "SELECT * FROM `objtectives` JOIN `grade` ON `objtectives`.`grade_id` = `grade`.`id` WHERE `grade_id` = ?";
        String SQL = "SELECT * FROM `objectives` WHERE `grade_id` = ?";
        List<ObjectiveEntity> entities = jdbcTemplate.query(SQL, new Object[]{gradeId},new ObjectiveMapper());
        return entities;
    }

    @Override
    public List<ObjectiveEntity> getByCourseTopicId(Integer courseTopicId) throws DataAccessException{
        //String SQL = "SELECT * FROM `objtectives` JOIN `course_topic` ON `objtectives`.`course_topic_id` = `course_topic`.`id` WHERE `course_topic_id` = ?";
        String SQL = "SELECT * FROM `objectives` WHERE `course_topic_id` = ?";
        List<ObjectiveEntity> entities = jdbcTemplate.query(SQL, new Object[]{courseTopicId},new ObjectiveMapper());
        return entities;
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        String SQL = "DELETE FROM `objectives` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
    }

    @Override
    public void update(ObjectiveEntity entity) throws DataAccessException {
        String SQL = "UPDATE `objectives` SET `grade_id` = ?, `course_topic_id` = ?, `name` = ?, `custom_name` = ? where `id` = ?";
        jdbcTemplate.update(SQL, entity.getGradeId(), entity.getCourseTopicId(), entity.getName(), entity.getCustomName(), entity.getId());
    }
}
