/*
 *  Created by Lambdaware as part of the course "Software Engineering" @ Hasselt University.
 */

package be.lambdaware.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "competences")
public class Competence {

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
    @JoinColumn(name = "sub_certificate_category_id")
    private SubCertificateCategory subCertificateCategory;

    @OneToMany(mappedBy = "competence")
    private List<BGVScore> bgvScores = new ArrayList<>();

    // ===================================================================================

    public Competence() {

    }

    public Competence(String name, String customName, boolean enabled) {
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

    public SubCertificateCategory getSubCertificateCategory() {
        return subCertificateCategory;
    }

    public void setSubCertificateCategory(SubCertificateCategory subCertificateCategory) {
        this.subCertificateCategory = subCertificateCategory;
        if (!subCertificateCategory.getCompetences().contains(this)) {
            subCertificateCategory.getCompetences().add(this);
        }
    }

    public List<BGVScore> getBgvScores() {
        return bgvScores;
    }

    public void setBgvScores(List<BGVScore> bgvScores) {
        this.bgvScores = bgvScores;
    }

    public void addBgvScore(BGVScore bgvScore) {
        this.bgvScores.add(bgvScore);
        if (bgvScore.getCompetence() != this) {
            bgvScore.setCompetence(this);
        }
    }

    // ===================================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Competence that = (Competence) o;

        return id == that.id;

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Competence{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", customName='").append(customName).append('\'');
        sb.append(", enabled=").append(enabled);
        sb.append('}');
        return sb.toString();
    }
}
