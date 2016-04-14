package be.lambdaware.mappers;

import be.lambdaware.entities.ClassTopicEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jensv on 08-Apr-16.
 */
public class ClassTopicMapper implements RowMapper<ClassTopicEntity> {
    @Override
    public ClassTopicEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        ClassTopicEntity entity = new ClassTopicEntity();
        entity.setCourseTopicId(resultSet.getInt("course_topic_id"));
        entity.setClassId(resultSet.getInt("class_id"));
        return entity;
    }
}
