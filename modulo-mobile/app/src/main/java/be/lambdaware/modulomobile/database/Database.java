package be.lambdaware.modulomobile.database;

import java.util.ArrayList;

import be.lambdaware.modulomobile.models.User;

/**
 * Author: Hendrik Lievens
 * Date: 21/05/2016
 * UHasselt / Software Engineering / 2015 - 2016
 */
public class Database {

    private static ArrayList<User> children = new ArrayList<>();
    private static User selectedUser;

    public static User getSelectedUser() {
        return selectedUser;
    }

    /**
     * Sets the selected User, based on an 0-index.
     * @param index the index of the User to select.
     */
    public static void setSelectedUser(int index) {
        //disable old user
        if (selectedUser != null)
            selectedUser.setSelected(false);
        // set new user
        selectedUser = children.get(index);
        selectedUser.setSelected(true);
    }

    /**
     * Sets the selected User
     * @param user The currently selected user.
     */
    public static void setSelectedUser(User user) {
        //disable old user
        if (selectedUser != null)
            selectedUser.setSelected(false);
        // set new user
        selectedUser = user;
        selectedUser.setSelected(true);
    }

    /**
     * Adds a child to the database
     * @param child The child to save to the list.
     */
    public static void saveChild(User child) {
        children.add(child);
    }

    /**
     * Clears the current list of children.
     */
    public static void clearChildren() {
        children.clear();
    }

    /**
     * Returns how many children are currently in the list.
     * @return the amount of children a parent has.
     */
    public static int getSizeOfChildren() {
        return children.size();
    }

    public static ArrayList<User> getChildren() {
        return children;
    }
}
