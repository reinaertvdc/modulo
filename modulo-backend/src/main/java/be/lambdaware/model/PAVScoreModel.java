package be.lambdaware.model;

import be.lambdaware.dao.StudentPAVScoreDAO;
import be.lambdaware.entities.StudentPAVScoreEntity;
import org.springframework.dao.DataAccessException;

/**
 * Created by MichielVM on 15/04/2016.
 */
public class PAVScoreModel {

    private StudentPAVScoreEntity studentPAVScoreEntity;
    private StudentPAVScoreDAO studentPAVScoreDAO;

    public PAVScoreModel() {}

    public PAVScoreModel(StudentPAVScoreDAO studentPAVScoreDAO) {
        this.studentPAVScoreDAO = studentPAVScoreDAO;
    }

    public StudentPAVScoreEntity getStudentPAVScoreEntity() {
        return studentPAVScoreEntity;
    }

    public void setStudentPAVScoreEntity(StudentPAVScoreEntity studentPAVScoreEntity) {
        this.studentPAVScoreEntity = studentPAVScoreEntity;
    }

    public void setStudentPAVScoreDAO(StudentPAVScoreDAO studentPAVScoreDAO) {
        this.studentPAVScoreDAO = studentPAVScoreDAO;
    }

    @Override
    public String toString() {
        return "PAVScoreModel{" +
                "studentPAVScoreEntity=" + studentPAVScoreEntity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PAVScoreModel that = (PAVScoreModel) o;

        return studentPAVScoreEntity.equals(that.studentPAVScoreEntity);
    }


    public void createInDB() throws DataAccessException {
        int id = studentPAVScoreDAO.create(studentPAVScoreEntity);
        studentPAVScoreEntity.setId(id);
    }

    public void getFromDB(Integer gradeId) throws DataAccessException {
        studentPAVScoreEntity = studentPAVScoreDAO.get(gradeId);
    }

    public void deleteFromDB() throws DataAccessException {
        studentPAVScoreDAO.delete(studentPAVScoreEntity.getId());
    }

    public void updateInDB() throws DataAccessException {
        studentPAVScoreDAO.update(studentPAVScoreEntity);
    }

}
