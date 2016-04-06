package be.lambdaware.model;

import be.lambdaware.entities.User;

/**
 * Created by Vincent on 06/04/16.
 */
public abstract class Account {
    protected User _user;

    public User getUser() {
        return _user;
    }

    public void setUser(User user) {
        this._user = user;
    }
}
