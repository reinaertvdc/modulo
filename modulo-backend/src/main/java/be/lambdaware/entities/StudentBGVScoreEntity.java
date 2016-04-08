package be.lambdaware.entities;

import java.sql.Date;

/**
 * Created by jensv on 08-Apr-16.
 */
public class StudentBGVScoreEntity {
    private Integer id;
    private Integer student_id;
    private Integer competence_id;
    private String score;
    private Date graded_date;
    private String remarks;

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public Integer getCompetence_id() {
        return competence_id;
    }

    public void setCompetence_id(Integer objective_id) {
        this.competence_id = objective_id;
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
                ", competence_id=" + competence_id +
                ", score='" + score + '\'' +
                ", graded_date=" + graded_date +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
