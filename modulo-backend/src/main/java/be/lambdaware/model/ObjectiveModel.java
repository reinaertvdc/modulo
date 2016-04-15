package be.lambdaware.model;

import be.lambdaware.dao.GradeDAO;
import be.lambdaware.dao.ObjectiveDAO;
import be.lambdaware.entities.ObjectiveEntity;
import org.springframework.dao.DataAccessException;

/**
 * Created by MichielVM on 15/04/2016.
 */
public class ObjectiveModel {

    private ObjectiveEntity objectiveEntity;
    private ObjectiveDAO objectiveDAO;

    public ObjectiveModel() {}

    public ObjectiveModel(ObjectiveDAO objectiveDAO) {
        this.objectiveDAO = objectiveDAO;
    }

    public ObjectiveEntity getObjectiveEntity() {
        return objectiveEntity;
    }

    public void setObjectiveEntity(ObjectiveEntity objectiveEntity) {
        this.objectiveEntity = objectiveEntity;
    }

    public void setObjectiveDAO(ObjectiveDAO objectiveDAO) {
        this.objectiveDAO = objectiveDAO;
    }

    @Override
    public String toString() {
        return "ObjectiveModel{" +
                "objectiveEntity=" + objectiveEntity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObjectiveModel that = (ObjectiveModel) o;

        return objectiveEntity.equals(that.objectiveEntity);
    }


    public boolean createInDB() throws DataAccessException {
        int id = objectiveDAO.create(objectiveEntity);
        objectiveEntity.setId(id);
        return true;
    }

    public boolean getFromDB(Integer objectiveId) throws DataAccessException {
        objectiveEntity = objectiveDAO.get(objectiveId);
        if (objectiveEntity == null)
            return false;
        return true;
    }

    public boolean deleteFromDB() throws DataAccessException {
        objectiveDAO.delete(objectiveEntity.getId());
        return true;
    }

    public boolean updateInDB() throws DataAccessException {
        objectiveDAO.update(objectiveEntity);
        return true;
    }


    public GradeModel getGrade(GradeDAO gradeDAO) {
        GradeModel gradeModel = new GradeModel(gradeDAO);
        gradeModel.getFromDB(objectiveEntity.getGradeId());
        return gradeModel;
    }
}
