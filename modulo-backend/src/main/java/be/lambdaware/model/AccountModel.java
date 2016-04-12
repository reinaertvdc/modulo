package be.lambdaware.model;

import be.lambdaware.dao.UserDAO;
import be.lambdaware.entities.UserEntity;
import be.lambdaware.jdbc.UserDAOImpl;
import org.springframework.stereotype.Component;

/**
 * @author hendrik
 */
public  class AccountModel {

    protected UserEntity userEntity;
    protected UserDAO userDAO = new UserDAOImpl();

//    public AccountModel() {
//        userDAO = new UserDAOImpl();
//    }

//    public AccountModel(UserEntity userEntity) { this.userEntity = userEntity; }

    public boolean createInDB() {
        userDAO.create(userEntity);
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
    //    public UserEntity getUserEntity() {
//        return userEntity;
//    }

//    public void setUserEntity(UserEntity userEntity) {
//        this.userEntity = userEntity;
//    }
}
