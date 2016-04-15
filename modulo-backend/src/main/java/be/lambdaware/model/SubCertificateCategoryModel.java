package be.lambdaware.model;

import be.lambdaware.dao.CompetenceDAO;
import be.lambdaware.dao.SubCertificateCategoryDAO;
import be.lambdaware.dao.SubCertificateDAO;
import be.lambdaware.entities.CompetenceEntity;
import be.lambdaware.entities.SubCertificateCategoryEntity;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;

/**
 * Created by MichielVM on 14/04/2016.
 */
public class SubCertificateCategoryModel {

    private SubCertificateCategoryEntity subCertificateCategoryEntity;
    private SubCertificateCategoryDAO subCertificateCategoryDAO;

    public SubCertificateCategoryModel() {}

    public SubCertificateCategoryModel(SubCertificateCategoryDAO subCertificateCategoryDAO) {
        this.subCertificateCategoryDAO = subCertificateCategoryDAO;
    }

    public SubCertificateCategoryEntity getSubCertificateCategoryEntity() {
        return subCertificateCategoryEntity;
    }

    public void setSubCertificateCategoryEntity(SubCertificateCategoryEntity subCertificateCategoryEntity) {
        this.subCertificateCategoryEntity = subCertificateCategoryEntity;
    }

    public void setSubCertificateCategoryDAO(SubCertificateCategoryDAO subCertificateCategoryDAO) {
        this.subCertificateCategoryDAO = subCertificateCategoryDAO;
    }

    @Override
    public String toString() {
        return "SubCertificateCategoryModel{" +
                "subCertificateCategoryEntity=" + subCertificateCategoryEntity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubCertificateCategoryModel that = (SubCertificateCategoryModel) o;

        return subCertificateCategoryEntity.equals(that.subCertificateCategoryEntity);
    }


    public void createInDB() throws DataAccessException {
        int id = subCertificateCategoryDAO.create(subCertificateCategoryEntity);
        subCertificateCategoryEntity.setId(id);
    }

    public void getFromDB(Integer subCertificateCategoryId) throws DataAccessException {
        subCertificateCategoryEntity = subCertificateCategoryDAO.get(subCertificateCategoryId);
    }

    public void deleteFromDB() throws DataAccessException {
        subCertificateCategoryDAO.delete(subCertificateCategoryEntity.getId());
    }

    public void updateInDB() throws DataAccessException {
        subCertificateCategoryDAO.update(subCertificateCategoryEntity);
    }


    public ArrayList<CompetenceModel> getCompetences(CompetenceDAO competenceDAO) throws DataAccessException {
        ArrayList<CompetenceModel> competences = new ArrayList<CompetenceModel>();

        for(CompetenceEntity entity : competenceDAO.getBySubCertificateCategory(subCertificateCategoryEntity.getId())) {
            CompetenceModel competence = new CompetenceModel(competenceDAO);
            competence.getFromDB(entity.getId());
            competences.add(competence);
        }

        return competences;
    }


    public SubCertificateModel getSubCertificate(SubCertificateDAO subCertificateDAO) {
        SubCertificateModel subCertificateModel = new SubCertificateModel(subCertificateDAO);
        subCertificateModel.getFromDB(subCertificateCategoryEntity.getSubCertificateId());
        return subCertificateModel;
    }
}
