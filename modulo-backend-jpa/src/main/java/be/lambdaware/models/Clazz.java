/*
 *  Created by Lambdaware as part of the course "Software Engineering" @ Hasselt University.
 */

package be.lambdaware.models;

import be.lambdaware.enums.ClassType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "classes")
public class Clazz {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    // ===================================================================================
    // Fields
    // ===================================================================================

    @Column(nullable = false)
    private String name;

    // ===================================================================================
    // Relations
    // ===================================================================================

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClassType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "classes_users", joinColumns = @JoinColumn(name = "class_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private List<User> students = new ArrayList<>();

    // ===================================================================================

    public Clazz() {

    }

    public Clazz(String name, ClassType type) {
        this.name = name;
        this.type = type;
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

    public ClassType getType() {
        return type;
    }

    public void setType(ClassType type) {
        this.type = type;
    }

    // ===================================================================================
    // Relation Accessors
    // ===================================================================================


    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
        if (!teacher.getTeachedClasses().contains(this)) {
            teacher.getTeachedClasses().add(this);
        }
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
        if (!certificate.getClasses().contains(this)) {
            certificate.getClasses().add(this);
        }
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
        if(!grade.getClasses().contains(this)){
            grade.getClasses().add(this);
        }
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public void addStudent(User user) {
        this.students.add(user);
        if(!user.getClasses().contains(this)){
            user.getClasses().add(this);
        }
    }

    // ===================================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Clazz clazz = (Clazz) o;

        return id == clazz.id;

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Clazz{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
