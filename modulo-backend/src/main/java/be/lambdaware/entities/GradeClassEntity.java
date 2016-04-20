package be.lambdaware.entities;

/**
 * Created by martijn on 07/04/16.
 */
public class GradeClassEntity {

    private Integer gradeId;
    private Integer classId;

    public GradeClassEntity(){}

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "GradeClassEntity{" +
                "gradeId=" + gradeId +
                ", classId=" + classId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GradeClassEntity that = (GradeClassEntity) o;

        if (gradeId != null ? !gradeId.equals(that.gradeId) : that.gradeId != null) return false;
        return classId != null ? classId.equals(that.classId) : that.classId == null;

    }

}
