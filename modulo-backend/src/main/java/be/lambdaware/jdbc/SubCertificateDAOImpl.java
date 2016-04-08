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
        String SQL = "INSERT INTO `sub_certificates` (`certificate_id`, `name`, `description`, `custom_name`, `custom_description`, `enabled`) VALUES (?, ?, ?, ?, ?, ?)";

        //TODO process result our catch SQL Exception
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getCertificateId());
                statement.setString(2, entity.getName());
                statement.setString(3, entity.getDescription());
                statement.setString(4, entity.getCustomName());
                statement.setString(5, entity.getCustomDescription());
                statement.setBoolean(6, entity.getEnabled());
                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public SubCertificateEntity get(Integer id) {
        String SQL = "SELECT * FROM `sub_certificates` WHERE `id` = ?";
        SubCertificateEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new SubCertificateMapper());
        //TODO catch SQL Exception
        return entity;
    }

    @Override
    public List<SubCertificateEntity> getAll() {
        String SQL = "SELECT * FROM `sub_certificates`";
        List<SubCertificateEntity> entities = jdbcTemplate.query(SQL, new SubCertificateMapper());
        //TODO catch SQL Exception
        return entities;
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM `sub_certificates` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
        //TODO catch SQL Exception
    }

    @Override
    public void update(SubCertificateEntity entity) {
        String SQL = "UPDATE `sub_certificates` SET `certificate_id` = ?, `name` = ?, `description` = ?, `custom_name` = ?, `description` = ?, `enabled` = ? WHERE id = ?";
        jdbcTemplate.update(SQL, entity.getCertificateId(), entity.getName(), entity.getDescription(), entity.getEnabled());
        //TODO catch SQL Exception
    }

    @Override
    public List<SubCertificateEntity> getAllByCertificate(Integer certificateId) {
        String SQL = "SELECT * FROM `sub_certificates` JOIN `certificates` ON `sub_certificates`.`certificate_id` = `certificates`.`id` WHERE `certificate_id` = ?";
        List<SubCertificateEntity> entities = jdbcTemplate.query(SQL, new SubCertificateMapper(), certificateId);
        //TODO catch SQL Exception
        return entities;
    }
}
