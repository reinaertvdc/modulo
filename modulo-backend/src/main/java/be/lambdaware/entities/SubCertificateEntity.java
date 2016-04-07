package be.lambdaware.entities;

/**
 * Created by MichielVM on 7/04/2016.
 */
public class SubCertificateEntity {

    private Integer id;
    private CertificateEntity certificate;
    private String name;
    private String description;
    private Boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CertificateEntity getCertificate() {
        return certificate;
    }

    public void setCertificate(CertificateEntity certificate) {
        this.certificate = certificate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "SubCertificateEntity{" +
                "id=" + id +
                ", certificate=" + certificate +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}