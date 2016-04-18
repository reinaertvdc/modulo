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


    public void createInDB() throws DataAccessException {
        int id = certificateDAO.create(certificateEntity);
        certificateEntity.setId(id);
    }

    public void getFromDB(Integer certificateId) throws DataAccessException {
        certificateEntity = certificateDAO.get(certificateId);
    }

    public void deleteFromDB() throws DataAccessException {
        certificateDAO.delete(certificateEntity.getId());
    }

    public void updateInDB() throws DataAccessException {
        certificateDAO.update(certificateEntity);
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


    public static ArrayList<CertificateModel> getAll(CertificateDAO certificateDAO) {
        ArrayList<CertificateModel> certificates = new ArrayList<CertificateModel>();

        for(CertificateEntity entity : certificateDAO.getAll()) {
            CertificateModel certificate = new CertificateModel();
            certificate.setCertificateEntity(entity);
            certificates.add(certificate);
        }

        return certificates;
    }
}
