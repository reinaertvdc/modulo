package be.lambdaware.jdbc;

import be.lambdaware.dao.SubCertificateDAO;
import be.lambdaware.entities.SubCertificateEntity;
import be.lambdaware.mappers.SubCertificateMapper;
import org.springframework.dao.DataAccessException;
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
    public int create(SubCertificateEntity entity) throws DataAccessException{
        String SQL = "INSERT INTO `sub_certificates` (`certificate_id`, `name`, `custom_name`,  `enabled`) VALUES (?, ?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getCertificateId());
                statement.setString(2, entity.getName());
                statement.setString(3, entity.getCustomName());
                statement.setBoolean(4, entity.getEnabled());
                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public SubCertificateEntity get(Integer id) throws DataAccessException {
        String SQL = "SELECT * FROM `sub_certificates` WHERE `id` = ?";
        SubCertificateEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new SubCertificateMapper());
        return entity;
    }

    @Override
    public List<SubCertificateEntity> getAll() throws DataAccessException {
        String SQL = "SELECT * FROM `sub_certificates`";
        List<SubCertificateEntity> entities = jdbcTemplate.query(SQL, new SubCertificateMapper());
        return entities;
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        String SQL = "DELETE FROM `sub_certificates` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
    }

    @Override
    public void update(SubCertificateEntity entity) throws DataAccessException {
        String SQL = "UPDATE `sub_certificates` SET `certificate_id` = ?, `name` = ?,  `custom_name` = ?, `enabled` = ? WHERE id = ?";
        jdbcTemplate.update(SQL, entity.getCertificateId(), entity.getName(),  entity.getCustomName(), entity.getEnabled(), entity.getId());
    }

    @Override
    public List<SubCertificateEntity> getAllByCertificate(Integer certificateId) throws DataAccessException {
        String SQL = "SELECT * FROM `sub_certificates` JOIN `certificates` ON `sub_certificates`.`certificate_id` = `certificates`.`id` WHERE `certificate_id` = ?";
        List<SubCertificateEntity> entities = jdbcTemplate.query(SQL, new SubCertificateMapper(), certificateId);
        return entities;
    }
}
