package be.lambdaware.jdbc;

import be.lambdaware.dao.CertificateDAO;
import be.lambdaware.entities.CertificateEntity;
import be.lambdaware.mappers.CertificateMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by martijn on 07/04/16.
 */
public class CertificateDAOImpl extends AbstractDAOImpl implements CertificateDAO {

    @Override
    public int create(CertificateEntity entity) {
        String SQL = "INSERT INTO `certificate` (`name`) VALUES (?)";

        //TODO process result our catch SQL Exception
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, entity.getName());
                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public CertificateEntity get(Integer id) {
        String SQL = "SELECT * FROM `certificate` WHERE `id` = ?";
        CertificateEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new CertificateMapper());
        //TODO catch SQL Exception
        return entity;
    }

    @Override
    public List<CertificateEntity> getAll() {
        String SQL = "SELECT * FROM `certificate`";
        List<CertificateEntity> entities = jdbcTemplate.query(SQL, new CertificateMapper());
        //TODO catch SQL Exception
        return entities;
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM `certificate` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
        //TODO catch SQL Exception
    }

    @Override
    public void update(CertificateEntity entity) {
        String SQL = "UPDATE `certificate` SET name = ? where id = ?";
        jdbcTemplate.update(SQL, entity.getName(),entity.getId());
        //TODO catch SQL Exception
    }
}
