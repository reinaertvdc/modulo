package be.lambdaware.mappers;

import be.lambdaware.entities.CertificatesEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by martijn on 07/04/16.
 */
public class CertificatesMapper implements RowMapper<CertificatesEntity> {

    @Override
    public CertificatesEntity mapRow(ResultSet resultSet, int row) throws SQLException {
        CertificatesEntity certificate = new CertificatesEntity();
        certificate.setId(resultSet.getInt("id"));
        certificate.setName(resultSet.getString("name"));
        certificate.setEnabled(resultSet.getBoolean("enabled"));
        return certificate;
    }
}
