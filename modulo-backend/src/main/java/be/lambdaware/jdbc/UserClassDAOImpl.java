package be.lambdaware.jdbc;

import be.lambdaware.dao.UserClassDAO;
import be.lambdaware.entities.ClassEntity;
import be.lambdaware.entities.StudentInfoEntity;
import be.lambdaware.entities.UserClassEntity;
import be.lambdaware.mappers.UserClassMapper;
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
public class UserClassDAOImpl extends AbstractDAOImpl implements UserClassDAO {

    @Override
    public void create(UserClassEntity entity) {
        String SQL = "INSERT INTO `user_class` (`student_id`, `class_id`) VALUES (?,?)";

        //TODO process result our catch SQL Exception
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1,entity.getStudentInfo().getId());
                statement.setInt(2,entity.getClassEntity().getId());
                return statement;
            }
        }, holder);

    }

    @Override
    public UserClassEntity get(StudentInfoEntity studentInfo, ClassEntity classEntity) {
        String SQL = "SELECT * FROM `user_class` " +
                "JOIN `classes` ON `classes`.`id` = `user_class`.`class_id`" +
                "JOIN `student_info` ON `student_info`.`id` = `user_class`.`student_id`" +
                "JOIN `users` ON `users`.`id` = `student_info`.`user_id`" +
                "JOIN `parent_info` ON `parent_info`.`id` = `student_info`.`parent_id` " +
                "WHERE `student_id` = ? AND `class_id` = ?";
        UserClassEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{studentInfo.getId(), classEntity.getId()}, new UserClassMapper());
        //TODO catch SQL Exception
        return entity;
    }

    @Override
    public List<UserClassEntity> getAll() {
        String SQL = "SELECT * FROM `user_class` " +
                "JOIN `classes` ON `classes`.`id` = `user_class`.`class_id`" +
                "JOIN `student_info` ON `student_info`.`id` = `user_class`.`student_id`" +
                "JOIN `users` ON `users`.`id` = `student_info`.`user_id`" +
                "JOIN `parent_info` ON `parent_info`.`id` = `student_info`.`parent_id` ";
        List<UserClassEntity> entities = jdbcTemplate.query(SQL, new UserClassMapper());
        //TODO catch SQL Exception
        return entities;
    }

    @Override
    public void delete(StudentInfoEntity studentInfo, ClassEntity classEntity) {
        String SQL = "DELETE FROM `user_class` WHERE `student_id` = ? AND class_id = ?";
        jdbcTemplate.update(SQL, studentInfo.getId(), classEntity.getId());
        //TODO catch SQL Exception
    }

}
