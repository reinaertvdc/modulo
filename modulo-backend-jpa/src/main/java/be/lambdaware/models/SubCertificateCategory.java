package be.lambdaware.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "sub_certificate_categories")
public class SubCertificateCategory {

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
    @JoinColumn(name = "sub_certificate_id")
    @JsonIgnore
    private SubCertificate subCertificate;

    @OneToMany(mappedBy = "subCertificateCategory", orphanRemoval = true)
    private List<Competence> competences = new ArrayList<>();

    // ===================================================================================

    public SubCertificateCategory() {

    }

    public SubCertificateCategory(String name, String customName, boolean enabled) {
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

    public SubCertificate getSubCertificate() {
        return subCertificate;
    }

    public void setSubCertificate(SubCertificate subCertificate) {
        this.subCertificate = subCertificate;
        if (!subCertificate.getSubCertificateCategories().contains(this)) {
            subCertificate.getSubCertificateCategories().add(this);
        }
    }

    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }

    public void addCompetence(Competence competence) {
        this.competences.add(competence);
        if (competence.getSubCertificateCategory() != this) {
            competence.setSubCertificateCategory(this);
        }
    }

    // ===================================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubCertificateCategory that = (SubCertificateCategory) o;

        return id == that.id;

    }

    @Override
    public String toString() {
        String sb = "SubCertificateCategory{" + "enabled=" + enabled +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", customName='" + customName + '\'' +
                '}';
        return sb;
    }
}
