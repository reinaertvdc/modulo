package be.lambdaware.model;

import be.lambdaware.dao.UserDAO;
import be.lambdaware.entities.UserEntity;
import org.springframework.dao.DataAccessException;

/**
 * @author hendrik
 */

public abstract class AccountModel {

    protected UserEntity userEntity;
    protected UserDAO userDAO;

    public AccountModel() {}

    public AccountModel(UserDAO userDAO) { this.userDAO = userDAO; }

    public void createInDB() throws DataAccessException {
        int entityID = userDAO.create(userEntity);
        userEntity.setId(entityID);
    }

    public void getFromDB(Integer id) throws DataAccessException {
        userEntity = userDAO.get(id);
    }

    public void deleteFromDB() throws DataAccessException {
        userDAO.delete(userEntity.getId());
    }

    public void updateInDB() throws DataAccessException {
        userDAO.update(userEntity);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountModel that = (AccountModel) o;

        return userEntity.equals(that.userEntity);

    }


}
