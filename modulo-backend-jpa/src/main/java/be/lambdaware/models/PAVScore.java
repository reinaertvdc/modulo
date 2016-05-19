package be.lambdaware.models;

import be.lambdaware.enums.ScoreType;

import javax.persistence.*;


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
    private int week;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courseTopic_id")
    private CourseTopic courseTopic;

    // ===================================================================================

    public PAVScore() {

    }

    public PAVScore(ScoreType score, int week, String remarks) {
        this.score = score;
        this.week = week;
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

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
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
        if (studentInfo != null && !studentInfo.getPavScores().contains(this)) {
            studentInfo.getPavScores().add(this);
        }
    }

    public Objective getObjective() {
        return objective;
    }

    public CourseTopic getCourseTopic(){
        return courseTopic;
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
        String sb = "PAVScore{" + "id=" + id +
                ", score=" + score +
                ", week=" + week +
                ", remarks='" + remarks + '\'' +
                '}';
        return sb;
    }
}
