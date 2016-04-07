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

        entity.setName(resultSet.getString("objective.name"));
        entity.setCustom_name(resultSet.getString("objective.custom_name"));
        entity.setGrade(resultSet.getInt("objective.grade_id"));
        entity.setCourse_topic(resultSet.getInt("objective.course_topic_id"));


        return entity;
    }
}
