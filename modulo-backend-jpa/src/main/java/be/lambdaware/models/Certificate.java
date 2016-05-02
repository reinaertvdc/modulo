/*
 *  Created by Lambdaware as part of the course "Software Engineering" @ Hasselt University.
 */

package be.lambdaware.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Part of: modulo-backend
 * User: Hendrik
 * Date: 1/05/2016
 * Time: 13:00
 */
@Entity
@Table(name = "certificates")
public class Certificate {

    @Id
    @GeneratedValue
    private long id;

    // ===================================================================================
    // Fields
    // ===================================================================================

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean enabled = true;

    // ===================================================================================
    // Relations
    // ===================================================================================

    @OneToMany(mappedBy = "certificate")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<SubCertificate> subCertificates = new ArrayList<>();

    @OneToMany(mappedBy = "certificate")
    @JsonBackReference
    private List<StudentInfo> students = new ArrayList<>();

    @OneToMany(mappedBy = "certificate")
    @JsonIgnore
    private List<Clazz> classes = new ArrayList<>();

    // ===================================================================================

    public Certificate() {

    }

    public Certificate(String name, boolean enabled) {
        this.name = name;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    // ===================================================================================
    // Relation Accessors
    // ===================================================================================

    public List<SubCertificate> getSubCertificates() {
        return subCertificates;
    }

    public void setSubCertificates(List<SubCertificate> subCertificates) {
        this.subCertificates = subCertificates;
    }

    public void addSubCertificate(SubCertificate subCertificate) {
        this.subCertificates.add(subCertificate);
        if(subCertificate.getCertificate() != this) {
            subCertificate.setCertificate(this);
        }
    }

    public List<StudentInfo> getStudents() {
        return students;
    }

    public void setStudents(List<StudentInfo> students) {
        this.students = students;
    }

    public void addStudent(StudentInfo studentInfo) {
        this.students.add(studentInfo);
        if(studentInfo.getCertificate() != this) {
            studentInfo.setCertificate(this);
        }
    }

    public List<Clazz> getClasses() {
        return classes;
    }

    public void setClasses(List<Clazz> classes) {
        this.classes = classes;
    }

    public void addClass(Clazz clazz) {
        this.classes.add(clazz);
        if(clazz.getCertificate() != this) {
            clazz.setCertificate(this);
        }
    }

    // ===================================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Certificate that = (Certificate) o;

        return id == that.id;

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Certificate{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", enabled=").append(enabled);
        sb.append('}');
        return sb.toString();
    }
}
