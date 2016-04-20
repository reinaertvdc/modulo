package be.lambdaware.mappers;

import be.lambdaware.entities.GradeClassEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by martijn on 07/04/16.
 */
public class GradeClassMapper implements RowMapper<GradeClassEntity> {

    //private StudentInfoMapper studentInfoMapper;
    //private ClassMapper classesMapper;

    @Override
    public GradeClassEntity mapRow(ResultSet resultSet, int row) throws SQLException {
        GradeClassEntity entity = new GradeClassEntity();
        entity.setGradeId(resultSet.getInt("grade_id"));
        entity.setClassId(resultSet.getInt("class_id"));

        return entity;
    }
}
