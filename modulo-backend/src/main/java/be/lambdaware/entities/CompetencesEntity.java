package be.lambdaware.entities;

/**
 * Created by martijn on 08/04/16.
 */
public class CompetencesEntity {
    private Integer id;
    private Integer subCertificateCategoryId;
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

    public Integer getSubCertificateCategoryId() {
        return subCertificateCategoryId;
    }

    public void setSubCertificateCategoryId(Integer subCertificateCategoryId) {
        this.subCertificateCategoryId = subCertificateCategoryId;
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
        return "CompetencesEntity{" +
                "id=" + id +
                ", subCertificateCategoryId=" + subCertificateCategoryId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", customName='" + customName + '\'' +
                ", customDescription='" + customDescription + '\'' +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetencesEntity that = (CompetencesEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (subCertificateCategoryId != null ? !subCertificateCategoryId.equals(that.subCertificateCategoryId) : that.subCertificateCategoryId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (customName != null ? !customName.equals(that.customName) : that.customName != null) return false;
        if (customDescription != null ? !customDescription.equals(that.customDescription) : that.customDescription != null)
            return false;
        return enabled != null ? enabled.equals(that.enabled) : that.enabled == null;

    }
}
