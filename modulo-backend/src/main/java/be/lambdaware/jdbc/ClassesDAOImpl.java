package be.lambdaware.jdbc;

import be.lambdaware.dao.ClassesDAO;
import be.lambdaware.entities.ClassEntity;
import be.lambdaware.entities.User;
import be.lambdaware.mappers.ClassesMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by Vincent on 06/04/16.
 */
public class ClassesDAOImpl extends AbstractDAOImpl implements ClassesDAO {

    @Override
    public int create(ClassEntity entity) {
        String SQL = "INSERT INTO `classes`(`teacher_id`, `name`, `type`) VALUES (?, ?, ?)";

        //TODO process result our catch SQL Exception
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getTeacher().getId());
                statement.setString(2, entity.getName());
                statement.setString(3, entity.getType());
                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public List<ClassEntity> getAll() {
        String SQL = "SELECT * FROM `classes` JOIN `users` ON `classes`.`teacher_id` = `users`.`id`";
        List<ClassEntity> entities = jdbcTemplate.query(SQL, new ClassesMapper());
        //TODO catch SQL Exception
        return entities;
    }

    public List<ClassEntity> getAllByTeacher(User teacher) {
        String SQL = "SELECT * FROM `classes` JOIN `users` ON `classes`.`teacher_id` = `users`.`id` WHERE `teacher_id` = ?";
        List<ClassEntity> entities = jdbcTemplate.query(SQL, new ClassesMapper(),teacher.getId());
        return entities;
    }
}
