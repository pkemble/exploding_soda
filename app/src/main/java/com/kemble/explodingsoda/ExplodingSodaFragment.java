package com.kemble.explodingsoda;

/**
 * Created by pete on 2/13/16.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kemble.explodingsoda.lib.Forecast;
import com.kemble.explodingsoda.lib.StationInfo;
import com.kemble.explodingsoda.lib.TempArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

//import android.widget.ImageView;

public class ExplodingSodaFragment extends Fragment {

    private String mSodaStationId;
    private final String TAG = ExplodingSodaFragment.class.getName();

    public ExplodingSodaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View sodaView = inflater.inflate(R.layout.exploding_soda, container, false);
        final Button btnSodaStation = (Button) sodaView.findViewById(R.id.btn_soda_station);

        if(mSodaStationId == null){
            mSodaStationId = "KBED";
        }

        GetSodaFate(sodaView);

        btnSodaStation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(sodaView.getContext());

                final EditText input = new EditText(v.getContext());
                alert.setView(input);
                alert.setTitle("Enter an ICAO station identifier:");
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String newStationId = input.getText().toString().trim();
                        //TODO handle blank entries
                        if (newStationId.length() == 3 && !Character.isDigit(newStationId.charAt(0))) {
                            newStationId = "K" + newStationId;
                        }
                        if (newStationId.length() == 4) {
                            mSodaStationId = newStationId;
                            GetSodaFate(sodaView);
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }
        });

        return sodaView;
    }

    private void GetSodaFate(View sodaView){
        StationInfo stationInfo = new StationInfo();

        Resources res = getResources();
        XmlResourceParser xrp = res.getXml(R.xml.airports);


        Forecast forecast = null;
        if(stationInfo.Latitude != null && stationInfo.Longitude != null){
            forecast = new Forecast(stationInfo.Latitude, stationInfo.Longitude);
        }
        GetSodaFate(sodaView, forecast);
    }

    private void GetSodaFate(View sodaView, Forecast forecast) {

        ImageView ivMap = (ImageView) sodaView.findViewById(R.id.img_map);
        if(ivMap != null && forecast != null && forecast.MapForecastLocation != null){
            ivMap.setImageBitmap(forecast.MapForecastLocation);
        }

        TextView tvSodaTime = (TextView) sodaView.findViewById(R.id.tv_soda_time);
        TextView tvMoreInfo = (TextView) sodaView.findViewById(R.id.tv_more_info);

        if(forecast == null){
            tvSodaTime.setText("No weather available. Check your network connection.");
        } else {

            if (!forecast.FCTimeTempMap.TimePeriods.isEmpty()) {
                TempArrayAdapter tempArrayAdapter = new TempArrayAdapter(sodaView.getContext(), forecast);

                ListView lvForecast = (ListView) sodaView.findViewById(R.id.lv_forecast);
                lvForecast.setAdapter(tempArrayAdapter);
            }

            //tvTemp.setText(result + (char) 0x00B0 + "F");
            tvSodaTime.setText("Refreshed at " + getLocalTime());
            tvMoreInfo.setText(forecast.Description + "\n" + forecast.MoreInfoUrl);
        }
        Activity activity = getActivity();
        SharedPreferences sharedPref = activity.getPreferences(activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString(getString(R.string.pref_default_soda_station_key), mSodaStationId);
        edit.commit();

        Linkify.addLinks(tvMoreInfo, Linkify.ALL);


    }

    public static String getLocalTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy, HH:mm");
        return sdf.format(new Date());
    }


}
