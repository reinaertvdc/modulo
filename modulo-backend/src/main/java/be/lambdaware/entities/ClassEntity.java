package be.lambdaware.entities;

/**
 * Created by Vincent on 06/04/16.
 */
public class ClassEntity {
    private Integer id;
    private Integer teacher_id;
    private String className;
    private String classType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    @Override
    public String toString() {
        return "ClassEntity{" +
                "id=" + id +
                ", teacher_id=" + teacher_id +
                ", className='" + className + '\'' +
                ", classType='" + classType + '\'' +
                '}';
    }
}
