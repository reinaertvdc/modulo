package be.lambdaware.jdbc;

import be.lambdaware.dao.CompetenceDAO;
import be.lambdaware.entities.CompetenceEntity;
import be.lambdaware.mappers.CompetenceMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by martijn on 08/04/16.
 */
public class CompetenceDAOImpl extends AbstractDAOImpl implements CompetenceDAO {

    @Override
    public int create(CompetenceEntity entity) throws DataAccessException {
        String SQL = "INSERT INTO `competences` (`sub_certificate_category_id`,`name`,`custom_name`,`enabled`) VALUES (?,?,?,?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getSubCertificateCategoryId());
                statement.setString(2, entity.getName());
                statement.setString(3, entity.getCustomName());
                statement.setBoolean(4,entity.getEnabled());
                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public CompetenceEntity get(Integer id) throws DataAccessException {
        String SQL = "SELECT * FROM `competences` WHERE `id` = ?";
        CompetenceEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new CompetenceMapper());
        return entity;
    }

    @Override
    public List<CompetenceEntity> getAll() throws DataAccessException {
        String SQL = "SELECT * FROM `competences`";
        List<CompetenceEntity> entities = jdbcTemplate.query(SQL, new CompetenceMapper());
        return entities;
    }

    @Override
    public List<CompetenceEntity> getBySubCertificateCategory(Integer id) throws DataAccessException {
        String SQL = "SELECT * FROM `competences` WHERE `sub_certificate_category_id` = ?";
        List<CompetenceEntity> entities = jdbcTemplate.query(SQL, new Object[]{id},new CompetenceMapper());
        return entities;
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        String SQL = "DELETE FROM `competences` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
    }

    @Override
    public void update(CompetenceEntity entity) throws DataAccessException {
        String SQL = "UPDATE `competences` SET `sub_certificate_category_id` = ?, `name` = ?,`custom_name` = ?,`enabled` = ? WHERE `id` = ?";
        jdbcTemplate.update(SQL, entity.getSubCertificateCategoryId(),entity.getName(),  entity.getCustomName(), entity.getEnabled(),entity.getId());
    }
}
