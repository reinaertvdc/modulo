package be.lambdaware.models;

import be.lambdaware.enums.ScoreType;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "pav_scores")
public class PAVScore {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    // ===================================================================================
    // Fields
    // ===================================================================================

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ScoreType score;

    @Column(nullable = false)
    private Date gradedDate;

    @Column
    private String remarks;

    // ===================================================================================
    // Relations
    // ===================================================================================

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private StudentInfo studentInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "objective_id")
    private Objective objective;

    // ===================================================================================

    public PAVScore() {

    }

    public PAVScore(ScoreType score, Date gradedDate, String remarks) {
        this.score = score;
        this.gradedDate = gradedDate;
        this.remarks = remarks;
    }

    // ===================================================================================
    // Field Accessors
    // ===================================================================================

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ScoreType getScore() {
        return score;
    }

    public void setScore(ScoreType score) {
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


    // ===================================================================================
    // Relation Accessors
    // ===================================================================================

    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
        if (!studentInfo.getPavScores().contains(this)) {
            studentInfo.getPavScores().add(this);
        }
    }

    public Objective getObjective() {
        return objective;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
        if (!objective.getPavScores().contains(this)) {
            objective.getPavScores().add(this);
        }
    }

    // ===================================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PAVScore pavScore = (PAVScore) o;

        return id == pavScore.id;

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PAVScore{");
        sb.append("id=").append(id);
        sb.append(", score=").append(score);
        sb.append(", gradedDate=").append(gradedDate);
        sb.append(", remarks='").append(remarks).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
