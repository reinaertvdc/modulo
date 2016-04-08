package be.lambdaware.mappers;

import be.lambdaware.entities.StudentBGVScoreEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jensv on 08-Apr-16.
 */
public class StudentBGVScoreMapper implements RowMapper<StudentBGVScoreEntity> {
    @Override
    public StudentBGVScoreEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        StudentBGVScoreEntity entity = new StudentBGVScoreEntity();
        entity.setStudent_id(resultSet.getInt("student_id"));
        entity.setCompetence_id(resultSet.getInt("competence_id"));
        entity.setScore(resultSet.getString("score"));
        entity.setGraded_date(resultSet.getDate("graded_date"));
        entity.setRemarks(resultSet.getString("remarks"));
        return entity;
    }
}
