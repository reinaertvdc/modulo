package be.lambdaware.mappers;

import be.lambdaware.entities.CertificateEntity;
import be.lambdaware.entities.ClassCertificateEntity;
import be.lambdaware.entities.ClassEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vincent on 07/04/16.
 */
public class ClassCertificateMapper implements RowMapper<ClassCertificateEntity>{

    private ClassesMapper classesMapper;
    private CertificateMapper certificateMapper;

    public ClassCertificateMapper() {
        classesMapper = new ClassesMapper();
        certificateMapper = new CertificateMapper();
    }


    @Override
    public ClassCertificateEntity mapRow(ResultSet resultSet, int row) throws SQLException {

        ClassCertificateEntity classCertificateEntity = new ClassCertificateEntity();

        ClassEntity classEntity = this.classesMapper.mapRow(resultSet, row);
        CertificateEntity certificateEntity = this.certificateMapper.mapRow(resultSet, row);

        classCertificateEntity.setClassEntity(classEntity);
        classCertificateEntity.setCertificateEntity(certificateEntity);

        return classCertificateEntity;
    }
}
