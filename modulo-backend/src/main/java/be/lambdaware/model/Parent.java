package be.lambdaware.model;

import be.lambdaware.entities.ParentInfo;
import be.lambdaware.entities.User;

/**
 * Created by jensv on 06-Apr-16.
 */
public class Parent extends Account{

    private ParentInfo parentInfo;

    public ParentInfo getParentInfo() {
        return parentInfo;
    }
}
