package be.lambdaware.model;

import be.lambdaware.entities.StudentInfoEntity;
import be.lambdaware.entities.UserEntity;

/**
 * @author hendrik
 */
public class StudentModel extends AccountModel{

    public static StudentModel create(UserEntity userEntity, StudentInfoEntity studentInfoEntity) {
        StudentModel studentModel = new StudentModel();
        studentModel.setUserEntity(userEntity);
        studentModel.setStudentInfoEntity(studentInfoEntity);
        return studentModel;
    }

    private StudentInfoEntity studentInfoEntity;

    public ParentModel parentModel;


    public StudentModel() {
        super();
    }

    public void setStudentInfoEntity(StudentInfoEntity studentInfoEntity) {
        this.studentInfoEntity = studentInfoEntity;
    }

    public StudentInfoEntity getStudentInfoEntity() {
        return studentInfoEntity;
    }
}
