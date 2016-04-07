package be.lambdaware.entities;

/**
 * Created by Vincent on 07/04/16.
 */
public class GradeEntity {
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
        return "GradeEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
