package be.lambdaware.entities;

/**
 * Created by jensv on 07-Apr-16.
 */
public class ObjectiveEntity {
    private Integer id;
    private String name;
    private String custom_name;

    private Integer grade;
    private Integer course_topic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustom_name() {
        return custom_name;
    }

    public void setCustom_name(String custom_name) {
        this.custom_name = custom_name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getCourse_topic() {
        return course_topic;
    }

    public void setCourse_topic(Integer course_topic) {
        this.course_topic = course_topic;
    }

    @Override
    public String toString() {
        return "ObjectiveEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", custom_name='" + custom_name + '\'' +
                ", grade=" + grade +
                ", course_topic=" + course_topic +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObjectiveEntity that = (ObjectiveEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (custom_name != null ? !custom_name.equals(that.custom_name) : that.custom_name != null) return false;
        if (grade != null ? !grade.equals(that.grade) : that.grade != null) return false;
        return course_topic != null ? course_topic.equals(that.course_topic) : that.course_topic == null;

    }
}
