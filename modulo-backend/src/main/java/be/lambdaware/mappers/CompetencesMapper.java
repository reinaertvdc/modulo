package be.lambdaware.mappers;

import be.lambdaware.entities.CompetencesEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by martijn on 08/04/16.
 */
public class CompetencesMapper implements RowMapper<CompetencesEntity> {

    @Override
    public CompetencesEntity mapRow(ResultSet resultSet, int row) throws SQLException {
        CompetencesEntity competence = new CompetencesEntity();
        competence.setId(resultSet.getInt("id"));
        competence.setSubCertificateCategoryId(resultSet.getInt("sub_certificate_category_id"));
        competence.setName(resultSet.getString("name"));
        competence.setDescription(resultSet.getString("description"));
        competence.setCustomName(resultSet.getString("custom_name"));
        competence.setCustomDescription(resultSet.getString("custom_description"));
        competence.setEnabled(resultSet.getBoolean("enabled"));
        return competence;
    }
}
