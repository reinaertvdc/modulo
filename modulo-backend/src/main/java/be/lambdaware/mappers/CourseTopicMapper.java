package be.lambdaware.mappers;

import be.lambdaware.entities.CourseTopicEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jensv on 07-Apr-16.
 */
public class CourseTopicMapper implements RowMapper<CourseTopicEntity> {
    @Override
    public CourseTopicEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        CourseTopicEntity courseTopic = new CourseTopicEntity();
        courseTopic.setId(resultSet.getInt("id"));
        courseTopic.setName(resultSet.getString("name"));
        return  courseTopic;
    }
}
