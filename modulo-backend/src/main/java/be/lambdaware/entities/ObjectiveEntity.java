package be.lambdaware.entities;

/**
 * Created by jensv on 07-Apr-16.
 */
public class ObjectiveEntity {
    private Integer id;
    private String name;
    private String customName;

    private Integer gradeId;
    private Integer courseTopicId;

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

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getCourseTopicId() {
        return courseTopicId;
    }

    public void setCourseTopicId(Integer courseTopicId) {
        this.courseTopicId = courseTopicId;
    }

    @Override
    public String toString() {
        return "ObjectiveEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customName='" + customName + '\'' +
                ", gradeId=" + gradeId +
                ", courseTopicId=" + courseTopicId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObjectiveEntity that = (ObjectiveEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (customName != null ? !customName.equals(that.customName) : that.customName != null) return false;
        if (gradeId != null ? !gradeId.equals(that.gradeId) : that.gradeId != null) return false;
        return courseTopicId != null ? courseTopicId.equals(that.courseTopicId) : that.courseTopicId == null;

    }
}
