package be.lambdaware.entities;

/**
 * Created by martijn on 07/04/16.
 */
public class CertificateEntity {
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
        return "CertificateEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
