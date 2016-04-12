package be.lambdaware.jdbc;

import be.lambdaware.dao.ClassesDAO;
import be.lambdaware.entities.ClassEntity;
import be.lambdaware.mappers.ClassesMapper;
import org.springframework.dao.DataAccessException;
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
    public int create(ClassEntity entity) throws DataAccessException {
        String SQL = "INSERT INTO `classes`(`teacher_id`, `name`, `type`) VALUES (?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getTeacherId());
                statement.setString(2, entity.getName());
                statement.setString(3, entity.getType());
                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public ClassEntity get(Integer id) throws DataAccessException {
        String SQL = "SELECT * FROM `classes` JOIN `users` ON `classes`.`teacher_id` = `users`.`id` WHERE `classes`.`id` = ?";
        ClassEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id},new ClassesMapper());
        return entity;
    }

    @Override
    public List<ClassEntity> getAll() throws DataAccessException {
        String SQL = "SELECT * FROM `classes` JOIN `users` ON `classes`.`teacher_id` = `users`.`id`";
        List<ClassEntity> entities = jdbcTemplate.query(SQL, new ClassesMapper());
        return entities;
    }

    public List<ClassEntity> getAllByTeacher(Integer teacherId) throws DataAccessException {
        String SQL = "SELECT * FROM `classes` JOIN `users` ON `classes`.`teacher_id` = `users`.`id` WHERE `teacher_id` = ?";
        List<ClassEntity> entities = jdbcTemplate.query(SQL, new ClassesMapper(),teacherId);
        return entities;
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        String SQL = "DELETE FROM `classes` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
    }

    @Override
    public void update(ClassEntity entity) throws DataAccessException {
        String SQL = "UPDATE `classes` SET `name` = ?,`type` = ?,`teacher_id` = ? WHERE `id` = ?";
        jdbcTemplate.update(SQL, entity.getName(),entity.getType(),entity.getTeacherId(),entity.getId());
    }
}
