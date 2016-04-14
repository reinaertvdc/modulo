package be.lambdaware.model;

import be.lambdaware.dao.CompetenceDAO;
import be.lambdaware.dao.SubCertificateCategoryDAO;
import be.lambdaware.entities.CompetenceEntity;
import org.springframework.dao.DataAccessException;

/**
 * Created by MichielVM on 14/04/2016.
 */
public class CompetenceModel {

    private CompetenceEntity competenceEntity;
    private CompetenceDAO competenceDAO;

    public CompetenceModel() {}

    public CompetenceModel(CompetenceDAO competenceDAO) {
        this.competenceDAO = competenceDAO;
    }

    public CompetenceEntity getCompetenceEntity() {
        return competenceEntity;
    }

    public void setCompetenceEntity(CompetenceEntity competenceEntity) {
        this.competenceEntity = competenceEntity;
    }

    public void setCompetenceDAO(CompetenceDAO competenceDAO) {
        this.competenceDAO = competenceDAO;
    }

    @Override
    public String toString() {
        return "Competence{" +
                "competenceEntity=" + competenceEntity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetenceModel that = (CompetenceModel) o;

        return competenceEntity.equals(that.competenceEntity);
    }


    public boolean createInDB() throws DataAccessException {
        int id = competenceDAO.create(competenceEntity);
        competenceEntity.setId(id);
        return true;
    }

    public boolean getFromDB(Integer competenceId) throws DataAccessException {
        competenceEntity = competenceDAO.get(competenceId);
        if (competenceEntity == null)
            return false;
        return true;
    }

    public boolean deleteFromDB() throws DataAccessException {
        competenceDAO.delete(competenceEntity.getId());
        return true;
    }

    public boolean updateInDB() throws DataAccessException {
        competenceDAO.update(competenceEntity);
        return true;
    }


    public SubCertificateCategoryModel getSubCertificateCategory(SubCertificateCategoryDAO subCertificateCategoryDAO) throws DataAccessException {
        SubCertificateCategoryModel subCertificateCategory = new SubCertificateCategoryModel(subCertificateCategoryDAO);
        subCertificateCategory.getFromDB(competenceEntity.getSubCertificateCategoryId());
        return subCertificateCategory;
    }
}
