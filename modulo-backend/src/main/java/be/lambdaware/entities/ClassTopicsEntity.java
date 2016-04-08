package be.lambdaware.entities;

/**
 * Created by jensv on 08-Apr-16.
 */
public class ClassTopicsEntity {
    private Integer course_topic_id;
    private Integer class_id;

    public Integer getCourse_topic_id() {
        return course_topic_id;
    }

    public void setCourse_topic_id(Integer course_topic_id) {
        this.course_topic_id = course_topic_id;
    }

    public Integer getClass_id() {
        return class_id;
    }

    public void setClass_id(Integer class_id) {
        this.class_id = class_id;
    }

    @Override
    public String toString() {
        return "ClassTopicsEntity{" +
                "course_topic_id=" + course_topic_id +
                ", class_id=" + class_id +
                '}';
    }
}
