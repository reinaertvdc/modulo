package be.lambdaware.entities;

/**
 * Created by martijn on 07/04/16.
 */
public class StudentClassEntity {
    private Integer studentInfoId;
    private Integer classId;

    public StudentClassEntity(){}

    public Integer getStudentInfoId() {
        return studentInfoId;
    }

    public void setStudentInfoId(Integer studentInfoId) {
        this.studentInfoId = studentInfoId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "StudentClassEntity{" +
                "studentInfoId=" + studentInfoId +
                ", classId=" + classId +
                '}';
    }
}
