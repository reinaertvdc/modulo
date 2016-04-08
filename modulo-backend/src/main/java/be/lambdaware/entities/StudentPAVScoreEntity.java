package be.lambdaware.entities;

import java.sql.Date;

/**
 * Created by jensv on 07-Apr-16.
 */
public class StudentPAVScoreEntity {
    private Integer id;
    private Integer student_id;
    private Integer objective_id;
    private String score;
    private Date graded_date;
    private String remarks;

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public Integer getObjective_id() {
        return objective_id;
    }

    public void setObjective_id(Integer objective_id) {
        this.objective_id = objective_id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Date getGraded_date() {
        return graded_date;
    }

    public void setGraded_date(Date graded_date) {
        this.graded_date = graded_date;
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
        return "StudentPAVScoreEntity{" +
                "id=" + id +
                ", student_id=" + student_id +
                ", objective_id=" + objective_id +
                ", score='" + score + '\'' +
                ", graded_date=" + graded_date +
                ", remarks='" + remarks + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentPAVScoreEntity that = (StudentPAVScoreEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (student_id != null ? !student_id.equals(that.student_id) : that.student_id != null) return false;
        if (objective_id != null ? !objective_id.equals(that.objective_id) : that.objective_id != null) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;
        if (graded_date != null ? !graded_date.equals(that.graded_date) : that.graded_date != null) return false;
        return remarks != null ? remarks.equals(that.remarks) : that.remarks == null;

    }
}
