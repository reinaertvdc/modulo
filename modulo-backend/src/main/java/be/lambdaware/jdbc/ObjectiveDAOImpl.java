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
        String SQL = "INSERT INTO `objective`(`grade_id`, `course_topic_id`, `name`, `custom_name`) VALUES (?,?,?,?)";

        //TODO process result our catch SQL Exception
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                System.out.println(entity);
                statement.setInt(1, entity.getGrade());
                statement.setInt(2, entity.getCourse_topic());
                statement.setString(3, entity.getName());
                statement.setString(4, entity.getCustom_name());
                return statement;
            }
        }, holder);
        return holder.getKey().intValue();
    }

    @Override
    public ObjectiveEntity get(Integer id) {
        String SQL = "SELECT * FROM `objective` WHERE `id` = ?";
        ObjectiveEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new ObjectiveMapper());
        //TODO catch SQL Exception
        return entity;
    }

    @Override
    public List<ObjectiveEntity> getAll() {
        String SQL = "SELECT * FROM `objective` JOIN `grade` ON `objective`.`grade_id` = `grade`.`id` JOIN `course_topic` ON `objective`.`course_topic_id` = `course_topic`.`id`";
        List<ObjectiveEntity> entities = jdbcTemplate.query(SQL, new ObjectiveMapper());
        //TODO catch SQL Exception
        return entities;
    }

    public List<ObjectiveEntity> getAllByGrade(GradeEntity grade) {
        String SQL = "SELECT * FROM `objective` JOIN `grade` ON `objective`.`grade_id` = `grade`.`id` WHERE `grade_id` = ?";
        List<ObjectiveEntity> entities = jdbcTemplate.query(SQL, new ObjectiveMapper(),grade.getId());
        return entities;
    }
    public List<ObjectiveEntity> getAllByCourseTopic(CourseTopicEntity courseTopic){
        String SQL = "SELECT * FROM `objective` JOIN `course_topic` ON `objective`.`course_topic_id` = `course_topic`.`id` WHERE `course_topic_id` = ?";
        List<ObjectiveEntity> entities = jdbcTemplate.query(SQL, new ObjectiveMapper(),courseTopic.getId());
        return entities;
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM `objective` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
        //TODO catch SQL Exception
    }

    @Override
    public void update(ObjectiveEntity entity) {
        String SQL = "UPDATE `sub_certificate` SET `certificate_id` = ?, `name` = ?, `description` = ?, `enabled` = ? where id = ?";
        jdbcTemplate.update(SQL, entity.getGrade(), entity.getCourse_topic(), entity.getName(), entity.getCustom_name());
        //TODO catch SQL Exception
    }
}
