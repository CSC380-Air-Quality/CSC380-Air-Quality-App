package com.branbron.bagos.airquality;

import android.app.Dialog;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by bagos on 10/30/2014.
 */
public class MAP extends FragmentActivity {

    GoogleMap googleMap;

    GPS inst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myactivity);
        final Button openMAP = (Button) findViewById(R.id.openMap);
        openMAP.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View Arg0) {
                setContentView(R.layout.map);
            }
        });
        //Get google play availability
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        //Check Status
        if (status != ConnectionResult.SUCCESS) {
            //GooglePlay services are not available
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        } else {
            //GooglePlay is available
            // Get reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();
            // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);
            // LocationManager object from LOCATION_SERVICE
            final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();
            // name of the best provider
            final String provider = locationManager.getBestProvider(criteria, true);
            // Get most current location
            final Location spot = locationManager.getLastKnownLocation(provider);
            final LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //draw marker on change
                    drawMarker(spot);
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }

            };
            locationManager.requestLocationUpdates(provider, 600000, 10, locationListener);

        }
    }
    private void drawMarker(Location loc){
        googleMap.clear();
        LatLng currentSpot = new LatLng(loc.getLatitude(),loc.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(currentSpot).snippet("Lat:" + loc.getLatitude() + "Lng:"+ loc.getLongitude()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title("ME"));

    }


}