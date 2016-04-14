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
    public boolean createInDB() throws DataAccessException {
        if (!super.createInDB())
            return false;

        studentInfoEntity.setUser(userEntity.getId());
        int id = studentInfoDAO.create(studentInfoEntity);
        studentInfoEntity.setId(id);

        return true;
    }

    @Override
    public boolean getFromDB(Integer userId) throws DataAccessException {
        if (!super.getFromDB(userId))
            return false;

        studentInfoEntity = studentInfoDAO.getByUserId(userId);
        if (studentInfoEntity == null)
            return false;

        return true;
    }

    public boolean getFromDBByStudentInfoId(Integer studentInfoId) throws DataAccessException {
        studentInfoEntity = studentInfoDAO.get(studentInfoId);
        if (studentInfoEntity == null)
            return false;

        userEntity = userDAO.get(studentInfoEntity.getUser());
        if (userEntity == null)
            return false;

        return true;
    }

    @Override
    public boolean deleteFromDB() throws DataAccessException {
        studentInfoDAO.delete(studentInfoEntity.getId());

        if (!super.deleteFromDB())
            return false;

        return true;
    }

    @Override
    public boolean updateInDB() throws DataAccessException {
        if (!super.updateInDB())
            return false;

        studentInfoDAO.update(studentInfoEntity);
        return true;
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
