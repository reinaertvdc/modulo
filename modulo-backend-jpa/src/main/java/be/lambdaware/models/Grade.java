package be.lambdaware.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @OneToMany(mappedBy = "grade")
    @JsonBackReference
    private List<StudentInfo> students = new ArrayList<>();

    @OneToMany(mappedBy = "grade")
    private List<Clazz> classes = new ArrayList<>();

    @OneToMany(mappedBy = "grade")
    private List<Objective> objectives = new ArrayList<>();

    // ===================================================================================

    public Grade() {

    }

    public Grade(String name, boolean enabled) {
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

    public List<StudentInfo> getStudents() {
        return students;
    }

    public void setStudents(List<StudentInfo> students) {
        this.students = students;
    }

    public void addStudent(StudentInfo studentInfo) {
        this.students.add(studentInfo);
        if (studentInfo.getGrade() != this) {
            studentInfo.setGrade(this);
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
        if (clazz.getGrade() != this) {
            clazz.setGrade(this);
        }
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public void addObjective(Objective objective) {
        this.objectives.add(objective);
        if (objective.getGrade() != this) {
            objective.setGrade(this);
        }
    }

    // ===================================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grade grade = (Grade) o;

        return id == grade.id;

    }

    @Override
    public String toString() {
        String sb = "Grade{" + "id=" + id +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                '}';
        return sb;
    }
}
