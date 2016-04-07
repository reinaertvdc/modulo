package be.lambdaware.dao;

import be.lambdaware.entities.CertificateEntity;
import be.lambdaware.entities.ClassCertificateEntity;
import be.lambdaware.entities.ClassEntity;
import be.lambdaware.entities.GradeEntity;

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
    public void create(ClassCertificateEntity entity);

    /**
     * Get an entity, based on it's id.
     *
     * @param id the entity's id.
     * @return the entity.
     */
    public List<ClassCertificateEntity> getByClass(ClassEntity classEntity);

    /**
     * Get an entity, based on it's id.
     *
     * @param id the entity's id.
     * @return the entity.
     */
    public List<ClassCertificateEntity> getByCertificate(CertificateEntity certificateEntity);

    /**
     * Get an entity, based on it's id.
     *
     * @param id the entity's id.
     * @return the entity.
     */
    public List<ClassCertificateEntity> get(ClassEntity classEntity, CertificateEntity certificateEntity);

    /**
     * Get a list of all the entities.
     *
     * @return a list containing all entities.
     */
    public List<ClassCertificateEntity> getAll();

    /**
     * Delete a certain entity.
     *
     * @param id the entity's id
     */
    public void delete(ClassEntity classEntity, CertificateEntity certificateEntity);
}
