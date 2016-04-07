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

    public ClassesMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // TODO implement mapRow
    @Override
    public ClassEntity mapRow(ResultSet resultSet, int row) throws SQLException {
        ClassEntity classEntity = new ClassEntity();
        classEntity.setId(resultSet.getInt("id"));
        classEntity.setName(resultSet.getString("name"));
        classEntity.setType(resultSet.getString("type"));

        User teacher = this.userMapper.mapRow(resultSet, row);
        classEntity.setTeacher(teacher);

        System.out.println(classEntity);

        return classEntity;
    }
}
