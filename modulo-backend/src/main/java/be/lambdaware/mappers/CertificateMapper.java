package be.lambdaware.mappers;

import be.lambdaware.entities.CertificateEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by martijn on 07/04/16.
 */
public class CertificateMapper implements RowMapper<CertificateEntity> {

    @Override
    public CertificateEntity mapRow(ResultSet resultSet, int row) throws SQLException {
        CertificateEntity certificate = new CertificateEntity();
        certificate.setId(resultSet.getInt("id"));
        certificate.setName(resultSet.getString("name"));
        return certificate;
    }
}
