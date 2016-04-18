package be.lambdaware.jdbc;

import be.lambdaware.dao.StudentClassDAO;
import be.lambdaware.entities.StudentClassEntity;
import be.lambdaware.mappers.StudentClassMapper;
import org.springframework.dao.DataAccessException;
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
    public void create(StudentClassEntity entity) throws DataAccessException {
        String SQL = "INSERT INTO `student_class` (`student_info_id`, `class_id`) VALUES (?,?)";

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
    public StudentClassEntity get(Integer studentInfoId, Integer classEntityId) throws DataAccessException {
        String SQL = "SELECT * FROM `student_class` WHERE `student_info_id` = ? AND `class_id` = ?";
        StudentClassEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{studentInfoId, classEntityId}, new StudentClassMapper());
        return entity;
    }

    public List<StudentClassEntity> getByClass(Integer classEntityId) throws DataAccessException {
        String SQL = "SELECT * FROM `student_class` WHERE `class_id` = ?";
        List<StudentClassEntity> entity = jdbcTemplate.query(SQL, new Object[]{classEntityId}, new StudentClassMapper());
        return entity;
    }

    @Override
    public List<StudentClassEntity> getAll() throws DataAccessException {
        String SQL = "SELECT * FROM `student_class`";
        List<StudentClassEntity> entities = jdbcTemplate.query(SQL, new StudentClassMapper());
        return entities;
    }

    @Override
    public void delete(Integer studentInfoId, Integer classEntityId) throws DataAccessException {
        String SQL = "DELETE FROM `student_class` WHERE `student_info_id` = ? AND class_id = ?";
        jdbcTemplate.update(SQL, studentInfoId, classEntityId);
    }

}
