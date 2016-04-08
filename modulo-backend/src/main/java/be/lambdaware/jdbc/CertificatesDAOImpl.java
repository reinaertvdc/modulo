package be.lambdaware.jdbc;

import be.lambdaware.dao.CertificatesDAO;
import be.lambdaware.entities.CertificatesEntity;
import be.lambdaware.mappers.CertificatesMapper;
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
public class CertificatesDAOImpl extends AbstractDAOImpl implements CertificatesDAO {

    @Override
    public int create(CertificatesEntity entity) {
        String SQL = "INSERT INTO `certificates` (`name`, `enabled`) VALUES (?,?)";

        //TODO process result our catch SQL Exception
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, entity.getName());
                statement.setBoolean(2,entity.getEnabled());
                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public CertificatesEntity get(Integer id) {
        String SQL = "SELECT * FROM `certificates` WHERE `id` = ?";
        CertificatesEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new CertificatesMapper());
        //TODO catch SQL Exception
        return entity;
    }

    @Override
    public List<CertificatesEntity> getAll() {
        String SQL = "SELECT * FROM `certificates`";
        List<CertificatesEntity> entities = jdbcTemplate.query(SQL, new CertificatesMapper());
        //TODO catch SQL Exception
        return entities;
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM `certificates` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
        //TODO catch SQL Exception
    }

    @Override
    public void update(CertificatesEntity entity) {
        String SQL = "UPDATE `certificates` SET `name` = ?, `enabled` =? WHERE `id` = ?";
        jdbcTemplate.update(SQL, entity.getName(), entity.getEnabled(),entity.getId());
        //TODO catch SQL Exception
    }
}
