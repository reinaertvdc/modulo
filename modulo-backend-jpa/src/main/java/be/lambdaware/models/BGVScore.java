package be.lambdaware.models;

import be.lambdaware.enums.ScoreType;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "bgv_scores")
public class BGVScore {

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
    @JoinColumn(name = "competence_id")
    private Competence competence;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private StudentInfo studentInfo;

    // ===================================================================================

    public BGVScore() {

    }

    public BGVScore(ScoreType score, Date gradedDate, String remarks) {
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

    public Competence getCompetence() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
        if (!competence.getBgvScores().contains(this)) {
            competence.getBgvScores().add(this);
        }
    }

    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
        if (!studentInfo.getBgvScores().contains(this)) {
            studentInfo.getBgvScores().add(this);
        }
    }

    // ===================================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BGVScore bgvScore = (BGVScore) o;

        return id == bgvScore.id;

    }

    @Override
    public String toString() {
        String sb = "BGVScore{" + "remarks='" + remarks + '\'' +
                ", gradedDate=" + gradedDate +
                ", score=" + score +
                ", id=" + id +
                '}';
        return sb;
    }
}
