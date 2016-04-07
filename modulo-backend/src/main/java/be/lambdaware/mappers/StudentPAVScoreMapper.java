package be.lambdaware.mappers;

import be.lambdaware.entities.StudentPAVScoreEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jensv on 07-Apr-16.
 */
public class StudentPAVScoreMapper implements RowMapper<StudentPAVScoreEntity> {
    @Override
    public StudentPAVScoreEntity mapRow(ResultSet resultSet, int i) throws SQLException {

        StudentPAVScoreEntity entity = new StudentPAVScoreEntity();
        entity.setStudent_id(resultSet.getInt("student_id"));
        entity.setObjective_id(resultSet.getInt("objective_id"));
        entity.setScore(resultSet.getString("score"));
        entity.setGraded_date(resultSet.getDate("graded_date"));
        entity.setRemarks(resultSet.getString("remarks"));
        return entity;
    }
}
