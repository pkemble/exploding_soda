package com.kemble.explodingsoda;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by pete on 2/14/16.
 */
public class GetStation extends AsyncTask<URL, Void, Document> {

    private final String TAG = GetWx.class.getName();

    //TODO add station / type / etc / params
    @Override
    protected Document doInBackground(URL... urlStrings) {

        Document doc = null;
        //TODO station is a string list. Add in multiple station support
        URL url;

        HttpURLConnection huc;

        try {
            //Log.d(TAG, "Opening connection to " + url);
            huc = (HttpURLConnection) urlStrings[0].openConnection();
            huc.setConnectTimeout(5000);
            InputStream is = huc.getInputStream();
            doc = parseXML(is);
            //Log.d(TAG, "Done parsing stream from" + url);
            huc.disconnect();
            //Log.d(TAG, "Disconnected from " + url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    private Document parseXML(InputStream inputStream) throws Exception
    {
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        try
        {
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

            doc = objDocumentBuilder.parse(inputStream);
        }
        catch(Exception ex)
        {
            throw ex;
        }

        return doc;
    }

}
