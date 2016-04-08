package be.lambdaware.jdbc;

import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.entities.StudentInfoEntity;
import be.lambdaware.mappers.StudentInfoMapper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Vincent on 06/04/16.
 */
public class StudentInfoDAOImpl extends AbstractDAOImpl implements StudentInfoDAO{

    @Override
    public int create(StudentInfoEntity entity) {
        String SQL = "INSERT INTO `student_info` (`user_id`, `parent_id`, `first_name`, `last_name`, `birthdate`, `birth_place`, `nationality`, `national_identification_number`, `street`, `house_number`, `postal_code`, `city`, `phone_parent`, `phone_cell`, `bank_account`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        //TODO process result our catch SQL Exception
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getUser());
                statement.setInt(2, entity.getParent());
                statement.setString(3, entity.getFirstName());
                statement.setString(4, entity.getLastName());
                statement.setDate(5, entity.getBirthDate());
                statement.setString(6, entity.getBirthPlace());
                statement.setString(7, entity.getNationality());
                statement.setString(8, entity.getNationalIdentificationNumber());
                statement.setString(9, entity.getStreet());
                statement.setString(10, entity.getHouseNumber());
                statement.setString(11, entity.getPostalCode());
                statement.setString(12, entity.getCity());
                statement.setString(13, entity.getPhoneParent());
                statement.setString(14, entity.getPhoneCell());
                statement.setString(15, entity.getBankAccount());

                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public StudentInfoEntity getByUserId(Integer id) {
        String SQL = "SELECT * FROM `student_info` WHERE `student_info`.`user_id` = ?";
        Logger.getRootLogger().info("Performing query: "+SQL+" with ? = " + id);
        StudentInfoEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new StudentInfoMapper());
        //TODO catch SQL Exception
        return entity;
    }

    @Override
    public StudentInfoEntity getById(Integer id) {
        String SQL = "SELECT * FROM `student_info` WHERE `student_info`.`id` = ?";
        Logger.getRootLogger().info("Performing query: "+SQL+" with ? = " + id);
        StudentInfoEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new StudentInfoMapper());
        //TODO catch SQL Exception
        return entity;
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM `student_info` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
    }

    @Override
    public void update(StudentInfoEntity entity) {
        String SQL = "UPDATE `student_info` SET `user_id` = ?, `parent_id` = ?, `first_name` = ?, `last_name` =  ?, `birthdate` = ?, `birth_place` = ?, `nationality` = ?, `national_identification_number` =  ?, `street` = ?, `house_number` =  ?, `postal_code` = ?, `city` = ?, `phone_parent` = ?, `phone_cell` =  ?, `bank_account` = ? WHERE `id` = ?";
        jdbcTemplate.update(SQL, entity.getUser(), entity.getParent(), entity.getFirstName(), entity.getLastName(), entity.getBirthDate(),entity.getBirthPlace(), entity.getNationality(),entity.getNationalIdentificationNumber(), entity.getStreet(), entity.getHouseNumber(), entity.getPostalCode(), entity.getCity(), entity.getPhoneParent(), entity.getPhoneCell(), entity.getBankAccount(), entity.getId());
        //TODO catch SQL Exception
    }


}
