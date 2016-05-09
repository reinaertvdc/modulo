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

    @Enumerated(EnumType.STRING)
    private ScoreType score;

    @Column(nullable = false)
    private Date gradedDate;

    @Column
    private String remarks;

    @Column
    private String fileName;

    // ===================================================================================
    // Relations
    // ===================================================================================

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName; }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // ===================================================================================


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskScore score1 = (TaskScore) o;

        if (id != score1.id) return false;
        if (score != score1.score) return false;
        if (gradedDate != null ? !gradedDate.equals(score1.gradedDate) : score1.gradedDate != null) return false;
        if (remarks != null ? !remarks.equals(score1.remarks) : score1.remarks != null) return false;
        if (fileName != null ? !fileName.equals(score1.fileName) : score1.fileName != null) return false;
        if (task != null ? !task.equals(score1.task) : score1.task != null) return false;
        return user != null ? user.equals(score1.user) : score1.user == null;

    }

    @Override
    public String toString() {
        return "TaskScore{" +
                "id=" + id +
                ", score=" + score +
                ", gradedDate=" + gradedDate +
                ", remarks='" + remarks + '\'' +
                ", fileName='" + fileName + '\'' +
                ", task=" + task +
                ", user=" + user +
                '}';
    }
}
