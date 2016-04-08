package be.lambdaware.model;

import be.lambdaware.entities.ParentInfoEntity;
import be.lambdaware.entities.StudentInfoEntity;
import be.lambdaware.entities.UserEntity;

/**
 * @author hendrik
 */
public class ParentModel extends AccountModel {
    private ParentInfoEntity parentInfoEntity;
    private StudentInfoEntity studentInfoEntity;

    public ParentInfoEntity getParentInfoEntity() {
        return parentInfoEntity;
    }

    public void setParentInfoEntity(ParentInfoEntity parentInfoEntity) {
        this.parentInfoEntity = parentInfoEntity;
    }

    public StudentInfoEntity getStudentInfoEntity() {
        return studentInfoEntity;
    }

    public void setStudentInfoEntity(StudentInfoEntity studentInfoEntity) {
        this.studentInfoEntity = studentInfoEntity;
    }
}
