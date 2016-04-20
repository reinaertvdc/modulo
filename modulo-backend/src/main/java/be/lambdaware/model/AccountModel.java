package be.lambdaware.model;

import be.lambdaware.dao.UserDAO;
import be.lambdaware.entities.UserEntity;
import org.springframework.dao.DataAccessException;

import javax.xml.crypto.Data;
import java.util.ArrayList;

/**
 * @author hendrik
 */

public class AccountModel {

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

    public void getFromDBByEmail(String email) throws DataAccessException {
        userEntity = userDAO.getByEmail(email);
    }

    public void deleteFromDB() throws DataAccessException {
        userDAO.delete(userEntity.getId());
    }

    public void updateInDB() throws DataAccessException {
        userDAO.update(userEntity);
    }


    public static ArrayList<AccountModel> getAll(UserDAO userDAO) {
        ArrayList<AccountModel> accounts = new ArrayList<AccountModel>();

        for(UserEntity entity : userDAO.getAll()) {
            AccountModel account = new AccountModel();
            account.setUserEntity(entity);
            accounts.add(account);
        }

        return accounts;
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
