package be.lambdaware.entities;

/**
 * Created by martijn on 07/04/16.
 */
public class StudentClassEntity {
    private Integer studentInfoId;
    private Integer classId;

    public StudentClassEntity(){}

    public StudentClassEntity(Integer studentInfoId, Integer classId){
        this.studentInfoId = studentInfoId;
        this.classId = classId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentClassEntity that = (StudentClassEntity) o;

        if (studentInfoId != null ? !studentInfoId.equals(that.studentInfoId) : that.studentInfoId != null)
            return false;
        return classId != null ? classId.equals(that.classId) : that.classId == null;

    }
}
