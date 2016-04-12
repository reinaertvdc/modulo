package be.lambdaware.model;

import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.entities.StudentInfoEntity;

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
    public boolean createInDB() {
        if (!super.createInDB())
            return false;

        int id = studentInfoDAO.create(studentInfoEntity);
        studentInfoEntity.setId(id);
        studentInfoEntity.setUser(userEntity.getId());

        return true;
    }

    @Override
    public boolean getFromDB(Integer userId) {
        if (!super.getFromDB(userId))
            return false;

        studentInfoEntity = studentInfoDAO.getByUserId(userId);
        if (studentInfoEntity == null)
            return false;

        return true;
    }

    public boolean getFromDBByStudentId(Integer studentId) {
        studentInfoEntity = studentInfoDAO.get(studentId);
        if (studentInfoEntity == null)
            return false;

        userEntity = userDAO.get(studentInfoEntity.getUser());
        if (userEntity == null)
            return false;

        return true;
    }

    @Override
    public boolean deleteFromDB() {
        if (!super.deleteFromDB())
            return false;

        studentInfoDAO.delete(studentInfoEntity.getId());
        return true;
    }

    @Override
    public boolean updateInDB() {
        if (!super.updateInDB())
            return false;

        studentInfoDAO.update(studentInfoEntity);
        return true;
    }

    @Override
    public String toString() {
        return "StudentModel{" +
                "userEntity=" + userEntity +
                "studentInfoEntity=" + studentInfoEntity +
                '}';
    }
}
