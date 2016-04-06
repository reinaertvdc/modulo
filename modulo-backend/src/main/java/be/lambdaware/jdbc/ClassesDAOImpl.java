package be.lambdaware.jdbc;

import be.lambdaware.dao.ClassesDAO;
import be.lambdaware.entities.ClassEntity;
import be.lambdaware.entities.User;
import be.lambdaware.mappers.ClassesMapper;
import be.lambdaware.model.AbstractClass;
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
        String SQL = "INSERT INTO `classes`(`teacherid`, `name`, `type`) VALUES (?, ?, ?)";

        //TODO process result our catch SQL Exception
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getTeacher_id());
                statement.setString(2, entity.getClassName());
                statement.setString(3, entity.getClassType());
                return statement;
            }
        }, holder);

//        return holder.getKey().intValue();
        return 0;
    }

    @Override
    public ClassEntity get(Integer id) {
        String SQL = "SELECT * FROM `classes` WHERE `id` = ?";
        ClassEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new ClassesMapper());
        //TODO catch SQL Exception
        return entity;
    }

    @Override
    public List<ClassEntity> getAll() {
        String SQL = "SELECT * FROM `classes`";
        List<ClassEntity> entities = jdbcTemplate.query(SQL, new ClassesMapper());
        return entities;
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM `classes` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
        //TODO catch SQL Exception
    }

    @Override
    public void update(ClassEntity entity) {
        String SQL = "UPDATE `classes` SET teacher_id = ?, name = ?, type = ?";
        jdbcTemplate.update(SQL, entity.getTeacher_id(), entity.getClassName(), entity.getClassType(),entity.getId());
        //TODO catch SQL Exception
    }
}
