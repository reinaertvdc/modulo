package be.lambdaware.dao;

import be.lambdaware.entities.SubCertificateCategorieEntity;

import java.util.List;

/**
 * Created by MichielVM on 8/04/2016.
 */
public interface SubCertificateCategoryDAO {

    /**
     * Create an entity in the database.
     * @param entity the entity to create.
     * @return the id of the newly created entity.
     */
    public int create(SubCertificateCategorieEntity entity);

    /**
     * Get an entity, based on it's id.
     * @param id the entity's id.
     * @return the entity.
     */
    public SubCertificateCategorieEntity get(Integer id);

    /**
     * Get a list of all the entities.
     * @return a list containing all entities.
     */
    public List<SubCertificateCategorieEntity> getAll();

    /**
     * Delete a certain entity.
     * @param id the entity's id
     */
    public void delete(Integer id);

    /**
     * Updates an entity/
     * @param entity the entity with the new values.
     */
    public void update(SubCertificateCategorieEntity entity);

    /**
     * Get a list of all the subcertificatecategories for a given subcertificate.
     * @param subCertificateId the id of the certificate to look for.
     */
    public List<SubCertificateCategorieEntity> getAllBySubCertificate(Integer subCertificateId);
}
