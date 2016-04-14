package be.lambdaware.entities;

/**
 * Created by MichielVM on 7/04/2016.
 */
public class SubCertificateEntity {

    private Integer id;
    private Integer certificateId;
    private String name;
    private String customName;
    private Boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Integer certificateId) {
        this.certificateId = certificateId;
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
                ", certificateId=" + certificateId +
                ", name='" + name + '\'' +
                ", customName='" + customName + '\'' +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubCertificateEntity that = (SubCertificateEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (certificateId != null ? !certificateId.equals(that.certificateId) : that.certificateId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (customName != null ? !customName.equals(that.customName) : that.customName != null) return false;
        return enabled != null ? enabled.equals(that.enabled) : that.enabled == null;

    }

}