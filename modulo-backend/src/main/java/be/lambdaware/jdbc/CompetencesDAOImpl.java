package be.lambdaware.jdbc;

import be.lambdaware.dao.CompetencesDAO;
import be.lambdaware.entities.CompetencesEntity;
import be.lambdaware.mappers.CompetencesMapper;
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
public class CompetencesDAOImpl extends AbstractDAOImpl implements CompetencesDAO {

    @Override
    public int create(CompetencesEntity entity) {
        String SQL = "INSERT INTO `competences` (`sub_certificate_category_id`,`name`,`description`,`custom_name`,`custom_description`,`enabled`) VALUES (?,?,?,?,?,?)";

        //TODO process result our catch SQL Exception
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getSubCertificateCategoryId());
                statement.setString(2, entity.getName());
                statement.setString(3, entity.getDescription());
                statement.setString(4, entity.getCustomName());
                statement.setString(5, entity.getCustomDescription());
                statement.setBoolean(6,entity.getEnabled());
                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public CompetencesEntity get(Integer id) {
        String SQL = "SELECT * FROM `competences` WHERE `id` = ?";
        CompetencesEntity entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new CompetencesMapper());
        //TODO catch SQL Exception
        return entity;
    }

    @Override
    public List<CompetencesEntity> getAll() {
        String SQL = "SELECT * FROM `competences`";
        List<CompetencesEntity> entities = jdbcTemplate.query(SQL, new CompetencesMapper());
        //TODO catch SQL Exception
        return entities;
    }

    @Override
    public List<CompetencesEntity> getBySubCertificateCategory(Integer id) {
        String SQL = "SELECT * FROM `competences` WHERE `sub_certificate_category_id` = ?";
        List<CompetencesEntity> entities = jdbcTemplate.query(SQL, new Object[]{id},new CompetencesMapper());
        //TODO catch SQL Exception
        return entities;
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM `competences` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
        //TODO catch SQL Exception
    }

    @Override
    public void update(CompetencesEntity entity) {
        String SQL = "UPDATE `competences` SET `sub_certificate_category_id` = ?, `name` = ?,`description` = ?,`custom_name` = ?,`custom_description` = ?,`enabled` = ? WHERE `id` = ?";
        jdbcTemplate.update(SQL, entity.getSubCertificateCategoryId(),entity.getName(), entity.getDescription(), entity.getCustomName(),entity.getCustomDescription(), entity.getEnabled(),entity.getId());
        //TODO catch SQL Exception
    }
}
