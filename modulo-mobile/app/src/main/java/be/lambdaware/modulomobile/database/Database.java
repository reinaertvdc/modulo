package be.lambdaware.modulomobile.database;

import android.util.Log;

import java.util.ArrayList;

import be.lambdaware.modulomobile.models.User;

/**
 * Created by Hendrik on 21/05/2016.
 */
public class Database {

    private static ArrayList<User> children = new ArrayList<>();
    private static int selectedIndex;
    private static User selectedUser;

    public static User getSelectedUser() {
        return selectedUser;
    }

    public static void setSelectedUser(int index) {
        //disable old user
        if(selectedUser!=null)
            selectedUser.setSelected(false);
        // set new user
        selectedIndex = index;
        selectedUser = children.get(index);
        selectedUser.setSelected(true);
        Log.i("Database", "Currently selected user: " +selectedUser.toString());
    }

    public static void setSelectedUser(User user) {
        //disable old user
        if(selectedUser!=null)
            selectedUser.setSelected(false);
        // set new user
        selectedUser = user;
        selectedUser.setSelected(true);
        Log.i("Database", "Currently selected user: " +selectedUser.toString());
    }

    public static void saveChild(User user) {
        Log.i("Database", "Saving " + user.toString());
        children.add(user);
    }

    public static void clearChildren() {
        children.clear();
        ;
    }

    public static int getSizeOfChildren() {
        return children.size();
    }

    public static ArrayList<User> getChildren() {
        return children;
    }
}
