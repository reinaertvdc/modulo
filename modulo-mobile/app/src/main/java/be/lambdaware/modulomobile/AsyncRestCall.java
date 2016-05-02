package be.lambdaware.modulomobile;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hendrik on 14/04/16.
 */
public class AsyncRestCall extends AsyncTask<String,Void,String> {

    private HttpURLConnection httpConnection;

    private TextView textViewName;
    private TextView textViewMail;

    public AsyncRestCall(TextView textViewName, TextView textViewMail) {
        this.textViewName = textViewName;
        this.textViewMail = textViewMail;
    }

    @Override
    protected String doInBackground(String... params) {

        // params.
        String webAddress = "http://10.0.2.2:8080/mobile";

        StringBuilder webResponse = new StringBuilder();

        try {
            URL url = new URL(webAddress);
            httpConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(httpConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                webResponse.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpConnection.disconnect();
        }

        return webResponse.toString();
    }

    @Override
    protected void onPostExecute(String jsonString) {
        Log.i("AsyncRestCall",jsonString);
        try {
            JSONObject json = new JSONObject(jsonString);
            JSONObject userEntity = json.getJSONObject("userEntity");
            String name = userEntity.getString("firstName");
            name +=" " + userEntity.getString("lastName");
            String mail = userEntity.getString("email");
            textViewName.setText(name);
            textViewMail.setText(mail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
