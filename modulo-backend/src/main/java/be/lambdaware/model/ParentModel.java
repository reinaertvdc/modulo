package be.lambdaware.model;

import be.lambdaware.dao.UserDAO;

/**
 * @author hendrik
 */
public class ParentModel extends AccountModel {

    public ParentModel(UserDAO userDAO) {
        super(userDAO);
    }
}
