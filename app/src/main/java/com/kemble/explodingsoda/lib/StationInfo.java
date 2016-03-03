package com.kemble.explodingsoda.lib;

/**
 * Created by pete on 2/13/16.
 */
import android.app.Activity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.util.Xml;

import com.kemble.explodingsoda.GetStation;
import com.kemble.explodingsoda.GetWx;
import com.kemble.explodingsoda.R;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class StationInfo {
    public String SiteName;
    public String SiteId;
    public String Latitude;
    public String Longitude;

//
//    public StationInfo(String stationId){
//
//        SiteId = stationId.toUpperCase();
//
//
//        String dataSourceParam = "stations";
//        String requestTypeParam = "retrieve";
//        String formatParam = "xml";
//
//        Document doc;
//        try {
//
//            final String DATA_SOURCE_PARAM = "dataSource";
//            final String REQUEST_TYPE_PARAM = "requestType";
//            final String FORMAT_PARAM = "format";
//            final String STATION_STRING_PARAM = "stationString";
//
//
//
//            Uri builtUrl = Uri.parse(mBaseUrl).buildUpon()
//                    .appendQueryParameter(DATA_SOURCE_PARAM, dataSourceParam)
//                    .appendQueryParameter(REQUEST_TYPE_PARAM, requestTypeParam)
//                    .appendQueryParameter(FORMAT_PARAM, formatParam)
//                    .appendQueryParameter(STATION_STRING_PARAM, SiteId)
//                    .build();
//
//
//            URL url = new URL(builtUrl.toString());
//
//            doc = new GetStation().execute(url).get();
//
//            Resources resources =
//            XmlResourceParser xrp = resources.getXml(R.xml.airports);
//
//
//            if(doc != null){
//                NodeList nodeList = doc.getElementsByTagName(mSiteNameField);
//                if(nodeList.getLength() > 0){
//                    SiteName = doc.getElementsByTagName(mSiteNameField).item(0).getTextContent();
//                    Latitude = doc.getElementsByTagName(mLatitudeTextField).item(0).getTextContent();
//                    Longitude = doc.getElementsByTagName(mLongitudeTextField).item(0).getTextContent();
//                }
//            }
//        } catch (InterruptedException | ExecutionException | MalformedURLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
}
