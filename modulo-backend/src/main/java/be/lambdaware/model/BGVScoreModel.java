package be.lambdaware.model;

import be.lambdaware.dao.StudentBGVScoreDAO;
import be.lambdaware.entities.StudentBGVScoreEntity;
import org.springframework.dao.DataAccessException;

/**
 * Created by MichielVM on 15/04/2016.
 */
public class BGVScoreModel {

    private StudentBGVScoreEntity studentBGVScoreEntity;
    private StudentBGVScoreDAO studentBGVScoreDAO;

    public BGVScoreModel() {}

    public BGVScoreModel(StudentBGVScoreDAO studentBGVScoreDAO) {
        this.studentBGVScoreDAO = studentBGVScoreDAO;
    }

    public StudentBGVScoreEntity getStudentBGVScoreEntity() {
        return studentBGVScoreEntity;
    }

    public void setStudentBGVScoreEntity(StudentBGVScoreEntity studentBGVScoreEntity) {
        this.studentBGVScoreEntity = studentBGVScoreEntity;
    }

    public void setStudentBGVScoreDAO(StudentBGVScoreDAO studentBGVScoreDAO) {
        this.studentBGVScoreDAO = studentBGVScoreDAO;
    }

    @Override
    public String toString() {
        return "BGVScoreModel{" +
                "studentBGVScoreEntity=" + studentBGVScoreEntity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BGVScoreModel that = (BGVScoreModel) o;

        return studentBGVScoreEntity.equals(that.studentBGVScoreEntity);
    }


    public void createInDB() throws DataAccessException {
        int id = studentBGVScoreDAO.create(studentBGVScoreEntity);
        studentBGVScoreEntity.setId(id);
    }

    public void getFromDB(Integer gradeId) throws DataAccessException {
        studentBGVScoreEntity = studentBGVScoreDAO.get(gradeId);
    }

    public void deleteFromDB() throws DataAccessException {
        studentBGVScoreDAO.delete(studentBGVScoreEntity.getId());
    }

    public void updateInDB() throws DataAccessException {
        studentBGVScoreDAO.update(studentBGVScoreEntity);
    }

}
