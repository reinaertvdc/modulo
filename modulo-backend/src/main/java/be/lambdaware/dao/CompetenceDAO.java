package be.lambdaware.dao;

import be.lambdaware.entities.CompetenceEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by martijn on 08/04/16.
 */
public interface CompetenceDAO {

    public int create(CompetenceEntity entity) throws DataAccessException;

    public CompetenceEntity get(Integer id) throws DataAccessException;

    public List<CompetenceEntity> getAll() throws DataAccessException;

    public List<CompetenceEntity> getBySubCertificateCategory(Integer id) throws DataAccessException;

    public void delete(Integer id) throws DataAccessException;

    public void update(CompetenceEntity entity) throws DataAccessException;

}
