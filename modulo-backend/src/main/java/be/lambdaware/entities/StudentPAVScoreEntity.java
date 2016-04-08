package be.lambdaware.entities;

import java.sql.Date;

/**
 * Created by jensv on 07-Apr-16.
 */
public class StudentPAVScoreEntity {
    private Integer id;
    private Integer studentId;
    private Integer objectiveId;
    private String score;
    private Date gradedDate;
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getObjectiveId() {
        return objectiveId;
    }

    public void setObjectiveId(Integer objectiveId) {
        this.objectiveId = objectiveId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Date getGradedDate() {
        return gradedDate;
    }

    public void setGradedDate(Date gradedDate) {
        this.gradedDate = gradedDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "StudentPAVScoreEntity{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", objectiveId=" + objectiveId +
                ", score='" + score + '\'' +
                ", gradedDate=" + gradedDate +
                ", remarks='" + remarks + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentPAVScoreEntity that = (StudentPAVScoreEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) return false;
        if (objectiveId != null ? !objectiveId.equals(that.objectiveId) : that.objectiveId != null) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;
        if (gradedDate != null ? !gradedDate.equals(that.gradedDate) : that.gradedDate != null) return false;
        return remarks != null ? remarks.equals(that.remarks) : that.remarks == null;

    }


}
