package com.kemble.explodingsoda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Pete on 3/11/2015.
 */

public class GetMap extends AsyncTask<URL, Void, Bitmap> {

    private final String TAG = GetMap.class.getName();

    @Override
    protected Bitmap doInBackground(URL... mapUrl) {
        Bitmap bmp = null;
        //TODO station is a string list. Add in multiple station support

        HttpURLConnection huc;
        try {
            Log.d(TAG, "Opening connection to " + mapUrl);
            huc = (HttpURLConnection) mapUrl[0].openConnection();
            Log.d(TAG, "Getting input stream from " + mapUrl);
            InputStream is = huc.getInputStream();
            Log.d(TAG, "Input stream received from " + mapUrl);
            bmp = BitmapFactory.decodeStream(is);
            //Log.d(TAG, "Done parsing stream from" + url);
            huc.disconnect();
            //Log.d(TAG, "Disconnected from " + url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }
}
