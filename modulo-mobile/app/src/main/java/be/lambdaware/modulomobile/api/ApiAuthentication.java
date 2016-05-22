package be.lambdaware.modulomobile.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import be.lambdaware.modulomobile.R;
import be.lambdaware.modulomobile.models.User;

/**
 * Author: Hendrik Lievens
 * Date: 13/05/2016
 * UHasselt / Software Engineering / 2015 - 2016
 */
public class ApiAuthentication {

    // Cache results from preferences
    private static String base64Authentication;
    private static User authenticatedUser;

    public static void init(Context context) {
        // Get name of prefrences
        String preferencesName = context.getResources().getString(R.string.preferences_name);
        // Load preferences
        SharedPreferences preferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        base64Authentication = preferences.getString("X-Auth", "empty");
        String jsonUser = preferences.getString("UserObject", "");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        try {
            authenticatedUser = gson.fromJson(jsonUser, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clear(Context context) {
        // Get name of prefrences
        String preferencesName = context.getResources().getString(R.string.preferences_name);
        // Clear preferences
        SharedPreferences preferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("UserObject", "");
        editor.putString("X-Auth", "empty");
        editor.apply();
    }

    public static String getAuthenticationHeader() {
        Log.d("ApiAuthentication", "Retrieving header from preferences: " + base64Authentication);
        return base64Authentication;
    }

    public static User getAuthenticatedUser() {
        return authenticatedUser;
    }
}
