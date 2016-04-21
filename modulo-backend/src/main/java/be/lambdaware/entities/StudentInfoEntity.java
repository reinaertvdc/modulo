package be.lambdaware.entities;


import java.sql.Date;

/**
 * Created by Vincent on 06/04/16.
 */
public class StudentInfoEntity {
    private Integer id;

    private Integer user;
    private Integer parent;
    private Integer gradeId;
    private Integer certificateId;

    private Date birthDate;
    private String birthPlace;
    private String nationality;
    private String nationalId;  // national identification number
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

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
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

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
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

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Integer certificateId) {
        this.certificateId = certificateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentInfoEntity that = (StudentInfoEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (parent != null ? !parent.equals(that.parent) : that.parent != null) return false;
        if (gradeId != null ? !gradeId.equals(that.gradeId) : that.gradeId != null) return false;
        if (certificateId != null ? !certificateId.equals(that.certificateId) : that.certificateId != null)
            return false;
        if (birthDate != null ? !birthDate.equals(that.birthDate) : that.birthDate != null) return false;
        if (birthPlace != null ? !birthPlace.equals(that.birthPlace) : that.birthPlace != null) return false;
        if (nationality != null ? !nationality.equals(that.nationality) : that.nationality != null) return false;
        if (nationalId != null ? !nationalId.equals(that.nationalId) : that.nationalId != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (houseNumber != null ? !houseNumber.equals(that.houseNumber) : that.houseNumber != null) return false;
        if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (phoneParent != null ? !phoneParent.equals(that.phoneParent) : that.phoneParent != null) return false;
        if (phoneCell != null ? !phoneCell.equals(that.phoneCell) : that.phoneCell != null) return false;
        return bankAccount != null ? bankAccount.equals(that.bankAccount) : that.bankAccount == null;

    }

    @Override
    public String toString() {
        return "StudentInfoEntity{" +
                "id=" + id +
                ", user=" + user +
                ", parent=" + parent +
                ", gradeId=" + gradeId +
                ", certificateId=" + certificateId +
                ", birthDate=" + birthDate +
                ", birthPlace='" + birthPlace + '\'' +
                ", nationality='" + nationality + '\'' +
                ", nationalId='" + nationalId + '\'' +
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

