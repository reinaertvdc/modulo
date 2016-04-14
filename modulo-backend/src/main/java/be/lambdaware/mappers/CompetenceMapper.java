package be.lambdaware.mappers;

import be.lambdaware.entities.CompetenceEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by martijn on 08/04/16.
 */
public class CompetenceMapper implements RowMapper<CompetenceEntity> {

    @Override
    public CompetenceEntity mapRow(ResultSet resultSet, int row) throws SQLException {
        CompetenceEntity competence = new CompetenceEntity();
        competence.setId(resultSet.getInt("id"));
        competence.setSubCertificateCategoryId(resultSet.getInt("sub_certificate_category_id"));
        competence.setName(resultSet.getString("name"));
        competence.setCustomName(resultSet.getString("custom_name"));
        competence.setEnabled(resultSet.getBoolean("enabled"));
        return competence;
    }
}
