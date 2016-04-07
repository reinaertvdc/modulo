package be.lambdaware.mappers;

import be.lambdaware.entities.ParentInfoEntity;
import be.lambdaware.entities.StudentInfoEntity;
import be.lambdaware.entities.UserEntity;
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

        UserMapper userMapper = new UserMapper();
        UserEntity user = userMapper.mapRow(resultSet,row);
        studentInfoEntity.setUser(user);

        ParentInfoMapper parentInfoMapper = new ParentInfoMapper();
        ParentInfoEntity parentInfoEntity = parentInfoMapper.mapRow(resultSet,row);
        studentInfoEntity.setParent(parentInfoEntity);

        studentInfoEntity.setFirstName(resultSet.getString("student_info.first_name"));
        studentInfoEntity.setLastName(resultSet.getString("student_info.last_name"));
        studentInfoEntity.setBirthDate(resultSet.getDate("student_info.birth_date"));
        studentInfoEntity.setBirthPlace(resultSet.getString("student_info.birth_place"));
        studentInfoEntity.setNationality(resultSet.getString("student_info.nationality"));
        studentInfoEntity.setNationalIdentificationNumber(resultSet.getString("student_info.national_identification_number"));
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
