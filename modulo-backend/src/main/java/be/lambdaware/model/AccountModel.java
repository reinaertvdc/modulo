package be.lambdaware.model;

import be.lambdaware.entities.UserEntity;

/**
 * @author hendrik
 */
public abstract class AccountModel {

    protected UserEntity userEntity;

    public AccountModel() {}

    public AccountModel(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
