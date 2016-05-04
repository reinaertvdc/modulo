package be.lambdaware.models;

import be.lambdaware.enums.ScoreType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by MichielVM on 3/05/2016.
 */

@Entity
@Table(name = "task_scores")
public class TaskScore {

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
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private StudentInfo studentInfo;

    // ===================================================================================

    public TaskScore() {

    }

    public TaskScore(ScoreType score, Date gradedDate, String remarks) {
        this.score = score;
        this.gradedDate = gradedDate;
        this.remarks = remarks;
    }

    // ===================================================================================
    // Field Accessors
    // ===================================================================================


    public String getRemarks() { return remarks; }

    public void setRemarks(String remarks) { this.remarks = remarks; }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public ScoreType getScore() { return score; }

    public void setScore(ScoreType score) { this.score = score; }

    public Date getGradedDate() { return gradedDate; }

    public void setGradedDate(Date gradedDate) { this.gradedDate = gradedDate; }

    // ===================================================================================
    // Relation Accessors
    // ===================================================================================


    public Task getTask() { return task; }

    public void setTask(Task task) {
        this.task = task;
        if (task != null && !task.getTaskScores().contains(this)) {
            task.getTaskScores().add(this);
        }
    }

    public StudentInfo getStudentInfo() { return studentInfo; }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
        if (studentInfo != null && !studentInfo.getTaskScores().contains(this)) {
            studentInfo.getTaskScores().add(this);
        }
    }

    // ===================================================================================


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskScore taskScore = (TaskScore) o;

        if (id != taskScore.id) return false;
        if (score != taskScore.score) return false;
        if (!gradedDate.equals(taskScore.gradedDate)) return false;
        if (!remarks.equals(taskScore.remarks)) return false;
        if (!task.equals(taskScore.task)) return false;
        return studentInfo.equals(taskScore.studentInfo);

    }

    @Override
    public String toString() {
        return "TaskScore{" +
                "id=" + id +
                ", score=" + score +
                ", gradedDate=" + gradedDate +
                ", remarks='" + remarks + '\'' +
                ", task=" + task +
                ", studentInfo=" + studentInfo +
                '}';
    }
}
