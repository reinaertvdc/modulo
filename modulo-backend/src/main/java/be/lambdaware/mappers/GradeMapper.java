package be.lambdaware.mappers;


import be.lambdaware.entities.GradeEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vincent on 07/04/16.
 */
public class GradeMapper implements RowMapper<GradeEntity> {

    @Override
    public GradeEntity mapRow(ResultSet resultSet, int row) throws SQLException {
        GradeEntity grade = new GradeEntity();
        grade.setId(resultSet.getInt("id"));
        grade.setName(resultSet.getString("name"));
        return grade;
    }
}
