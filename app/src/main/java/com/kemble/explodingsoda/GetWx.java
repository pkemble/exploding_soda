package com.kemble.explodingsoda;

/**
 * Created by pete on 2/13/16.
 */
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class GetWx extends AsyncTask<URL, Void, String> {

    private final String TAG = GetWx.class.getName();

    //TODO add station / type / etc / params
    @Override
    protected String doInBackground(URL... urlStrings) {

        String jsonForecast = null;
        //TODO station is a string list. Add in multiple station support

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            urlConnection = (HttpURLConnection) urlStrings[0].openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream is = urlConnection.getInputStream();
            StringBuffer sb = new StringBuffer();
            if(is == null){
                jsonForecast = null;
            }
            reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                sb.append(line + "\n");
            }

            if (sb.length() == 0) {
                // Stream was empty.  No point in parsing.
                jsonForecast = null;
            }
            jsonForecast = sb.toString();

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            jsonForecast = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            return jsonForecast;
        }
    }
}
