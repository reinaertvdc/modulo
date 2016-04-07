package be.lambdaware.mappers;


import be.lambdaware.entities.SubCertificateEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by MichielVM on 7/04/2016.
 */
public class SubCertificateMapper implements RowMapper<SubCertificateEntity> {

    @Override
    public SubCertificateEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        SubCertificateEntity subcertificate = new SubCertificateEntity();
        subcertificate.setId(resultSet.getInt("id"));
        subcertificate.setCertificate_id(resultSet.getInt("certificate_id"));
        subcertificate.setName(resultSet.getString("name"));
        subcertificate.setDescription(resultSet.getString("description"));
        subcertificate.setEnabled(resultSet.getBoolean("enabled"));
        return subcertificate;
    }
}
