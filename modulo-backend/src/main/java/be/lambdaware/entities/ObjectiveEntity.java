package be.lambdaware.entities;

/**
 * Created by jensv on 07-Apr-16.
 */
public class ObjectiveEntity {
    private Integer id;
    private String name;
    private String custom_name;

    private GradeEntity grade;
    private CourseTopicEntity course_topic;

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

    public GradeEntity getGrade() {
        return grade;
    }

    public void setGrade(GradeEntity grade) {
        this.grade = grade;
    }

    public CourseTopicEntity getCourse_topic() {
        return course_topic;
    }

    public void setCourse_topic(CourseTopicEntity course_topic) {
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
}
