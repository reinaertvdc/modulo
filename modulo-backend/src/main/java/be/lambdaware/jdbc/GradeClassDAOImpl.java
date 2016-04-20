package be.lambdaware.jdbc;

import be.lambdaware.dao.GradeClassDAO;
import be.lambdaware.entities.GradeClassEntity;
import be.lambdaware.mappers.GradeClassMapper;
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
public class GradeClassDAOImpl extends AbstractDAOImpl implements GradeClassDAO {

    @Override
    public void create(GradeClassEntity entity) throws DataAccessException {
        String SQL = "INSERT INTO `grade_class` (`grade_id`, `class_id`) VALUES (?,?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1,entity.getGradeId());
                statement.setInt(2,entity.getClassId());
                return statement;
            }
        }, holder);

    }

    @Override
    public GradeClassEntity get(Integer gradeId, Integer classId) throws DataAccessException {
        String SQL = "SELECT * FROM `grade_class` WHERE `grade_id` = ? AND `class_id` = ?";
        GradeClassEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{gradeId, classId}, new GradeClassMapper());
        return entity;
    }

    public List<GradeClassEntity> getByClass(Integer classId) throws DataAccessException{
        String SQL = "SELECT * FROM `grade_class` WHERE `class_id` = ?";
        List<GradeClassEntity> entity = jdbcTemplate.query(SQL, new Object[]{classId}, new GradeClassMapper());
        return entity;
    }

    public List<GradeClassEntity> getByGrade(Integer gradeId) throws DataAccessException{
        String SQL = "SELECT * FROM `grade_class` WHERE `grade_id` = ?";
        List<GradeClassEntity> entity = jdbcTemplate.query(SQL, new Object[]{gradeId}, new GradeClassMapper());
        return entity;
    }

    @Override
    public List<GradeClassEntity> getAll() throws DataAccessException {
        String SQL = "SELECT * FROM `grade_class`";
        List<GradeClassEntity> entities = jdbcTemplate.query(SQL, new GradeClassMapper());
        return entities;
    }

    @Override
    public void delete(Integer gradeId, Integer classId) throws DataAccessException {
        String SQL = "DELETE FROM `grade_class` WHERE `class_id` = ? AND `grade_id` = ?";
        jdbcTemplate.update(SQL, classId, gradeId);
    }

}
