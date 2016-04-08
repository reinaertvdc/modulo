package be.lambdaware.entities;

import java.util.List;

/**
 * Created by MichielVM on 8/04/2016.
 */
public class SubCertificateCategoryEntity {
    private Integer id;
    private Integer subCertificateId;
    private String name;
    private String description;
    private String customName;
    private String customDescription;
    private Boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubCertificateId() {
        return subCertificateId;
    }

    public void setSubCertificateId(Integer subCertificateId) {
        this.subCertificateId = subCertificateId;
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

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getCustomDescription() {
        return customDescription;
    }

    public void setCustomDescription(String customDescription) {
        this.customDescription = customDescription;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "SubCertificateCategoryEntity{" +
                "subCertificateId=" + subCertificateId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", customName='" + customName + '\'' +
                ", customDescription='" + customDescription + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
