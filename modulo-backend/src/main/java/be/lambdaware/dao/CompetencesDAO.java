package be.lambdaware.dao;

import be.lambdaware.entities.CompetencesEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by martijn on 08/04/16.
 */
public interface CompetencesDAO {

    public int create(CompetencesEntity entity) throws DataAccessException;

    public CompetencesEntity get(Integer id) throws DataAccessException;

    public List<CompetencesEntity> getAll() throws DataAccessException;

    public List<CompetencesEntity> getBySubCertificateCategory(Integer id) throws DataAccessException;

    public void delete(Integer id) throws DataAccessException;

    public void update(CompetencesEntity entity) throws DataAccessException;

}
