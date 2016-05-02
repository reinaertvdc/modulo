package be.lambdaware.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "objectives")
public class Objective {

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
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_topic_id")
    private CourseTopic courseTopic;


    @OneToMany(mappedBy = "objective")
    private List<PAVScore> pavScores = new ArrayList<>();

    // ===================================================================================

    public Objective() {

    }

    public Objective(String name, String customName, boolean enabled) {
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

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
        if (!grade.getObjectives().contains(this)) {
            grade.getObjectives().add(this);
        }
    }

    public CourseTopic getCourseTopic() {
        return courseTopic;
    }

    public void setCourseTopic(CourseTopic courseTopic) {
        this.courseTopic = courseTopic;
        if (!courseTopic.getObjectives().contains(this)) {
            courseTopic.addObjective(this);
        }
    }

    public List<PAVScore> getPavScores() {
        return pavScores;
    }

    public void setPavScores(List<PAVScore> pavScores) {
        this.pavScores = pavScores;
    }

    public void addPavScore(PAVScore pavScore) {
        this.pavScores.add(pavScore);
        if (pavScore.getObjective() != this) {
            pavScore.setObjective(this);
        }
    }

    // ===================================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Objective objective = (Objective) o;

        return id == objective.id;

    }

    @Override
    public String toString() {
        String sb = "Objective{" + "id=" + id +
                ", name='" + name + '\'' +
                ", customName='" + customName + '\'' +
                ", enabled=" + enabled +
                '}';
        return sb;
    }

}
