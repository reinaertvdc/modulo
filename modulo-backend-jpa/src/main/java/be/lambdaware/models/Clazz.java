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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClassType type;

    // ===================================================================================
    // Relations
    // ===================================================================================

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "certificate_id")
    @JsonIgnore
    private Certificate certificate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grade_id")
    @JsonIgnore
    private Grade grade;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "classes_users", joinColumns = @JoinColumn(name = "class_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private List<User> students = new ArrayList<>();

    @OneToMany(mappedBy = "clazz")
    @JsonIgnore
    private List<Task> tasks;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "classes_courseTopics", joinColumns = @JoinColumn(name = "class_id"), inverseJoinColumns = @JoinColumn(name = "courseTopic_id"))
    @JsonIgnore
    private List<CourseTopic> courseTopics;

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
        if (teacher != null && !teacher.getTeachedClasses().contains(this)) {
            teacher.getTeachedClasses().add(this);
        }
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
        if (certificate != null && !certificate.getClasses().contains(this)) {
            certificate.getClasses().add(this);
        }
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
        if (grade != null && !grade.getClasses().contains(this)) {
            grade.getClasses().add(this);
        }
    }

    public List<User> getStudents() {
        return students;
    }

    public List<CourseTopic> getCourseTopics() {
        return courseTopics;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public void addStudent(User user) {
        this.students.add(user);
        if (!user.getClasses().contains(this)) {
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
        String sb = "Clazz{" + "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
        return sb;
    }
}
