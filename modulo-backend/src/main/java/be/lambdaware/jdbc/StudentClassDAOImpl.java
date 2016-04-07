package be.lambdaware.jdbc;

import be.lambdaware.dao.StudentClassDAO;
import be.lambdaware.entities.StudentClassEntity;
import be.lambdaware.mappers.StudentClassMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by martijn on 07/04/16.
 */
public class StudentClassDAOImpl extends AbstractDAOImpl implements StudentClassDAO {

    @Override
    public void create(StudentClassEntity entity) {
        String SQL = "INSERT INTO `student_class` (`student_id`, `class_id`) VALUES (?,?)";

        //TODO process result our catch SQL Exception
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1,entity.getStudentInfoId());
                statement.setInt(2,entity.getClassId());
                return statement;
            }
        }, holder);

    }

    @Override
    public StudentClassEntity get(Integer studentInfoId, Integer classEntityId) {
        /*String SQL = "SELECT * FROM `student_class` " +
                "JOIN `classes` ON `classes`.`id` = `student_class`.`class_id`" +
                "JOIN `student_info` ON `student_info`.`id` = `student_class`.`student_id`" +
                "JOIN `users` ON `users`.`id` = `student_info`.`user_id`" +
                "JOIN `parent_info` ON `parent_info`.`id` = `student_info`.`parent_id` " +
                "WHERE `student_id` = ? AND `class_id` = ?";*/
        String SQL = "SELECT * FROM `student_class` WHERE `student_id` = ? AND `class_id` = ?";
        StudentClassEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{studentInfoId, classEntityId}, new StudentClassMapper());
        //TODO catch SQL Exception
        return entity;
    }

    @Override
    public List<StudentClassEntity> getAll() {
        /*String SQL = "SELECT * FROM `student_class` " +
                "JOIN `classes` ON `classes`.`id` = `student_class`.`class_id`" +
                "JOIN `student_info` ON `student_info`.`id` = `student_class`.`student_id`" +
                "JOIN `users` ON `users`.`id` = `student_info`.`user_id`" +
                "JOIN `parent_info` ON `parent_info`.`id` = `student_info`.`parent_id` ";*/
        String SQL = "SELECT * FROM `student_class`";
        List<StudentClassEntity> entities = jdbcTemplate.query(SQL, new StudentClassMapper());
        //TODO catch SQL Exception
        return entities;
    }

    @Override
    public void delete(Integer studentInfoId, Integer classEntityId) {
        String SQL = "DELETE FROM `student_class` WHERE `student_id` = ? AND class_id = ?";
        jdbcTemplate.update(SQL, studentInfoId, classEntityId);
        //TODO catch SQL Exception
    }

}
