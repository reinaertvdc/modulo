package be.lambdaware.entities;


import java.sql.Date;

/**
 * Created by Vincent on 06/04/16.
 */
public class StudentInfo {
    private Integer id;
    private Integer user_id;
    private Integer parent_id;
    private String first_name;
    private String last_name;
    private Date birthdate;
    private String birth_place;
    private String nationality;
    private String national_identification_number;
    private String street;
    private String house_number;
    private String postal_code;
    private String city;
    private String phone_parent;
    private String phone_cell;
    private String bank_account;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getBirth_place() {
        return birth_place;
    }

    public void setBirth_place(String birth_place) {
        this.birth_place = birth_place;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNational_identification_number() {
        return national_identification_number;
    }

    public void setNational_identification_number(String national_identification_number) {
        this.national_identification_number = national_identification_number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse_number() {
        return house_number;
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone_parent() {
        return phone_parent;
    }

    public void setPhone_parent(String phone_parent) {
        this.phone_parent = phone_parent;
    }

    public String getPhone_cell() {
        return phone_cell;
    }

    public void setPhone_cell(String phone_cell) {
        this.phone_cell = phone_cell;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", parent_id=" + parent_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", birthdate=" + birthdate +
                ", birth_place='" + birth_place + '\'' +
                ", nationality='" + nationality + '\'' +
                ", national_identification_number='" + national_identification_number + '\'' +
                ", street='" + street + '\'' +
                ", house_number='" + house_number + '\'' +
                ", postal_code='" + postal_code + '\'' +
                ", city='" + city + '\'' +
                ", phone_parent='" + phone_parent + '\'' +
                ", phone_cell='" + phone_cell + '\'' +
                ", bank_account='" + bank_account + '\'' +
                '}';
    }
}

