package be.lambdaware.mappers;

import be.lambdaware.entities.StudentInfoEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vincent on 06/04/16.
 */
public class StudentInfoMapper implements RowMapper<StudentInfoEntity> {

    @Override
    public StudentInfoEntity mapRow(ResultSet resultSet, int row) throws SQLException {
        StudentInfoEntity studentInfoEntity = new StudentInfoEntity();

        studentInfoEntity.setId(resultSet.getInt("student_info.id"));
        studentInfoEntity.setUser(resultSet.getInt("student_info.user_id"));
        studentInfoEntity.setParent(resultSet.getInt("student_info.parent_id"));
        studentInfoEntity.setGradeId(resultSet.getInt("student_info.grade_id"));
        studentInfoEntity.setCertificateId(resultSet.getInt("student_info.certificate_id"));
        studentInfoEntity.setBirthDate(resultSet.getDate("student_info.birthdate"));
        studentInfoEntity.setBirthPlace(resultSet.getString("student_info.birth_place"));
        studentInfoEntity.setNationality(resultSet.getString("student_info.nationality"));
        studentInfoEntity.setNationalId(resultSet.getString("student_info.national_identification_number"));
        studentInfoEntity.setStreet(resultSet.getString("student_info.street"));
        studentInfoEntity.setHouseNumber(resultSet.getString("student_info.house_number"));
        studentInfoEntity.setPostalCode(resultSet.getString("student_info.postal_code"));
        studentInfoEntity.setCity(resultSet.getString("student_info.city"));
        studentInfoEntity.setPhoneParent(resultSet.getString("student_info.phone_parent"));
        studentInfoEntity.setPhoneCell(resultSet.getString("student_info.phone_cell"));
        studentInfoEntity.setBankAccount(resultSet.getString("student_info.bank_account"));

        return studentInfoEntity;
    }
}
