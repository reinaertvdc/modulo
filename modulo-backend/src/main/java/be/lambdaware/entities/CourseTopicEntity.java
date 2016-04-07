package be.lambdaware.entities;

/**
 * Created by jensv on 07-Apr-16.
 */
public class CourseTopicEntity {

    private Integer id;
    private String name;

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

    @Override
    public String toString() {
        return "CourseTopicEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
