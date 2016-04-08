package be.lambdaware.jdbc;

import be.lambdaware.dao.StudentBGVScoreDAO;
import be.lambdaware.entities.StudentBGVScoreEntity;
import be.lambdaware.mappers.StudentBGVScoreMapper;
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
    public int create(StudentBGVScoreEntity entity) {
        String SQL = "INSERT INTO `student_bgv_score` (`student_id`, `competence_id`, `score`, `graded_date`, `remarks`) VALUES (?, ?, ?, ?, ?)";

        //TODO process result our catch SQL Exception
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getStudent_id());
                statement.setInt(2, entity.getCompetence_id());
                statement.setString(3, entity.getScore());
                statement.setDate(4, entity.getGraded_date());
                statement.setString(5, entity.getRemarks());
                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public StudentBGVScoreEntity get(Integer id) {
        String SQL = "SELECT * FROM `student_bgv_score` WHERE `id` = ?";
        StudentBGVScoreEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new StudentBGVScoreMapper());
        //TODO catch SQL Exception
        return entity;
    }

    @Override
    public List<StudentBGVScoreEntity> getAll() {
        String SQL = "SELECT * FROM `student_bgv_score`";
        List<StudentBGVScoreEntity> entities = jdbcTemplate.query(SQL, new StudentBGVScoreMapper());
        //TODO catch SQL Exception
        return entities;
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM `student_bgv_score` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
        //TODO catch SQL Exception
    }

    @Override
    public void update(StudentBGVScoreEntity entity) {
        String SQL = "UPDATE `student_bgv_score` SET `student_id` = ?, `objective_id` = ?, `score` = ?, `graded_date`, `remarks` = ? where id = ?";
        jdbcTemplate.update(SQL, entity.getStudent_id(), entity.getCompetence_id(), entity.getScore(), entity.getGraded_date(), entity.getRemarks());
        //TODO catch SQL Exception
    }
}
