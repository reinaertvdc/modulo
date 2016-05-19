package be.lambdaware.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.json.PackageVersion;

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

    @Column(nullable = false)
    private boolean resit = true;

    // ===================================================================================
    // Relations
    // ===================================================================================

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Objective> objectives = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinTable(name = "course_topic_students", joinColumns = @JoinColumn(name = "course_topic_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> students = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clazz_id")
    private Clazz clazz;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "courseTopic")
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


    public boolean isResit() {
        return resit;
    }

    public void setResit(boolean resit) {
        this.resit = resit;
    }

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

    public void addObjective(Objective objective) {
        if(!this.objectives.contains(objective)) {
            this.objectives.add(objective);
        }

        if (!objective.getCourseTopic().contains(this)) {
            objective.getCourseTopic().add(this);
        }
    }

    public void removeStudent(User student){
        this.students.remove(students);
        if (student.getCourseTopics().contains(this)) {
            student.getCourseTopics().remove(this);
        }
    }

    public void removeObjective(Objective obj){
        this.objectives.remove(obj);
        if (obj.getCourseTopic().contains(this)) {
            obj.getCourseTopic().remove(this);
        }
    }

    // ===================================================================================


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseTopic that = (CourseTopic) o;

        if (id != that.id) return false;
        if (resit != that.resit) return false;
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
                ", resit=" + resit +
                ", clazz=" + clazz +
                ", grade=" + grade +
                '}';
    }
}
