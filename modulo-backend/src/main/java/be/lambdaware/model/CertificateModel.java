package be.lambdaware.model;

import be.lambdaware.dao.CertificateDAO;
import be.lambdaware.dao.SubCertificateDAO;
import be.lambdaware.entities.CertificateEntity;
import be.lambdaware.entities.SubCertificateEntity;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;

/**
 * Created by MichielVM on 14/04/2016.
 */
public class CertificateModel {

    private CertificateEntity certificateEntity;
    private CertificateDAO certificateDAO;

    public CertificateModel() {}

    public CertificateModel(CertificateDAO certificateDAO) {
        this.certificateDAO = certificateDAO;
    }

    public CertificateEntity getCertificateEntity() {
        return certificateEntity;
    }

    public void setCertificateEntity(CertificateEntity certificateEntity) {
        this.certificateEntity = certificateEntity;
    }

    public void setCertificateDAO(CertificateDAO certificateDAO) {
        this.certificateDAO = certificateDAO;
    }

    @Override
    public String toString() {
        return "CertificateModel{" +
                "certificateEntity=" + certificateEntity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CertificateModel that = (CertificateModel) o;

        return certificateEntity.equals(that.certificateEntity);

    }


    public boolean createInDB() throws DataAccessException {
        int id = certificateDAO.create(certificateEntity);
        certificateEntity.setId(id);
        return true;
    }

    public boolean getFromDB(Integer certificateId) throws DataAccessException {
        certificateEntity = certificateDAO.get(certificateId);
        if (certificateEntity == null)
            return false;
        return true;
    }

    public boolean deleteFromDB() throws DataAccessException {
        certificateDAO.delete(certificateEntity.getId());
        return true;
    }

    public boolean updateInDB() throws DataAccessException {
        certificateDAO.update(certificateEntity);
        return true;
    }


    public ArrayList<SubCertificateModel> getSubCertificates(SubCertificateDAO subCertificateDAO) {
        ArrayList<SubCertificateModel> subCertificates = new ArrayList<SubCertificateModel>();

        for(SubCertificateEntity entity : subCertificateDAO.getAllByCertificate(certificateEntity.getId())) {
            SubCertificateModel subCertificate = new SubCertificateModel(subCertificateDAO);
            subCertificate.getFromDB(entity.getId());
            subCertificates.add(subCertificate);
        }

        return subCertificates;
    }
}
