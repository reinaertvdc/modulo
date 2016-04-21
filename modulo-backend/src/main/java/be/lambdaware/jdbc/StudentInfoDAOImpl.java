package be.lambdaware.jdbc;

import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.entities.StudentInfoEntity;
import be.lambdaware.mappers.StudentInfoMapper;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.*;
import java.util.List;

/**
 * Created by Vincent on 06/04/16.
 */
public class StudentInfoDAOImpl extends AbstractDAOImpl implements StudentInfoDAO {

    @Override
    public int create(StudentInfoEntity entity) throws DataAccessException {
        String SQL = "INSERT INTO `student_info` (`user_id`, `parent_id`,`grade_id`,`certificate_id`, `birthdate`, `birth_place`, `nationality`, `national_identification_number`, `street`, `house_number`, `postal_code`, `city`, `phone_parent`, `phone_cell`, `bank_account`) VALUES (?, ?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getUser());
                if (entity.getParent() == null)
                    statement.setNull(2, Types.INTEGER);
                else
                    statement.setInt(2, entity.getParent());
                statement.setInt(3, entity.getGradeId());
                statement.setInt(4, entity.getCertificateId());
                statement.setDate(5, entity.getBirthDate());
                statement.setString(6, entity.getBirthPlace());
                statement.setString(7, entity.getNationality());
                statement.setString(8, entity.getNationalId());
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
    public StudentInfoEntity get(Integer id) throws DataAccessException {
        String SQL = "SELECT * FROM `student_info` WHERE `id` = ?";
        Logger.getRootLogger().info("Performing query: " + SQL + " with ? = " + id);
        StudentInfoEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new StudentInfoMapper());
        return entity;
    }

    @Override
    public StudentInfoEntity getByUserId(Integer id) throws DataAccessException {
        String SQL = "SELECT * FROM `student_info` WHERE `user_id` = ?";
        Logger.getRootLogger().info("Performing query: " + SQL + " with ? = " + id);
        StudentInfoEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new StudentInfoMapper());
        return entity;
    }

    @Override
    public List<StudentInfoEntity> getByParentId(Integer id) throws DataAccessException {
        String SQL = "SELECT * FROM `student_info` WHERE `parent_id` = ?";
        Logger.getRootLogger().info("Performing query: " + SQL + " with ? = " + id);
        List<StudentInfoEntity> entity = jdbcTemplate.query(SQL, new Object[]{id}, new StudentInfoMapper());
        return entity;
    }

    @Override
    public List<StudentInfoEntity> getAll() throws DataAccessException {
        String SQL = "SELECT * FROM `student_info`";
        Logger.getRootLogger().info("Performing query: " + SQL);
        List<StudentInfoEntity> entity = jdbcTemplate.query(SQL, new StudentInfoMapper());
        return entity;
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        String SQL = "DELETE FROM `student_info` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
    }

    @Override
    public void update(StudentInfoEntity entity) throws DataAccessException {
        String SQL = "UPDATE `student_info` SET `user_id` = ?, `parent_id` = ?,`grade_id` = ? , `certificate_id` = ?, `birthdate` = ?, `birth_place` = ?, `nationality` = ?, `national_identification_number` =  ?, `street` = ?, `house_number` =  ?, `postal_code` = ?, `city` = ?, `phone_parent` = ?, `phone_cell` =  ?, `bank_account` = ? WHERE `id` = ?";
        jdbcTemplate.update(SQL, entity.getUser(), entity.getParent(), entity.getGradeId(), entity.getCertificateId(), entity.getBirthDate(), entity.getBirthPlace(), entity.getNationality(), entity.getNationalId(), entity.getStreet(), entity.getHouseNumber(), entity.getPostalCode(), entity.getCity(), entity.getPhoneParent(), entity.getPhoneCell(), entity.getBankAccount(), entity.getId());
    }


}
