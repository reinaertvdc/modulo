package be.lambdaware.model;

import be.lambdaware.entities.ParentInfoEntity;
import be.lambdaware.entities.UserEntity;

/**
 * @author hendrik
 */
public class ParentModel extends AccountModel {

    public static ParentModel create(UserEntity userEntity, ParentInfoEntity parentInfoEntity) {
        ParentModel parentModel = new ParentModel();
        parentModel.setUserEntity(userEntity);
        parentModel.setParentInfoEntity(parentInfoEntity);
        return parentModel;
    }

    protected ParentInfoEntity parentInfoEntity;

    public ParentModel() {

    }

    public ParentInfoEntity getParentInfoEntity() {
        return parentInfoEntity;
    }

    public void setParentInfoEntity(ParentInfoEntity parentInfoEntity) {
        this.parentInfoEntity = parentInfoEntity;
    }
}
