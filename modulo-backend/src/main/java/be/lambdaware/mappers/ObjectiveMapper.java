package be.lambdaware.mappers;

import be.lambdaware.entities.CourseTopicEntity;
import be.lambdaware.entities.GradeEntity;
import be.lambdaware.entities.ObjectiveEntity;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jensv on 07-Apr-16.
 */
public class ObjectiveMapper implements RowMapper<ObjectiveEntity> {


    @Override
    public ObjectiveEntity mapRow(ResultSet resultSet, int row) throws SQLException {
        ObjectiveEntity entity = new ObjectiveEntity();
        entity.setId(resultSet.getInt("id"));
        entity.setName(resultSet.getString("objectives.name"));
        entity.setCustomName(resultSet.getString("objectives.custom_name"));
        entity.setGradeId(resultSet.getInt("objectives.grade_id"));
        entity.setCourseTopicId(resultSet.getInt("objectives.course_topic_id"));

        return entity;
    }
}
