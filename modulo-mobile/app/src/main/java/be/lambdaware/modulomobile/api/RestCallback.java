package be.lambdaware.modulomobile.api;

import org.json.JSONException;

/**
 * Created by Hendrik on 13/05/2016.
 */
public interface RestCallback {

    void onSuccess(String response) throws JSONException;
}
