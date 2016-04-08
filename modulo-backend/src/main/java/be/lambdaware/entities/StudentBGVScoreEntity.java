package be.lambdaware.entities;

import java.sql.Date;

/**
 * Created by jensv on 08-Apr-16.
 */
public class StudentBGVScoreEntity {
    private Integer id;
    private Integer studentId;
    private Integer competenceId;
    private String score;
    private Date gradedDate;
    private String remarks;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(Integer objective_id) {
        this.competenceId = objective_id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "StudentBGVScoreEntity{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", competenceId=" + competenceId +
                ", score='" + score + '\'' +
                ", gradedDate=" + gradedDate +
                ", remarks='" + remarks + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentBGVScoreEntity that = (StudentBGVScoreEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) return false;
        if (competenceId != null ? !competenceId.equals(that.competenceId) : that.competenceId != null) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;
        if (gradedDate != null ? !gradedDate.equals(that.gradedDate) : that.gradedDate != null) return false;
        return remarks != null ? remarks.equals(that.remarks) : that.remarks == null;

    }

}
