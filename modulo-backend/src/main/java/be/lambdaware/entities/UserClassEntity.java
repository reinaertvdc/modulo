package be.lambdaware.entities;

/**
 * Created by martijn on 07/04/16.
 */
public class UserClassEntity {
    private Integer student_id;
    private Integer class_id;

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public Integer getClass_id() {
        return class_id;
    }

    public void setClass_id(Integer class_id) {
        this.class_id = class_id;
    }

    @Override
    public String toString() {
        return "UserClassEntity{" +
                "student_id=" + student_id +
                ", class_id=" + class_id +
                '}';
    }
}
