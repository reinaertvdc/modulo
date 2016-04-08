package be.lambdaware.mappers;

import be.lambdaware.entities.SubCertificateCategoryEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by MichielVM on 8/04/2016.
 */
public class SubCertificateCategoryMapper implements RowMapper<SubCertificateCategoryEntity> {


    @Override
    public SubCertificateCategoryEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        SubCertificateCategoryEntity subCertificateCategory = new SubCertificateCategoryEntity();
        subCertificateCategory.setId(resultSet.getInt("id"));
        subCertificateCategory.setSubCertificateId(resultSet.getInt("sub_certificate_id"));
        subCertificateCategory.setName(resultSet.getString("name"));
        subCertificateCategory.setDescription(resultSet.getString("description"));
        subCertificateCategory.setCustomName(resultSet.getString("custom_name"));
        subCertificateCategory.setCustomDescription(resultSet.getString("custom_description"));
        subCertificateCategory.setEnabled(resultSet.getBoolean("enabled"));
        return subCertificateCategory;
    }
}