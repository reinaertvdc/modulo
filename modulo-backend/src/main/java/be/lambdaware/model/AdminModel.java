package be.lambdaware.model;

import be.lambdaware.dao.UserDAO;

/**
 * Created by MichielVM on 20/04/2016.
 */
public class AdminModel extends AccountModel {

    public AdminModel() {}

    public AdminModel(UserDAO userDAO) {
        super(userDAO);
    }
}
