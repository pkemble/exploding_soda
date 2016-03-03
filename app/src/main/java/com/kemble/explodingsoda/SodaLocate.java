package com.kemble.explodingsoda;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.kemble.explodingsoda.lib.Forecast;

/**
 * Created by pete on 2/14/16.
 */
public class SodaLocate implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private String TAG = SodaLocate.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected");

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(50000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation != null){
            Log.d(TAG, "Found Location");
            Forecast forecast =
                    new Forecast(Double.toString(mLastLocation.getLatitude()),
                            Double.toString(mLastLocation.getLongitude()));
            if(forecast != null){
                //GetSodaFate(this.getView(), forecast);
            }
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void createLocationRequest() {

    }

    public synchronized void buildGoogleApiClient(Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }
}
