package be.lambdaware.dao;

import be.lambdaware.entities.CompetencesEntity;

import java.util.List;

/**
 * Created by martijn on 08/04/16.
 */
public interface CompetencesDAO {

    public int create(CompetencesEntity entity);

    public CompetencesEntity get(Integer id);

    public List<CompetencesEntity> getAll();

    public List<CompetencesEntity> getBySubCertificateCategory(Integer id);

    public void delete(Integer id);

    public void update(CompetencesEntity entity);

}
