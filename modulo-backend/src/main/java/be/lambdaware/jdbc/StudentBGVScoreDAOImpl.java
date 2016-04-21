package be.lambdaware.jdbc;

import be.lambdaware.dao.StudentBGVScoreDAO;
import be.lambdaware.entities.StudentBGVScoreEntity;
import be.lambdaware.mappers.StudentBGVScoreMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by jensv on 08-Apr-16.
 */
public class StudentBGVScoreDAOImpl extends AbstractDAOImpl implements StudentBGVScoreDAO {

    @Override
    public int create(StudentBGVScoreEntity entity) throws DataAccessException {
        String SQL = "INSERT INTO `student_bgv_score` (`student_id`, `competence_id`, `score`, `graded_date`, `remarks`) VALUES (?, ?, ?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getStudentId());
                statement.setInt(2, entity.getCompetenceId());
                statement.setString(3, entity.getScore());
                statement.setDate(4, entity.getGradedDate());
                statement.setString(5, entity.getRemarks());
                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public StudentBGVScoreEntity get(Integer id) throws DataAccessException {
        String SQL = "SELECT * FROM `student_bgv_score` WHERE `id` = ?";
        StudentBGVScoreEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new StudentBGVScoreMapper());
        return entity;
    }

    @Override
    public StudentBGVScoreEntity getByStudentAndCompetence(Integer studentId, Integer competenceId) {
        String SQL = "SELECT * FROM `student_bgv_score` WHERE `student_id` = ? AND `competence_id` = ?";
        StudentBGVScoreEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{studentId, competenceId}, new StudentBGVScoreMapper());
        return entity;
    }

    @Override
    public List<StudentBGVScoreEntity> getAll() throws DataAccessException {
        String SQL = "SELECT * FROM `student_bgv_score`";
        List<StudentBGVScoreEntity> entities = jdbcTemplate.query(SQL, new StudentBGVScoreMapper());
        return entities;
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        String SQL = "DELETE FROM `student_bgv_score` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
    }

    @Override
    public void update(StudentBGVScoreEntity entity) throws DataAccessException {
        String SQL = "UPDATE `student_bgv_score` SET `student_id` = ?, `competence_id` = ?, `score` = ?, `graded_date` =  ?, `remarks` = ? WHERE id = ?";
        jdbcTemplate.update(SQL, entity.getStudentId(), entity.getCompetenceId(), entity.getScore(), entity.getGradedDate(), entity.getRemarks(), entity.getId());
    }
}
