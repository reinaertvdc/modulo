package be.lambdaware.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "course_topics")
public class CourseTopic {

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

    @OneToMany(mappedBy = "courseTopic")
    private List<Objective> objectives = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "course_topics_students", joinColumns = @JoinColumn(name = "course_topic_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    @JsonIgnore
    private List<StudentInfo> students = new ArrayList<>();

    // ===================================================================================

    public CourseTopic() {

    }

    public CourseTopic(String name) {
        this.name = name;
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

    // ===================================================================================
    // Relation Accessors
    // ===================================================================================

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public void addObjective(Objective objective) {
        this.objectives.add(objective);
        if (objective.getCourseTopic() != this) {
            objective.setCourseTopic(this);
        }
    }

    public List<StudentInfo> getStudents() {
        return students;
    }

    public void setStudents(List<StudentInfo> students) {
        this.students = students;
    }

    public void addStudent(StudentInfo student) {
        this.students.add(student);
        if (!student.getCourseTopics().contains(this)) {
            student.getCourseTopics().add(this);
        }
    }

    // ===================================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseTopic that = (CourseTopic) o;

        return id == that.id;

    }

    @Override
    public String toString() {
        String sb = "CourseTopic{" + "name='" + name + '\'' +
                ", id=" + id +
                '}';
        return sb;
    }
}
