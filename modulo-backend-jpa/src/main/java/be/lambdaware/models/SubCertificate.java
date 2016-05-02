package be.lambdaware.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "sub_certificates")
public class SubCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    // ===================================================================================
    // Fields
    // ===================================================================================

    @Column(nullable = false)
    private String name;

    @Column
    private String customName;

    @Column(nullable = false)
    private boolean enabled = true;

    // ===================================================================================
    // Relations
    // ===================================================================================

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "certificate_id")
    @JsonIgnore
    private Certificate certificate;

    @OneToMany(mappedBy = "subCertificate", orphanRemoval = true)
    private List<SubCertificateCategory> subCertificateCategories = new ArrayList<>();

    // ===================================================================================

    public SubCertificate() {

    }

    public SubCertificate(String name, String customName, boolean enabled) {
        this.name = name;
        this.customName = customName;
        this.enabled = enabled;
    }

    // ===================================================================================
    // Field Accessors
    // ===================================================================================

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    // ===================================================================================
    // Relation Accessors
    // ===================================================================================

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
        if (!certificate.getSubCertificates().contains(this)) {
            certificate.getSubCertificates().add(this);
        }
    }

    public List<SubCertificateCategory> getSubCertificateCategories() {
        return subCertificateCategories;
    }

    public void setSubCertificateCategories(List<SubCertificateCategory> subCertificateCategories) {
        this.subCertificateCategories = subCertificateCategories;
    }

    public void addSubCertificateCategory(SubCertificateCategory subCertificateCategory) {
        this.subCertificateCategories.add(subCertificateCategory);
        if (subCertificateCategory.getSubCertificate() != this) {
            subCertificateCategory.setSubCertificate(this);
        }
    }

    // ===================================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubCertificate that = (SubCertificate) o;

        return id == that.id;

    }

    @Override
    public String toString() {
        String sb = "SubCertificate{" + "id=" + id +
                ", name='" + name + '\'' +
                ", customName='" + customName + '\'' +
                ", enabled=" + enabled +
                '}';
        return sb;
    }
}
