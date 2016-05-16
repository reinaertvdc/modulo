package be.lambdaware.modulomobile.models;

import java.sql.Date;

/**
 * Created by hendrik on 13/05/16.
 */
public class Task {

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

    public Task(String name, Date deadLine, String score, String remarks, String description) {
        this.name = name;
        this.deadLine = deadLine;
        this.score = score;
        this.remarks = remarks;
        this.description = description;
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
}
