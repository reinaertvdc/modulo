package be.lambdaware.mappers;

import be.lambdaware.entities.StudentInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vincent on 06/04/16.
 */
public class StudentInfoMapper implements RowMapper<StudentInfo> {

    @Override
    public StudentInfo mapRow(ResultSet resultSet, int row) throws SQLException {
        StudentInfo studentInfo = new StudentInfo();

        studentInfo.setId(resultSet.getInt("id"));
        studentInfo.setUser_id(resultSet.getInt("user_id"));
        studentInfo.setParent_id(resultSet.getInt("parent_id"));
        studentInfo.setFirst_name(resultSet.getString("first_name"));
        studentInfo.setLast_name(resultSet.getString("last_name"));
        studentInfo.setBirthdate(resultSet.getDate("birthdate"));
        studentInfo.setNationality(resultSet.getString("nationality"));
        studentInfo.setNational_identification_number(resultSet.getString("national_identification_number"));
        studentInfo.setStreet(resultSet.getString("street"));
        studentInfo.setHouse_number(resultSet.getString("house_number"));
        studentInfo.setPostal_code(resultSet.getString("postal_code"));
        studentInfo.setCity(resultSet.getString("city"));
        studentInfo.setPhone_parent(resultSet.getString("phone_parent"));
        studentInfo.setPhone_cell(resultSet.getString("phone_cell"));
        studentInfo.setBank_account(resultSet.getString("bank_account"));

        return studentInfo;
    }
}
