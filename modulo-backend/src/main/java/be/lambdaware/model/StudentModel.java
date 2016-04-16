package be.lambdaware.model;

import be.lambdaware.dao.ParentInfoDAO;
import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.entities.ParentInfoEntity;
import be.lambdaware.entities.StudentInfoEntity;
import org.springframework.dao.DataAccessException;

/**
 * @author Vincent
 */
public class StudentModel extends AccountModel {

    private StudentInfoEntity studentInfoEntity;
    private StudentInfoDAO studentInfoDAO;

    // TODO StudyProgress bijhouden
    // TODO class bijhouden -> cyclische referentie?

    public StudentModel() {}

    public StudentModel(UserDAO userDAO, StudentInfoDAO studentInfoDAO) {
        super(userDAO);
        this.studentInfoDAO = studentInfoDAO;
    }

    public StudentInfoEntity getStudentInfoEntity() {
        return studentInfoEntity;
    }

    public void setStudentInfoEntity(StudentInfoEntity studentInfoEntity) {
        this.studentInfoEntity = studentInfoEntity;
    }

    public void setStudentInfoDAO(StudentInfoDAO studentInfoDAO) {
        this.studentInfoDAO = studentInfoDAO;
    }

    @Override
    public void createInDB() throws DataAccessException {
        super.createInDB();

        studentInfoEntity.setUser(userEntity.getId());
        int id = studentInfoDAO.create(studentInfoEntity);
        studentInfoEntity.setId(id);
    }

    @Override
    public void getFromDB(Integer userId) throws DataAccessException {
        super.getFromDB(userId);
        studentInfoEntity = studentInfoDAO.getByUserId(userId);
    }

    public void getFromDBByStudentInfoId(Integer studentInfoId) throws DataAccessException {
        studentInfoEntity = studentInfoDAO.get(studentInfoId);
        userEntity = userDAO.get(studentInfoEntity.getUser());
    }

    @Override
    public void deleteFromDB() throws DataAccessException {
        studentInfoDAO.delete(studentInfoEntity.getId());
        super.deleteFromDB();
    }

    @Override
    public void updateInDB() throws DataAccessException {
        super.updateInDB();
        studentInfoDAO.update(studentInfoEntity);
    }

    public ParentModel getParent(ParentInfoDAO parentInfoDAO) throws DataAccessException {
        ParentModel parentModel = new ParentModel(userDAO, parentInfoDAO);
        parentModel.getFromDB(studentInfoEntity.getParent());
        return parentModel;
    }

    @Override
    public String toString() {
        return "StudentModel{" +
                "userEntity=" + userEntity +
                "studentInfoEntity=" + studentInfoEntity +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        StudentModel that = (StudentModel) o;

        return studentInfoEntity.equals(that.studentInfoEntity);

    }
}
