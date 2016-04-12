package be.lambdaware.dao;

import be.lambdaware.entities.ClassCertificateEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.util.List;

/**
 * Created by Vincent on 07/04/16.
 */
public interface ClassCertificateDAO {

    /**
     * Create an entity in the database.
     *
     * @param entity the entity to create.
     * @return the id of the newly created entity.
     */
    public void create(ClassCertificateEntity entity) throws DataAccessException;

    /**
     * Get an entity, based on the classId
     *
     * @param classId the entity's classId.
     * @return a list containing the entities with classId
     */
    public List<ClassCertificateEntity> getByClass(Integer classId) throws DataAccessException;

    /**
     * Get an entity, based on it's certificateId.
     *
     * @param certificateId the entity's certificateId.
     * @return a list containing the entities with certificateId.
     */
    public List<ClassCertificateEntity> getByCertificate(Integer certificateId) throws DataAccessException;

    /**
     * Get an entity, based on it's id.
     *
     * @param classId the class id.
     * @param certificateId the certificate id.
     * @return the entity.
     */
    public ClassCertificateEntity get(Integer classId, Integer certificateId) throws DataAccessException;

    /**
     * Get a list of all the entities.
     *
     * @return a list containing all entities.
     */
    public List<ClassCertificateEntity> getAll() throws DataAccessException;

    /**
     * Delete a certain entity.
     *
     * @param classId the class id.
     * @param certificateId the certificate id.
     */
    public void delete(Integer classId, Integer certificateId) throws DataAccessException;
}
