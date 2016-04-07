package be.lambdaware.jdbc;

import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.entities.StudentInfo;
import be.lambdaware.mappers.StudentInfoMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by Vincent on 06/04/16.
 */
public class StudentInfoDAOImpl extends AbstractDAOImpl implements StudentInfoDAO{

    @Override
    public int create(StudentInfo entity) {
        String SQL = "INSERT INTO `student_info` (`user_id`, `parent_id`, `first_name`, `last_name`, `birthdate`, `birth_place`, `nationality`, `national_identification_number`, `street`, `house_number`, `postal_code`, `city`, `phone_parent`, `phone_cell`, `bank_account`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        //TODO process result our catch SQL Exception
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getUser_id());
                statement.setInt(2, entity.getParent_id());
                statement.setString(3, entity.getFirst_name());
                statement.setString(4, entity.getLast_name());
                statement.setDate(5, entity.getBirthdate());
                statement.setString(6, entity.getBirth_place());
                statement.setString(7, entity.getNationality());
                statement.setString(8, entity.getNational_identification_number());
                statement.setString(9, entity.getStreet());
                statement.setString(10, entity.getHouse_number());
                statement.setString(11, entity.getPostal_code());
                statement.setString(12, entity.getCity());
                statement.setString(13, entity.getPhone_parent());
                statement.setString(14, entity.getPhone_cell());
                statement.setString(15, entity.getBank_account());

                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public StudentInfo get(Integer id) {
        String SQL = "SELECT * FROM `student_info` WHERE `id` = ?";
        StudentInfo entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new StudentInfoMapper());
        //TODO catch SQL Exception
        return entity;
    }

    @Override
    public List<StudentInfo> getAll() {
        String SQL = "SELECT * FROM `student_info`";
        List<StudentInfo> entities = jdbcTemplate.query(SQL, new StudentInfoMapper());
        //TODO catch SQL Exception
        return entities;
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM `student_info` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
        //TODO catch SQL Exception
    }

    @Override
    public void update(StudentInfo entity) {
        String SQL = "UPDATE `users` SET user_id = ?, parent_id = ?, first_name = ?, last_name = ?, birthdate = ?, birth_place = ?, nationality = ?, national_identification_number = ?, street = ?, house_number = ?, postal_code = ?, city = ?, phone_parent = ?, phone_cell = ?, bank_account = ? where id = ?";
        jdbcTemplate.update(SQL, entity.getUser_id(), entity.getParent_id(), entity.getFirst_name(), entity.getLast_name(), entity.getBirthdate(), entity.getNationality(), entity.getNational_identification_number(), entity.getStreet(), entity.getHouse_number(), entity.getPostal_code(), entity.getCity(), entity.getPhone_parent(), entity.getPhone_cell(), entity.getBank_account());
        //TODO catch SQL Exception
    }
}
