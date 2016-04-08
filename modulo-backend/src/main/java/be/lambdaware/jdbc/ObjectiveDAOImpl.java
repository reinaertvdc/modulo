package be.lambdaware.jdbc;

import be.lambdaware.dao.ObjectiveDAO;
import be.lambdaware.entities.CourseTopicEntity;
import be.lambdaware.entities.GradeEntity;
import be.lambdaware.entities.ObjectiveEntity;
import be.lambdaware.mappers.ObjectiveMapper;
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
public class ObjectiveDAOImpl extends AbstractDAOImpl implements ObjectiveDAO {
    @Override
    public int create(ObjectiveEntity entity) {
        String SQL = "INSERT INTO `objectives`(`grade_id`, `course_topic_id`, `name`, `custom_name`) VALUES (?,?,?,?)";

        //TODO process result our catch SQL Exception
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                System.out.println(entity);
                statement.setInt(1, entity.getGradeId());
                statement.setInt(2, entity.getCourseTopicId());
                statement.setString(3, entity.getName());
                statement.setString(4, entity.getCustomName());
                return statement;
            }
        }, holder);
        return holder.getKey().intValue();
    }

    @Override
    public ObjectiveEntity get(Integer id) {
        String SQL = "SELECT * FROM `objectives` WHERE `id` = ?";
        ObjectiveEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new ObjectiveMapper());
        //TODO catch SQL Exception
        return entity;
    }

    @Override
    public List<ObjectiveEntity> getAll() {
        //String SQL = "SELECT * FROM `objectives` JOIN `grades` ON `objectives`.`grade_id` = `grades`.`id` JOIN `course_topic` ON `objectives`.`course_topic_id` = `course_topic`.`id`";
        String SQL = "SELECT * FROM `objectives`";
        List<ObjectiveEntity> entities = jdbcTemplate.query(SQL, new ObjectiveMapper());
        //TODO catch SQL Exception
        return entities;
    }

    @Override
    public List<ObjectiveEntity> getByGradeId(Integer gradeId) {
        //String SQL = "SELECT * FROM `objtectives` JOIN `grade` ON `objtectives`.`grade_id` = `grade`.`id` WHERE `grade_id` = ?";
        String SQL = "SELECT * FROM `objectives` WHERE `grade_id` = ?";
        List<ObjectiveEntity> entities = jdbcTemplate.query(SQL, new Object[]{gradeId},new ObjectiveMapper());
        return entities;
    }

    @Override
    public List<ObjectiveEntity> getByCourseTopicId(Integer courseTopicId){
        //String SQL = "SELECT * FROM `objtectives` JOIN `course_topic` ON `objtectives`.`course_topic_id` = `course_topic`.`id` WHERE `course_topic_id` = ?";
        String SQL = "SELECT * FROM `objectives` WHERE `course_topic_id` = ?";
        List<ObjectiveEntity> entities = jdbcTemplate.query(SQL, new Object[]{courseTopicId},new ObjectiveMapper());
        return entities;
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM `objectives` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
        //TODO catch SQL Exception
    }

    @Override
    public void update(ObjectiveEntity entity) {
        String SQL = "UPDATE `objectives` SET `grade_id` = ?, `course_topic_id` = ?, `name` = ?, `custom_name` = ? where `id` = ?";
        jdbcTemplate.update(SQL, entity.getGradeId(), entity.getCourseTopicId(), entity.getName(), entity.getCustomName(), entity.getId());
        //TODO catch SQL Exception
    }
}
