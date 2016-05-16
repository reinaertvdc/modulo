package be.lambdaware.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.json.PackageVersion;
//import com.sun.deploy.net.proxy.pac.PACFunctions;

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

    @Column
    private String description;

    // ===================================================================================
    // Relations
    // ===================================================================================

    @ManyToMany(mappedBy = "courseTopics")
    @JsonIgnore
    private List<Objective> objectives = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "course_topic_students", joinColumns = @JoinColumn(name = "course_topic_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private List<User> students = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clazz_id")
    private Clazz clazz;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @OneToMany(mappedBy = "courseTopic")
    @JsonIgnore
    private List<PAVScore> pavScores = new ArrayList<>();

    // ===================================================================================

    public CourseTopic() {

    }

    public CourseTopic(String name, String description) {
        this.name = name;
        this.description = description;
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

    public void setDescription(String description){this.description = description;}

    public String getDescription() {return this.description; }

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
        if (objective != null && objective.getCourseTopic() != this) {
            objective.addCourseTopic(this);
        }
    }

    public void setGrade(Grade grade){ this.grade = grade;}

    public Grade getGrade(){return this.grade;}

    public void setPavScores(List<PAVScore> pavScore){this.pavScores = pavScore;}

    public void addPavScore(PAVScore pavScore) {
        this.pavScores.add(pavScore);
    }

    public List<PAVScore> getPavScores(){return pavScores;}

    public void setClazz(Clazz clazz) {this.clazz = clazz;}

    public Clazz getClazz() {
        return clazz;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public void addStudent(User student) {
        if(!this.students.contains(student))
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

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (objectives != null ? !objectives.equals(that.objectives) : that.objectives != null) return false;
        if (students != null ? !students.equals(that.students) : that.students != null) return false;
        if (clazz != null ? !clazz.equals(that.clazz) : that.clazz != null) return false;
        if (grade != null ? !grade.equals(that.grade) : that.grade != null) return false;
        return pavScores != null ? pavScores.equals(that.pavScores) : that.pavScores == null;

    }


    @Override
    public String toString() {
        return "CourseTopic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", objectives=" + objectives +
                ", students=" + students +
                ", clazz=" + clazz +
                ", grade=" + grade +
                ", pavScores=" + pavScores +
                '}';
    }
}
