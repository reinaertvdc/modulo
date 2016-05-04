package be.lambdaware.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MichielVM on 3/05/2016.
 */
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    // ===================================================================================
    // Fields
    // ===================================================================================

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private Date deadline;

    // ===================================================================================
    // Relations
    // ===================================================================================

    // TODO: on delete in controller: set the reference to the task to 'null' for all taskscores
    @OneToMany(mappedBy = "task")
    @JsonIgnore
    private List<TaskScore> taskScores = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id")
    private Clazz clazz;

    // ===================================================================================


    public Task() {
    }

    public Task(String name, String description, Date deadline) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    // ===================================================================================
    // Relation Accessors
    // ===================================================================================


    public List<TaskScore> getTaskScores() {
        return taskScores;
    }

    public void setTaskScores(List<TaskScore> taskScores) {
        this.taskScores = taskScores;
    }

    public void addTaskScore(TaskScore taskScore) {
        this.taskScores.add(taskScore);
        if(taskScore != null && taskScore.getTask() != this) {
            taskScore.setTask(this);
        }
    }

    public Clazz getClazz() { return clazz; }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    // ===================================================================================


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (id != task.id) return false;
        if (name != null ? !name.equals(task.name) : task.name != null) return false;
        if (description != null ? !description.equals(task.description) : task.description != null) return false;
        if (deadline != null ? !deadline.equals(task.deadline) : task.deadline != null) return false;
        if (taskScores != null ? !taskScores.equals(task.taskScores) : task.taskScores != null) return false;
        return clazz != null ? clazz.equals(task.clazz) : task.clazz == null;

    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", taskScores=" + taskScores +
                ", clazz=" + clazz +
                '}';
    }
}
