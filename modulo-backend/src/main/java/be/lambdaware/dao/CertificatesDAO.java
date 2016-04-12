package be.lambdaware.dao;

import be.lambdaware.entities.CertificatesEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by martijn on 07/04/16.
 */
public interface CertificatesDAO {

    /**
     * Create an entity in the database.
     * @param entity the entity to create.
     * @return the id of the newly created entity.
     */
    public int create(CertificatesEntity entity) throws DataAccessException;

    /**
     * Get an entity, based on it's id.
     * @param id the entity's id.
     * @return the entity.
     */
    public CertificatesEntity get(Integer id) throws DataAccessException;

    /**
     * Get a list of all the entities.
     * @return a list containing all entities.
     */
    public List<CertificatesEntity> getAll() throws DataAccessException;

    /**
     * Delete a certain entity.
     * @param id the entity's id
     */
    public void delete(Integer id) throws DataAccessException;

    /**
     * Updates an entity/
     * @param entity the entity with the new values.
     */
    public void update(CertificatesEntity entity) throws DataAccessException;

}
