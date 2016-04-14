package be.lambdaware.model;

import be.lambdaware.dao.CompetencesDAO;
import be.lambdaware.dao.SubCertificateCategoryDAO;
import be.lambdaware.entities.CompetencesEntity;
import org.springframework.dao.DataAccessException;

/**
 * Created by MichielVM on 14/04/2016.
 */
public class CompetenceModel {

    private CompetencesEntity competencesEntity;
    private CompetencesDAO competencesDAO;

    public CompetenceModel() {}

    public CompetenceModel(CompetencesDAO competencesDAO) {
        this.competencesDAO = competencesDAO;
    }

    public CompetencesEntity getCompetencesEntity() {
        return competencesEntity;
    }

    public void setCompetencesEntity(CompetencesEntity competencesEntity) {
        this.competencesEntity = competencesEntity;
    }

    public void setCompetencesDAO(CompetencesDAO competencesDAO) {
        this.competencesDAO = competencesDAO;
    }

    @Override
    public String toString() {
        return "Competence{" +
                "competencesEntity=" + competencesEntity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetenceModel that = (CompetenceModel) o;

        return competencesEntity.equals(that.competencesEntity);
    }


    public boolean createInDB() throws DataAccessException {
        int id = competencesDAO.create(competencesEntity);
        competencesEntity.setId(id);
        return true;
    }

    public boolean getFromDB(Integer competenceId) throws DataAccessException {
        competencesEntity = competencesDAO.get(competenceId);
        if (competencesEntity == null)
            return false;
        return true;
    }

    public boolean deleteFromDB() throws DataAccessException {
        competencesDAO.delete(competencesEntity.getId());
        return true;
    }

    public boolean updateInDB() throws DataAccessException {
        competencesDAO.update(competencesEntity);
        return true;
    }


    public SubCertificateCategoryModel getSubCertificateCategory(SubCertificateCategoryDAO subCertificateCategoryDAO) throws DataAccessException {
        SubCertificateCategoryModel subCertificateCategory = new SubCertificateCategoryModel(subCertificateCategoryDAO);
        subCertificateCategory.getFromDB(competencesEntity.getSubCertificateCategoryId());
        return subCertificateCategory;
    }
}
