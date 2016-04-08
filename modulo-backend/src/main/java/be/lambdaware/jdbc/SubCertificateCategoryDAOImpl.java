package be.lambdaware.jdbc;

import be.lambdaware.dao.SubCertificateCategoryDAO;
import be.lambdaware.entities.SubCertificateCategorieEntity;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by MichielVM on 8/04/2016.
 */
public class SubCertificateCategoryDAOImpl extends AbstractDAOImpl implements SubCertificateCategoryDAO {
    @Override
    public int create(SubCertificateCategorieEntity entity) {
        String SQL = "INSERT INTO `sub_certificate_categories` (`sub_certificate_id`, `name`, `description`, `custom_name`, `custom_description`, `enabled`) VALUES (?, ?, ?, ?, ?, ?)";

        //TODO process result our catch SQL Exception
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getSubCertificateId());
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
    public SubCertificateCategorieEntity get(Integer id) {
        String SQL = "SELECT * FROM `sub_certificate_categories` WHERE `id` = ?";
        SubCertificateEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new SubCertificateMapper());
        //TODO catch SQL Exception
        return entity;
    }

    @Override
    public List<SubCertificateCategorieEntity> getAll() {
        String SQL = "SELECT * FROM `sub_certificate_categories`";
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
    public void update(SubCertificateCategorieEntity entity) {
        String SQL = "UPDATE `sub_certificates` SET `certificate_id` = ?, `name` = ?, `description` = ?, `custom_name` = ?, `description` = ?, `enabled` = ? WHERE id = ?";
        jdbcTemplate.update(SQL, entity.getCertificateId(), entity.getName(), entity.getDescription(), entity.getEnabled());
        //TODO catch SQL Exception
    }

    @Override
    public List<SubCertificateCategorieEntity> getAllBySubCertificate(Integer subCertificateId) {
        String SQL = "SELECT * FROM `sub_certificate_categories` JOIN `sub_certificates` ON `sub_certificates`.`certificate_id` = `certificates`.`id` WHERE `certificate_id` = ?";
        List<SubCertificateEntity> entities = jdbcTemplate.query(SQL, new SubCertificateMapper(), certificateId);
        //TODO catch SQL Exception
        return entities;
    }
}
