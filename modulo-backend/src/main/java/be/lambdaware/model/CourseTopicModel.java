package be.lambdaware.model;

import be.lambdaware.dao.CourseTopicDAO;
import be.lambdaware.dao.ObjectiveDAO;
import be.lambdaware.entities.CourseTopicEntity;
import be.lambdaware.entities.ObjectiveEntity;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;

/**
 * Created by MichielVM on 15/04/2016.
 */
public class CourseTopicModel {

    private CourseTopicEntity courseTopicEntity;
    private CourseTopicDAO courseTopicDAO;

    public CourseTopicModel() {}

    public CourseTopicModel(CourseTopicDAO courseTopicDAO) {
        this.courseTopicDAO = courseTopicDAO;
    }

    public CourseTopicEntity getCourseTopicEntity() {
        return courseTopicEntity;
    }

    public void setCourseTopicEntity(CourseTopicEntity courseTopicEntity) {
        this.courseTopicEntity = courseTopicEntity;
    }

    public void setCourseTopicDAO(CourseTopicDAO courseTopicDAO) {
        this.courseTopicDAO = courseTopicDAO;
    }

    @Override
    public String toString() {
        return "CourseTopicModel{" +
                "courseTopicEntity=" + courseTopicEntity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseTopicModel that = (CourseTopicModel) o;

        return courseTopicEntity.equals(that.courseTopicEntity);
    }


    public void createInDB() throws DataAccessException {
        int id = courseTopicDAO.create(courseTopicEntity);
        courseTopicEntity.setId(id);
    }

    public void getFromDB(Integer courseTopicId) throws DataAccessException {
        courseTopicEntity = courseTopicDAO.get(courseTopicId);
    }

    public void deleteFromDB() throws DataAccessException {
        courseTopicDAO.delete(courseTopicEntity.getId());
    }

    public void updateInDB() throws DataAccessException {
        courseTopicDAO.update(courseTopicEntity);
    }


    public ArrayList<ObjectiveModel> getObjectives(ObjectiveDAO objectiveDAO) {
        ArrayList<ObjectiveModel> objectives = new ArrayList<ObjectiveModel>();

        for(ObjectiveEntity entity : objectiveDAO.getByCourseTopicId(courseTopicEntity.getId())) {
            ObjectiveModel objective = new ObjectiveModel(objectiveDAO);
            objective.getFromDB(entity.getId());
            objectives.add(objective);
        }

        return objectives;
    }
}
