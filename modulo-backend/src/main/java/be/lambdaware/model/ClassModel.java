package be.lambdaware.model;

import be.lambdaware.dao.ClassDAO;
import be.lambdaware.dao.StudentClassDAO;
import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.entities.ClassEntity;
import be.lambdaware.entities.StudentClassEntity;
import be.lambdaware.entities.StudentInfoEntity;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 08/04/16.
 */
public abstract class ClassModel {
    // models
//    private TeacherModel teacherModel;
//    private List<StudentModel> studentModels;

    // entities
    protected ClassEntity classEntity;

    // DAO's
    protected ClassDAO classDAO;

    public ClassModel() {}

    public ClassModel(ClassDAO classDAO) { this.classDAO = classDAO; }

    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    public void setClassDAO(ClassDAO classDAO) {
        this.classDAO = classDAO;
    }


    public void createInDB() throws DataAccessException {
        classDAO.create(classEntity);
    }

    public void getFromDB(Integer id) throws DataAccessException {
        classDAO.get(id);
    }

    public void getFromDBByTeacher(Integer teacherId) throws DataAccessException {
        classDAO.getAllByTeacher(teacherId);
    }

    public void deleteFromDB() throws DataAccessException {
        classDAO.delete(classEntity.getId());
    }

    public void updateInDB() throws DataAccessException {
        classDAO.update(classEntity);
    }

    public ArrayList<StudentModel> getStudents(UserDAO userDAO, StudentInfoDAO studentInfoDAO, StudentClassDAO studentClassDAO) throws DataAccessException {
        ArrayList<StudentModel> students = new ArrayList<>();

        // lus over alle userId's per class
        for (StudentClassEntity studentClassEntity : studentClassDAO.getByClass(classEntity.getId())) {
            StudentModel student = new StudentModel(userDAO, studentInfoDAO);
            student.getFromDBByStudentInfoId(studentClassEntity.getStudentInfoId());
            students.add(student);
        }

        return students;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassModel that = (ClassModel) o;

        return classEntity.equals(that.classEntity);

    }

    @Override
    public String toString() {
        return "ClassModel{" +
                "classEntity=" + classEntity +
                '}';
    }
}
