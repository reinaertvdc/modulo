package be.lambdaware.jdbc;

import be.lambdaware.dao.StudentPAVScoreDAO;
import be.lambdaware.entities.StudentPAVScoreEntity;
import be.lambdaware.mappers.StudentPAVScoreMapper;
import org.springframework.dao.DataAccessException;
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
public class StudentPAVScoreDAOImpl extends AbstractDAOImpl implements StudentPAVScoreDAO {

    @Override
    public int create(StudentPAVScoreEntity entity) throws DataAccessException {
        String SQL = "INSERT INTO `student_pav_score` (`student_id`, `objective_id`, `score`, `graded_date`, `remarks`) VALUES (?, ?, ?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getStudentId());
                statement.setInt(2, entity.getObjectiveId());
                statement.setString(3, entity.getScore());
                statement.setDate(4, entity.getGradedDate());
                statement.setString(5, entity.getRemarks());
                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public StudentPAVScoreEntity get(Integer id) throws DataAccessException {
        String SQL = "SELECT * FROM `student_pav_score` WHERE `id` = ?";
        StudentPAVScoreEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new StudentPAVScoreMapper());
        return entity;
    }

    @Override
    public List<StudentPAVScoreEntity> getAll() throws DataAccessException {
        String SQL = "SELECT * FROM `student_pav_score`";
        List<StudentPAVScoreEntity> entities = jdbcTemplate.query(SQL, new StudentPAVScoreMapper());
        return entities;
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        String SQL = "DELETE FROM `student_pav_score` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
    }

    @Override
    public void update(StudentPAVScoreEntity entity) throws DataAccessException {
        String SQL = "UPDATE `student_pav_score` SET `student_id` = ?, `objective_id` = ?, `score` = ?, `graded_date` = ?, `remarks` = ? where id = ?";
        jdbcTemplate.update(SQL, entity.getStudentId(), entity.getObjectiveId(), entity.getScore(), entity.getGradedDate(), entity.getRemarks(),entity.getId());
    }
}
