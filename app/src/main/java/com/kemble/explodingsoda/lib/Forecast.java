package com.kemble.explodingsoda.lib;


import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.kemble.explodingsoda.GetMap;
import com.kemble.explodingsoda.GetWx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Forecast {

    protected String TAG = Forecast.class.getName();
    private URL mForecastUrl;
    private String mMapUrl = Strings.GoogleMapUrl;
    public String Description;
    public String MoreInfoUrl;
    private String mNoData;
    private String mDescriptionTextField = Strings.DescriptionTextField;
    private String mTemperatureTextField = Strings.TemperatureTextfield;
    private String mDataTextField = Strings.DataTextField;
    private String mValues = "value";
    private String mMoreWeatherInformation = Strings.MoreWeatherInformationTextField;
    public Bitmap MapForecastLocation;
    public TimeTempMap FCTimeTempMap = new TimeTempMap();

    public class TimeTempMap {
        public String LayoutKey;
        public String Name;
        public ArrayList<TimePeriod> TimePeriods = new ArrayList<>();
    }

    public class TimePeriod {
        public String PeriodName;
        public Date PeriodStart;
        public Date PeriodEnd;
        public int Temperature;
    }

    public Forecast(String lat, String lon) {
        mNoData = "No forecast :(";
        //TODO: handle a garbage or empty forecast
        Document doc;

        String coords = lat + "," + lon;
        String zoomParam = "12";
        String sizeParam = "400x400";
        String markersParam = "size:mid|color:red|" + coords;

        String unitsParam = "0";
        String langParam = "en";
        String forecastTypeParam = "json";

        try {

            //"https://maps.googleapis.com/maps/api/staticmap?center=<LATITUDE>,<LONGITUDE>&zoom=12&size=200x200&markers=size:mid|color:red|<LATITUDE>,<LONGITUDE>"
            final String BASE_MAP_URL = "https://maps.googleapis.com/maps/api/staticmap?";
            final String CENTER_PARAM = "center";
            final String ZOOM_PARAM = "zoom";
            final String SIZE_PARAM = "size";
            final String MARKER_PARAM = "markers";

            Uri mapUri = Uri.parse(BASE_MAP_URL).buildUpon()
                    .appendQueryParameter(CENTER_PARAM, coords)
                    .appendQueryParameter(ZOOM_PARAM, zoomParam)
                    .appendQueryParameter(SIZE_PARAM, sizeParam)
                    .appendQueryParameter(MARKER_PARAM, markersParam)
                    .build();

            URL url = new URL(mapUri.toString());

            MapForecastLocation = new GetMap().execute(url).get();
//http://forecast.weather.gov/MapClick.php?lat=42&lon=-72&unit=0&lg=en&FcstType=dwml
            //http://api.openweathermap.org/data/2.5/forecast?lat=<LATITUDE>&lon=<LONGITUDE>&units=imperial&cnt=1&mode=json&appid=44db6a862fba0b067b1930da0d769e98
            final String BASE_FORECAST_URL = Strings.ForecastUrl;
            final String LAT_PARAM = "lat";
            final String LON_PARAM = "lon";
            final String UNITS_PARAM = "unit";
            final String LANG_PARAM = "lg";
            final String FORECAST_TYPE_PARAM = "FcstType";

            Uri forecastUri = Uri.parse(BASE_FORECAST_URL).buildUpon()
                    .appendQueryParameter(LAT_PARAM, lat)
                    .appendQueryParameter(LON_PARAM, lon)
                    .appendQueryParameter(UNITS_PARAM, unitsParam)
                    .appendQueryParameter(LANG_PARAM, langParam)
                    .appendQueryParameter(FORECAST_TYPE_PARAM, forecastTypeParam)
                    .build();

            mForecastUrl = new URL(forecastUri.toString());
            this.MoreInfoUrl = forecastUri.buildUpon()
                    .appendQueryParameter(FORECAST_TYPE_PARAM, null)
                    .build().toString();

            String jsonForecast = new GetWx().execute(mForecastUrl).get();

            if(jsonForecast != null) {
                Log.d(TAG, "Beginning forecast generation from Document");
                try {

                    JSONObject jsonObject = new JSONObject(jsonForecast);
                    this.Description = jsonObject.getJSONObject("location").get("areaDescription").toString();
                    JSONArray timeArray = jsonObject.optJSONArray("startPeriodName");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Log.d(TAG, "Finished parsing Document for " + this.Description);
        } catch (InterruptedException | ExecutionException | MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}