package be.lambdaware.mappers;

import be.lambdaware.entities.ClassEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vincent on 06/04/16.
 */
public class ClassMapper implements RowMapper<ClassEntity> {



    @Override
    public ClassEntity mapRow(ResultSet resultSet, int row) throws SQLException {
        ClassEntity classEntity = new ClassEntity();
        classEntity.setId(resultSet.getInt("classes.id"));
        classEntity.setName(resultSet.getString("classes.name"));
        classEntity.setType(resultSet.getString("classes.type"));
        classEntity.setTeacherId(resultSet.getInt("classes.teacher_id"));

        return classEntity;
    }
}
