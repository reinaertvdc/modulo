package be.lambdaware.modulomobile.models;

import java.sql.Date;

/**
 * Author: Hendrik Lievens
 * Date: 13/05/2016
 * UHasselt / Software Engineering / 2015 - 2016
 */
public class Task {

    public enum TaskStatus {
        EMPTY,
        SUBMITTED,
        GRADED
    }

    //string titel
    //string classname
    //date deadline
    //string score
    //string remarks
    //string description

    private String name;
    private Date deadLine;
    private String score;
    private String remarks;
    private String description;

    private TaskStatus status;


    public Task(String name, Date deadLine, String score, String remarks, String description, TaskStatus status) {
        this.name = name;
        this.deadLine = deadLine;
        this.score = score;
        this.remarks = remarks;
        this.description = description;
        this.status = status;
    }

    public Task() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }
}
