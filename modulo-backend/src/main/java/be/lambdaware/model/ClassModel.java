package be.lambdaware.model;

import be.lambdaware.dao.ClassDAO;
import be.lambdaware.entities.ClassEntity;
import be.lambdaware.entities.StudentClassEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by Vincent on 08/04/16.
 */
public abstract class ClassModel {
    // models
//    private TeacherModel teacherModel;
//    private List<StudentModel> studentModels;

    // entities
    private ClassEntity classEntity;

    // DAO's
    private ClassDAO classDAO;

    public ClassModel() {}

    public ClassModel(ClassDAO classDAO) {
        this.classDAO = classDAO;
    }

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
