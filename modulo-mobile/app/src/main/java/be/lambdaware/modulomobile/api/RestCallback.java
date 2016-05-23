package be.lambdaware.modulomobile.api;

import org.json.JSONException;

/**
 * Author: Hendrik Lievens
 * Date: 13/05/2016
 * UHasselt / Software Engineering / 2015 - 2016
 */
public interface RestCallback {

    /**
     * Called when a REST request is finished and has resulted in a correctly formatted JSON-string.
     *
     * @param response the valid JSON-string.
     * @throws JSONException when the backend didn't reply with a valid JSON-string.
     */
    void onSuccess(String response) throws JSONException;
}
