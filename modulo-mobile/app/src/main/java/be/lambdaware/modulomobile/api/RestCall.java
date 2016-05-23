package be.lambdaware.modulomobile.api;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Author: Hendrik Lievens
 * Date: 13/05/2016
 * UHasselt / Software Engineering / 2015 - 2016
 */
public class RestCall extends AsyncTask<String, Void, String> {

    /**
     * When this call is finished, callback.onSuccess will be called. Mostly, an activity or fragment
     * will be set as callback so the View's can be manipulated with the new data.
     */
    private RestCallback callback;
    private HttpURLConnection httpConnection;

    public RestCall(RestCallback callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {

        String webAddress = params[0];
        StringBuilder webResponse = new StringBuilder();
        Log.i("REST", "Executing request to : " + webAddress);

        try {
            // Connect to backend and set required header
            URL url = new URL(webAddress);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestProperty("X-auth", ApiAuthentication.getAuthenticationHeader());

            // Read response
            InputStream in = new BufferedInputStream(httpConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                webResponse.append(line);
            }
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            httpConnection.disconnect();
        }
        return webResponse.toString();
    }

    @Override
    /**
     * Tries to parse the response to validate if we got a valid JSON response. If this is the case,
     * the response will be passed to the callback's onSucces() function.
     */
    protected void onPostExecute(String response) {
        try {
            Log.i("RestCall", "Processing : " + response);
            callback.onSuccess(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
