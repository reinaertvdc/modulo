package be.lambdaware.entities;

/**
 * Created by jensv on 08-Apr-16.
 */
public class ClassTopicEntity {
    private Integer courseTopicId;
    private Integer classId;

    public Integer getCourseTopicId() {
        return courseTopicId;
    }

    public void setCourseTopicId(Integer courseTopicId) {
        this.courseTopicId = courseTopicId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "ClassTopicEntity{" +
                "courseTopicId=" + courseTopicId +
                ", classId=" + classId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassTopicEntity that = (ClassTopicEntity) o;

        if (courseTopicId != null ? !courseTopicId.equals(that.courseTopicId) : that.courseTopicId != null)
            return false;
        return classId != null ? classId.equals(that.classId) : that.classId == null;

    }
}
