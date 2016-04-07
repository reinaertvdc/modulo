package be.lambdaware.entities;

/**
 * Created by martijn on 07/04/16.
 */
public class UserClassEntity {
    private StudentInfo studentInfo;
    private ClassEntity classEntity;

    public UserClassEntity(){}

    public UserClassEntity(StudentInfo studentInfo, ClassEntity classEntity){
        this.studentInfo = studentInfo;
        this.classEntity = classEntity;
    }

    public StudentInfo getStudentInfo() { return studentInfo; }

    public void setStudentInfo(StudentInfo student) {
        this.studentInfo = student;
    }

    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    @Override
    public String toString() {
        return "UserClassEntity{" +
                "studentInfo=" + studentInfo +
                ", classEntity=" + classEntity +
                '}';
    }
}
