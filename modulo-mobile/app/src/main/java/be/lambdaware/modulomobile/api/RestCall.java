package be.lambdaware.modulomobile.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Hendrik on 13/05/2016.
 */
public class RestCall extends AsyncTask<String, Void, String> {

    private RestCallback callback;

    private HttpURLConnection httpConnection;

    public RestCall(RestCallback callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {

        String webAddress = params[0];
        StringBuilder webResponse = new StringBuilder();
        Log.i("REST","Executing request to : " + webAddress);

        try {
            URL url = new URL(webAddress);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestProperty("X-auth", ApiAuthentication.getAuthenticationHeader());
            int status = httpConnection.getResponseCode();


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
    protected void onPostExecute(String response) {
        try {
            Log.i("RestCall","Processing : " + response);
            callback.onSuccess(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
