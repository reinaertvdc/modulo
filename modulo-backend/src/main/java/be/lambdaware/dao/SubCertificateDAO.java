package be.lambdaware.dao;

import be.lambdaware.entities.SubCertificateEntity;

import java.util.List;

/**
 * Created by MichielVM on 7/04/2016.
 */
public interface SubCertificateDAO {
    // define functions that are not within AbstractDAO
    // custom functions e.g.
    // public List<User> getUsersByCertificate();

    /**
     * Create an entity in the database.
     * @param entity the entity to create.
     * @return the id of the newly created entity.
     */
    public int create(SubCertificateEntity entity);

    /**
     * Get an entity, based on it's id.
     * @param id the entity's id.
     * @return the entity.
     */
    public SubCertificateEntity get(Integer id);

    /**
     * Get a list of all the entities.
     * @return a list containing all entities.
     */
    public List<SubCertificateEntity> getAll();

    /**
     * Delete a certain entity.
     * @param id the entity's id
     */
    public void delete(Integer id);

    /**
     * Updates an entity/
     * @param entity the entity with the new values.
     */
    public void update(SubCertificateEntity entity);
}
