
package com.branbron.bagos.airquality;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by bagos on 10/6/2014.
 */

public class GPS extends Activity{

    private GPS location;
    private String cityName = "";
    private String country = "";
    private String street = "";
    private String postalCode = "";
    private double lat;
    private double lng;
    private int miliSeconds = 60000;
    private int distance = 50;

    public GPS(){}

    public GPS(String cty, String ctry, String srt, String zip, double lt, double lg) {
        cityName = cty;
        country = ctry;
        street = srt;
        postalCode = zip;
        lat = lt;
        lng = lg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       if (!onGPS()) {

            //Have option to enter in location information
            setContentView(R.layout.activity_myactivity);
            final Button openInputWindow = (Button) findViewById(R.id.openuserinput);
            openInputWindow.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.popup, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    Button done = (Button) popupView.findViewById(R.id.done);
                    done.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAsDropDown(openInputWindow, 50, -30);
                }
            });
            //Doesn't update gps location after this

        } else {
            LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            LocationListener locListener = new LocationListener(){
                //reacting to a location change and gathering appropriate location information
                @Override
                public void onLocationChanged(Location loc) {
                    lat = loc.getLatitude();
                    lng = loc.getLongitude();
                    Geocoder geo = new Geocoder(getBaseContext(), Locale.getDefault());
                    List<Address> a = null;
                    try
                    {
                        a = geo.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                    }
                    if (a != null && a.size() > 0) {
                        cityName = a.get(0).getLocality();
                        country = a.get(0).getCountryName();
                        postalCode = a.get(0).getPostalCode();
                        if (a.get(0).getMaxAddressLineIndex() > 0)
                            street = a.get(0).getAddressLine(0);
                        location = new GPS(cityName, country, street, postalCode, lat, lng);
                    } else {
                        System.out.println("No address found.");
                    }
                }

                //GPS disabled -- Called when the provider is disabled by the user.
                @Override
                public void onProviderDisabled(String provider) {
                    //TODO Auto-generated method stub
                }

                //GPS Enabled -- Called when the provider is enabled by the user.
                @Override
                public void onProviderEnabled(String provider) {
                    //TODO Auto-generated method stub
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    //TODO Auto-generated method stub
                }
            };
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, miliSeconds, distance, locListener);
        }
    }

    public void setTime(int r){
        miliSeconds = r;
    }

    public void setDistance(int r){
        distance = r;
    }

    //Checks if the GPS is on or off
    public boolean onGPS() {
        boolean gpsStat;
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        gpsStat = locManager.isProviderEnabled(locManager.GPS_PROVIDER);
        if (gpsStat) {
            return true;
        } else {
            return false;
        }
    }

    //If no GPS is enabled on the device the user can be prompted for general location information
    public void userInputLocation(View v) {

        EditText city = (EditText) findViewById(R.id.city);
        EditText count = (EditText) findViewById(R.id.country);
        EditText strt = (EditText) findViewById(R.id.street);
        EditText zp = (EditText) findViewById(R.id.zip);
        if(city != null){
            cityName = city.getText().toString();
        }
        if(count != null){
            country = count.getText().toString();
        }
        if(strt != null){
            street = strt.getText().toString();
        }
        if(zp != null){
            postalCode = zp.getText().toString();
        }
        location = new GPS(cityName, country, street, postalCode, 91, 191);

    }

    //Get current GPS location when necessary.
    public GPS giveLocation() {
        return location;
    }

}
