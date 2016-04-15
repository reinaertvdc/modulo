package be.lambdaware.model;

import be.lambdaware.dao.GradeDAO;
import be.lambdaware.dao.ObjectiveDAO;
import be.lambdaware.entities.GradeEntity;
import be.lambdaware.entities.ObjectiveEntity;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;

/**
 * Created by MichielVM on 15/04/2016.
 */
public class GradeModel {

    private GradeEntity gradeEntity;
    private GradeDAO gradeDAO;

    public GradeModel() {}

    public GradeModel(GradeDAO gradeDAO) {
        this.gradeDAO = gradeDAO;
    }

    public GradeEntity getGradeEntity() {
        return gradeEntity;
    }

    public void setGradeEntity(GradeEntity gradeEntity) {
        this.gradeEntity = gradeEntity;
    }

    public void setGradeDAO(GradeDAO gradeDAO) {
        this.gradeDAO = gradeDAO;
    }

    @Override
    public String toString() {
        return "GradeModel{" +
                "gradeEntity=" + gradeEntity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GradeModel that = (GradeModel) o;

        return gradeEntity.equals(that.gradeEntity);

    }


    public boolean createInDB() throws DataAccessException {
        int id = gradeDAO.create(gradeEntity);
        gradeEntity.setId(id);
        return true;
    }

    public boolean getFromDB(Integer gradeId) throws DataAccessException {
        gradeEntity = gradeDAO.get(gradeId);
        if (gradeEntity == null)
            return false;
        return true;
    }

    public boolean deleteFromDB() throws DataAccessException {
        gradeDAO.delete(gradeEntity.getId());
        return true;
    }

    public boolean updateInDB() throws DataAccessException {
        gradeDAO.update(gradeEntity);
        return true;
    }


    public ArrayList<ObjectiveModel> getObjectives(ObjectiveDAO objectiveDAO) {
        ArrayList<ObjectiveModel> objectives = new ArrayList<ObjectiveModel>();

        for(ObjectiveEntity entity : objectiveDAO.getByGradeId(gradeEntity.getId())) {
            ObjectiveModel objective = new ObjectiveModel(objectiveDAO);
            objective.getFromDB(entity.getId());
            objectives.add(objective);
        }

        return objectives;
    }
}
