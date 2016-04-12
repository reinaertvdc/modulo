package be.lambdaware.model;

import be.lambdaware.dao.UserDAO;
import be.lambdaware.entities.UserEntity;

/**
 * @author hendrik
 */

public abstract class AccountModel {

    protected UserEntity userEntity;
    protected UserDAO userDAO;

    public AccountModel() {}

    public AccountModel(UserDAO userDAO) { this.userDAO = userDAO; }

    public boolean createInDB() {
        int entityID = userDAO.create(userEntity);
        userEntity.setId(entityID);
        return true;
    }

    public boolean getFromDB(Integer id) {
        userEntity = userDAO.get(id);
        if (userEntity != null)
            return true;
        else
            return false;
    }

    public boolean deleteFromDB() {
        if (userEntity == null)
            return false;
        userDAO.delete(userEntity.getId());
        return true;
    }

    public boolean updateInDB() {
        if (userEntity == null)
            return false;
        userDAO.update(userEntity);
        return true;
    }

    @Override
    public String toString() {
        return "AccountModel{" +
                "userEntity=" + userEntity +
                '}';
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
