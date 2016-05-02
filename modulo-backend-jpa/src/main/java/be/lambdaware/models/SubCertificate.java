/*
 *  Created by Lambdaware as part of the course "Software Engineering" @ Hasselt University.
 */

package be.lambdaware.models;

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
    private Certificate certificate;

    @OneToMany(mappedBy = "subCertificate")
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
        final StringBuffer sb = new StringBuffer("SubCertificate{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", customName='").append(customName).append('\'');
        sb.append(", enabled=").append(enabled);
        sb.append('}');
        return sb.toString();
    }
}
