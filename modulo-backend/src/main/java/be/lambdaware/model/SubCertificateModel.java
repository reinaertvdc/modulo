package be.lambdaware.model;

import be.lambdaware.dao.CertificateDAO;
import be.lambdaware.dao.SubCertificateCategoryDAO;
import be.lambdaware.dao.SubCertificateDAO;
import be.lambdaware.entities.SubCertificateCategoryEntity;
import be.lambdaware.entities.SubCertificateEntity;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;

/**
 * Created by MichielVM on 14/04/2016.
 */
public class SubCertificateModel {

    private SubCertificateEntity subCertificateEntity;
    private SubCertificateDAO subCertificateDAO;

    public SubCertificateModel() {}

    public SubCertificateModel(SubCertificateDAO subCertificateDAO) {
        this.subCertificateDAO = subCertificateDAO;
    }

    public SubCertificateEntity getSubCertificateEntity() {
        return subCertificateEntity;
    }

    public void setSubCertificateEntity(SubCertificateEntity subCertificateEntity) {
        this.subCertificateEntity = subCertificateEntity;
    }

    public void setSubCertificateDAO(SubCertificateDAO subCertificateDAO) {
        this.subCertificateDAO = subCertificateDAO;
    }

    @Override
    public String toString() {
        return "SubCertificateModel{" +
                "subCertificateEntity=" + subCertificateEntity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubCertificateModel that = (SubCertificateModel) o;

        return subCertificateEntity.equals(that.subCertificateEntity);
    }


    public void createInDB() throws DataAccessException {
        int id = subCertificateDAO.create(subCertificateEntity);
        subCertificateEntity.setId(id);
    }

    public void getFromDB(Integer subCertificateId) throws DataAccessException {
        subCertificateEntity = subCertificateDAO.get(subCertificateId);
    }

    public void deleteFromDB() throws DataAccessException {
        subCertificateDAO.delete(subCertificateEntity.getId());
    }

    public void updateInDB() throws DataAccessException {
        subCertificateDAO.update(subCertificateEntity);
    }


    public ArrayList<SubCertificateCategoryModel> getSubCertificateCategories(SubCertificateCategoryDAO subCertificateCategoryDAO) throws DataAccessException {
        ArrayList<SubCertificateCategoryModel> categories = new ArrayList<SubCertificateCategoryModel>();

        for(SubCertificateCategoryEntity entity : subCertificateCategoryDAO.getAllBySubCertificate(subCertificateEntity.getId())) {
            SubCertificateCategoryModel subCertificateCategory = new SubCertificateCategoryModel(subCertificateCategoryDAO);
            subCertificateCategory.getFromDB(entity.getId());
            categories.add(subCertificateCategory);
        }

        return categories;
    }


    public CertificateModel getCertificate(CertificateDAO certificateDAO) {
        CertificateModel certificateModel = new CertificateModel(certificateDAO);
        certificateModel.getFromDB(subCertificateEntity.getCertificateId());
        return certificateModel;
    }
}
