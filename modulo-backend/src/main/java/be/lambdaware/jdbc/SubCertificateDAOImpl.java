package be.lambdaware.jdbc;

import be.lambdaware.dao.SubCertificateDAO;
import be.lambdaware.entities.SubCertificateEntity;
import be.lambdaware.mappers.SubCertificateMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by MichielVM on 7/04/2016.
 */
public class SubCertificateDAOImpl extends AbstractDAOImpl implements SubCertificateDAO {
    @Override
    public int create(SubCertificateEntity entity) {
        String SQL = "INSERT INTO `sub_certificate` (`certificate_id`, `name`, `description`, `enabled`) VALUES (?, ?, ?, ?)";

        //TODO process result our catch SQL Exception
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getCertificate_id());
                statement.setString(2, entity.getName());
                statement.setString(3, entity.getDescription());
                statement.setBoolean(4, entity.getEnabled());
                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public SubCertificateEntity get(Integer id) {
        String SQL = "SELECT * FROM `sub_certificate` WHERE `id` = ?";
        SubCertificateEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new SubCertificateMapper());
        //TODO catch SQL Exception
        return entity;
    }

    @Override
    public List<SubCertificateEntity> getAll() {
        String SQL = "SELECT * FROM `sub_certificate`";
        List<SubCertificateEntity> entities = jdbcTemplate.query(SQL, new SubCertificateMapper());
        //TODO catch SQL Exception
        return entities;
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM `sub_certificate` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
        //TODO catch SQL Exception
    }

    @Override
    public void update(SubCertificateEntity entity) {
        String SQL = "UPDATE `sub_certificate` SET `certificate_id` = ?, `name` = ?, `description` = ?, `enabled` = ? where id = ?";
        jdbcTemplate.update(SQL, entity.getCertificate_id(), entity.getName(), entity.getDescription(), entity.getEnabled());
        //TODO catch SQL Exception
    }
}
