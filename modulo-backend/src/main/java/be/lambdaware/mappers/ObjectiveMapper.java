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
        Logger.getLogger("objectiveMapper").info("test");
        GradeEntity grade = new GradeEntityMapper().mapRow(resultSet, row);
        entity.setGrade(grade);
        CourseTopicEntity courseTopic = new CourseTopicMapper().mapRow(resultSet, row);
        entity.setCourse_topic(courseTopic);

        entity.setName(resultSet.getString("objective.name"));
        entity.setCustom_name(resultSet.getString("objective.custom_name"));


        return entity;
    }
}
