package com.example.mustard.airqualityapplication;

import android.app.Dialog;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mark on 10/19/2014.
 */
/**
 * Created by bagos on 10/30/2014.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapTab extends Fragment {

    //private static GoogleMap mMap
    private boolean bShowMap;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_map, container, false);

        //SupportMapFragment fm = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        //googleMap = fm.getMap();

        return V;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Get google play availability
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity().getBaseContext());
        if (status != ConnectionResult.SUCCESS) {
            //GooglePlay services are not available
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getActivity(), requestCode);
            dialog.show();
        }else{
            Toast toast = Toast.makeText(getActivity(),"Tesint", Toast.LENGTH_LONG);
            //toast.show();

            //GooglePlay is available
            // Get reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
            // Getting GoogleMap object from the fragment
            /*googleMap = fm.getMap();
            // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);
            // LocationManager object from LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();
            // name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //draw marker on change
                    drawMarker(location);
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

            locationManager.requestLocationUpdates(provider, 600000, 10, locationListener);*/




        }
    }

    private void runMap(){
        //Get google play availability
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity().getBaseContext());
        //Check Status
        if (status != ConnectionResult.SUCCESS) {
            //GooglePlay services are not available
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getActivity(), requestCode);
            dialog.show();
        } else {
            //GooglePlay is available
            // Get reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();
            // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);
            // LocationManager object from LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();
            // name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //draw marker on change
                    drawMarker(location);
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