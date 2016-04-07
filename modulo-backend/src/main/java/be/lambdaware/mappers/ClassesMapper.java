package be.lambdaware.mappers;

import be.lambdaware.entities.ClassEntity;
import be.lambdaware.entities.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vincent on 06/04/16.
 */
public class ClassesMapper implements RowMapper<ClassEntity> {

    private UserMapper userMapper;

    public ClassesMapper() {
        this.userMapper = new UserMapper();
    }

    @Override
    public ClassEntity mapRow(ResultSet resultSet, int row) throws SQLException {
        ClassEntity classEntity = new ClassEntity();
        classEntity.setId(resultSet.getInt("classes.id"));
        classEntity.setName(resultSet.getString("classes.name"));
        classEntity.setType(resultSet.getString("classes.type"));

        User teacher = this.userMapper.mapRow(resultSet, row);
        classEntity.setTeacher(teacher);

        return classEntity;
    }
}
