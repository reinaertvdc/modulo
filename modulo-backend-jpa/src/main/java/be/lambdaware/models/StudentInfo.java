package be.lambdaware.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "student_info")
public class StudentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    // ===================================================================================
    // Fields
    // ===================================================================================

    @Column(nullable = false)
    private Date birthDate;

    @Column(nullable = false)
    private String birthPlace;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false, length = 11)
    private String nationalIdentificationNumber;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String houseNumber;

    @Column(nullable = false, length = 4)
    private String postalCode;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String emergencyNumber;

    @Column(nullable = false)
    private String bankAccount;

    // ===================================================================================
    // Relations
    // ===================================================================================

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "certificate_id")
    @JsonIgnore
    private Certificate certificate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grade_id")
    @JsonIgnore
    private Grade grade;

    @OneToMany(mappedBy = "studentInfo")
    @JsonIgnore
    private List<BGVScore> bgvScores = new ArrayList<>();

    @OneToMany(mappedBy = "studentInfo")
    @JsonIgnore
    private List<PAVScore> pavScores = new ArrayList<>();

    @ManyToMany(mappedBy = "students")
    @JsonIgnore
    private List<CourseTopic> courseTopics = new ArrayList<>();

    // ===================================================================================

    public StudentInfo() {

    }

    public StudentInfo(Date birthDate, String birthPlace, String nationality, String nationalIdentificationNumber, String street, String houseNumber, String postalCode, String city, String phoneNumber, String emergencyNumber, String bankAccount) {
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.nationality = nationality;
        this.nationalIdentificationNumber = nationalIdentificationNumber;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.emergencyNumber = emergencyNumber;
        this.bankAccount = bankAccount;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationalIdentificationNumber() {
        return nationalIdentificationNumber;
    }

    public void setNationalIdentificationNumber(String nationalIdentificationNumber) {
        this.nationalIdentificationNumber = nationalIdentificationNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    public void setEmergencyNumber(String emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }


    // ===================================================================================
    // Relation Accessors
    // ===================================================================================

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null && user.getStudentInfo() != this) {
            user.setStudentInfo(this);
        }
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
        if (!certificate.getStudents().contains(this)) {
            certificate.getStudents().add(this);
        }
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
        if (grade != null && !grade.getStudents().contains(this)) {
            grade.getStudents().add(this);
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
        if (bgvScore.getStudentInfo() != this) {
            bgvScore.setStudentInfo(this);
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
        if (pavScore.getStudentInfo() != this) {
            pavScore.setStudentInfo(this);
        }
    }

    public List<CourseTopic> getCourseTopics() {
        return courseTopics;
    }

    public void setCourseTopics(List<CourseTopic> courseTopics) {
        this.courseTopics = courseTopics;
    }

    public void addCourseTopic(CourseTopic courseTopic) {
        this.courseTopics.add(courseTopic);
        if (!courseTopic.getStudents().contains(this)) {
            courseTopic.getStudents().add(this);
        }
    }

    // ===================================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentInfo that = (StudentInfo) o;

        return id == that.id;

    }

    @Override
    public String toString() {
        String sb = "StudentInfo{" + "id=" + id +
                ", birthDate=" + birthDate +
                ", birthPlace='" + birthPlace + '\'' +
                ", nationality='" + nationality + '\'' +
                ", nationalIdentificationNumber='" + nationalIdentificationNumber + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emergencyNumber='" + emergencyNumber + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                '}';
        return sb;
    }
}
