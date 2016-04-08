package be.lambdaware.model;

import be.lambdaware.entities.StudentInfoEntity;
import be.lambdaware.entities.UserEntity;

/**
 * @author hendrik
 */
public class StudentModel extends AccountModel{
    private StudentInfoEntity studentInfoEntity;
//    private List<ClassModel> schoolClasses; // TODO ClassModel toevoegen
//    private StudyProgress studyProgress; // TODO StudyProgressModel toevoegen


    public StudentInfoEntity getStudentInfoEntity() {
        return studentInfoEntity;
    }

    public void setStudentInfoEntity(StudentInfoEntity studentInfoEntity) {
        this.studentInfoEntity = studentInfoEntity;
    }
}
