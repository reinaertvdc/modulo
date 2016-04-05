package be.lambdaware.dao;

import be.lambdaware.entities.User;

/**
 * @author hendrik
 */
public interface UserDAO extends AbstractDAO<User> {

    // define functions that are not within AbstractDAO
    // custom functions e.g.
    // public List<User> getUsersByCertificate();

}
