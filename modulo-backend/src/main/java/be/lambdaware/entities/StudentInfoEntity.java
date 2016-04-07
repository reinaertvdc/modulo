package be.lambdaware.entities;


import java.sql.Date;

/**
 * Created by Vincent on 06/04/16.
 */
public class StudentInfoEntity {
    private Integer id;
//    private Integer user_id;
//    private Integer parent_id;

    private UserEntity user;
    private ParentInfoEntity parent;

    private String firstName;
    private String lastName;
    private Date birthDate;
    private String birthPlace;
    private String nationality;
    private String nationalIdentificationNumber;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String phoneParent;
    private String phoneCell;
    private String bankAccount;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ParentInfoEntity getParent() {
        return parent;
    }

    public void setParent(ParentInfoEntity parent) {
        this.parent = parent;
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

    public String getPhoneParent() {
        return phoneParent;
    }

    public void setPhoneParent(String phoneParent) {
        this.phoneParent = phoneParent;
    }

    public String getPhoneCell() {
        return phoneCell;
    }

    public void setPhoneCell(String phoneCell) {
        this.phoneCell = phoneCell;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public String toString() {
        return "StudentInfoEntity{" +
                "id=" + id +
                ", user=" + user +
                ", parent=" + parent +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", birthPlace='" + birthPlace + '\'' +
                ", nationality='" + nationality + '\'' +
                ", nationalIdentificationNumber='" + nationalIdentificationNumber + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", phoneParent='" + phoneParent + '\'' +
                ", phoneCell='" + phoneCell + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                '}';
    }
}

