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

        studentInfo.setId(resultSet.getInt("student_info.id"));
        studentInfo.setUser_id(resultSet.getInt("student_info.user_id"));
        studentInfo.setParent_id(resultSet.getInt("student_info.parent_id"));
        studentInfo.setFirst_name(resultSet.getString("student_info.first_name"));
        studentInfo.setLast_name(resultSet.getString("student_info.last_name"));
        studentInfo.setBirthdate(resultSet.getDate("student_info.birthdate"));
        studentInfo.setNationality(resultSet.getString("student_info.nationality"));
        studentInfo.setNational_identification_number(resultSet.getString("student_info.national_identification_number"));
        studentInfo.setStreet(resultSet.getString("student_info.street"));
        studentInfo.setHouse_number(resultSet.getString("student_info.house_number"));
        studentInfo.setPostal_code(resultSet.getString("student_info.postal_code"));
        studentInfo.setCity(resultSet.getString("student_info.city"));
        studentInfo.setPhone_parent(resultSet.getString("student_info.phone_parent"));
        studentInfo.setPhone_cell(resultSet.getString("student_info.phone_cell"));
        studentInfo.setBank_account(resultSet.getString("student_info.bank_account"));

        return studentInfo;
    }
}
