package be.lambdaware.mappers;

import be.lambdaware.entities.ClassCertificateEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vincent on 07/04/16.
 */
public class ClassCertificateMapper implements RowMapper<ClassCertificateEntity>{

    @Override
    public ClassCertificateEntity mapRow(ResultSet resultSet, int row) throws SQLException {

        ClassCertificateEntity classCertificateEntity = new ClassCertificateEntity();

        classCertificateEntity.setClassId(resultSet.getInt("class_id"));
        classCertificateEntity.setCertificateId(resultSet.getInt("certificate_id"));

        return classCertificateEntity;
    }
}
