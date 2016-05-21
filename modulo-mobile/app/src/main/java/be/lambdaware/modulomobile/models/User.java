package be.lambdaware.modulomobile.models;

/**
 * Created by Hendrik on 13/05/2016.
 */
public class User {

    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String sex;
    private String role;
    private boolean enabled;
    private boolean selected;

    private StudentInfo studentInfo;

    public User() {

    }

    public User(long id, String email, String firstName, String lastName, String sex, String role, boolean enabled) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.role = role;
        this.enabled = enabled;
    }

    public User(long id, String email, String lastName, String sex, String role, boolean enabled, StudentInfo studentInfo) {
        this.id = id;
        this.email = email;
        this.lastName = lastName;
        this.sex = sex;
        this.role = role;
        this.enabled = enabled;
        this.studentInfo = studentInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }

    public boolean isParent() {
        return role.equals("PARENT");
    }

    public boolean isStudent() {
        return role.equals("STUDENT");
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sex='" + sex + '\'' +
                ", role='" + role + '\'' +
                ", enabled=" + enabled +
                ", selected=" + selected +
                '}';
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
