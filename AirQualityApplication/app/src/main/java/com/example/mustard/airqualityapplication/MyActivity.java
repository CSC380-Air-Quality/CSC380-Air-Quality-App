package com.example.mustard.airqualityapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MyActivity extends FragmentActivity {

    private FragmentTabHost th;
    private boolean bInSession = false;
    private Button btnLeft, btnRight;
    private AlertDialog.Builder builder;
    private LayoutInflater inflator;
    private TextView tvNo;
    private TextView tvCo;
    private TextView tvTemp;
    private TextView tvHumid;
    private DataQueue dataQueue;
    private Session mainSession;
    private Graph mainGraph;
    private Thread queueThread;
    private Thread ioThread;
    private Thread sessionThread;
    private Lock lock = new ReentrantLock();
    private String strCountry = null;
    private String strCity = null;
    private String strStreet = null;
    private String strZip = null;
    private GoogleMap googleMap;
    private boolean bMap = false;
    private boolean bFirstRun = true;
    private Location loc;
    /*
    DataQueue queue = new DataQueue();
        Session session = new Session();
        Graph graph = new Graph();


        Thread queueThread = new DataQueueThread(session, graph, queue);
        Thread ioThread = new IOThread(queue);


     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        builder = new AlertDialog.Builder(this);
        inflator = this.getLayoutInflater();
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnRight = (Button) findViewById(R.id.btnRight);

        setButtonListeners();

        th = (FragmentTabHost) findViewById(android.R.id.tabhost);
        th.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        th.addTab(th.newTabSpec("tab1").setIndicator("Map"), MapTab.class, null);
        th.addTab(th.newTabSpec("tab2").setIndicator("Home"), HomeTab.class, null);
        th.addTab(th.newTabSpec("tab3").setIndicator("Graph"), GraphTab.class, null);
        th.setCurrentTab(1);

        tvNo = (TextView) findViewById(R.id.tvNo);
        tvCo = (TextView) findViewById(R.id.tvCo);
        tvTemp = (TextView) findViewById(R.id.tvTemp);
        tvHumid = (TextView) findViewById(R.id.tvHumid);

        //Make data queue stuff
        dataQueue = new DataQueue();
        mainSession = new Session(this, tvNo,tvCo,tvTemp,tvHumid);
        mainGraph = new Graph();
        sessionThread = new SessionThread(mainSession);
        ioThread = new IOThread(dataQueue);
        queueThread = new DataQueueThread(mainSession,mainGraph,dataQueue);

        ioThread.start();
        queueThread.start();
        sessionThread.start();

        Thread thread = new Thread(){
            public void run(){
                for(;;){
                    try{
                        //This is how we control how often we generate a new data point
                        this.sleep(1000);
                    }catch(InterruptedException ie){

                    }

                    if(bMap){
                        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                        // Getting GoogleMap object from the fragment
                        googleMap = fm.getMap();
                        if(googleMap  != null){
                            runMap();
                        }else{
                            System.out.println("SUPER NULL!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        }
                    }
                }

            }
        };
        thread.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showInfo(MenuItem item){
        Intent i = new Intent(MyActivity.this, InfoListActivity.class);
        startActivity(i);
    }

    public void showSetting(MenuItem item){
        Intent i = new Intent(MyActivity.this, SettingsListActivity.class);
        startActivity(i);
    }



    @Override
    public void onAttachFragment(android.support.v4.app.Fragment fragment){
        System.err.println("HERE SIR.");
        super.onAttachFragment(fragment);
        if(th.getCurrentTab() == 0){
            Toast toast = Toast.makeText(this,"Tesint", Toast.LENGTH_LONG);
            toast.show();
            bMap = true;
            //runMap();
        }else{
            if(googleMap != null) {
                googleMap.clear();
            }
        }
        /*if(((Object)fragment).getClass() == HomeTab.class){
            System.out.println("WE HAVE A FRAGMENT HOME");
            Bundle arguments = new Bundle();
            arguments.putString("strId","DATA");
            HomeTab temp = new HomeTab();// (HomeTab)fragment;
            temp.setArguments(arguments);
            fragment = temp;
            getSupportFragmentManager().beginTransaction().add(R.id.graphFragLayout, fragment).commit();

        }else if (((Object)fragment).getClass() == GraphTab.class){
            System.out.println("WE HAVE A FRAGMENT GRAPH");
        }*/
    }

    private void setButtonListeners(){

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bInSession){
                    //end the session
                    bInSession = false;

                    /*Fragment curentFrag = getFragmentManager().findFragmentByTag("Home");
                    if(curentFrag == null){
                        System.out.println("Null");
                    }else{
                        System.out.println("Not NULL");
                    }*/


                    HomeTab currentFrag = (HomeTab) getSupportFragmentManager().findFragmentById(R.id.realtabcontent);


                    if(currentFrag == null){
                        System.out.println("Null");
                    }else{
                        System.out.println("Not NULL");
                    }

                    //add a note
                    LayoutInflater inf1 = LayoutInflater.from(MyActivity.this);
                    final View inf2 = inf1.inflate(R.layout.dialog_note,null);
                    builder.setTitle("Note");
                    //builder.setView(inflator.inflate(R.layout.dialog_note, null));
                    builder.setView(inf2);
                    builder.setPositiveButton("Save Note", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText temp = (EditText) inf2.findViewById(R.id.editCountry);
                            if(temp != null){
                                strCountry = temp.getText().toString();
                                mainSession.setCountry(strCountry);
                            }

                            temp = (EditText) inf2.findViewById(R.id.editCity);
                            if(temp != null){
                                strCity = temp.getText().toString();
                                mainSession.setCity(strCity);
                            }

                            temp = (EditText) inf2.findViewById(R.id.editStreet);
                            if(temp != null){
                                strStreet = temp.getText().toString();
                                mainSession.setStreet(strStreet);
                            }

                            temp = (EditText) inf2.findViewById(R.id.editZipCode);
                            if(temp != null){
                                strZip = temp.getText().toString();
                                mainSession.setZip(strZip);
                            }

                             temp = (EditText) inf2.findViewById(R.id.editNote);
                            if(temp != null){
                                String temp2 = temp.getText().toString();
                                mainSession.updateNote(temp2);
                            }else{
                                System.out.println("ERROR: NULL");
                            }
                            mainSession.endSession();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mainSession.endSession();
                        }
                    });
                    if(strCountry != null){
                        EditText tempCountry = (EditText) inf2.findViewById(R.id.editCountry);
                        tempCountry.setText(strCountry);
                    }
                    if(strCity != null){
                        EditText tempCity = (EditText) inf2.findViewById(R.id.editCity);
                        tempCity.setText(strCity);
                    }
                    if(strStreet != null){
                        EditText tempStreet = (EditText) inf2.findViewById(R.id.editStreet);
                        tempStreet.setText(strStreet);
                    }
                    if(strZip != null){
                        EditText tempZip = (EditText) inf2.findViewById(R.id.editZipCode);
                        tempZip.setText(strZip);
                    }
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    //end of add note

                    btnLeft.setText("Start Session");
                    btnRight.setText("Existing Sessions");
                }else{
                    //start the session
                    bInSession = true;
                    btnLeft.setText("End Session");
                    btnRight.setText("Add Note");
                    mainSession.startNewSession();
                }
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bInSession){
                    //add a note
                    LayoutInflater inf1 = LayoutInflater.from(MyActivity.this);
                    final View inf2 = inf1.inflate(R.layout.dialog_note,null);



                    builder.setTitle("Note");
                    //builder.setView(inflator.inflate(R.layout.dialog_note, null));
                    builder.setView(inf2);
                    builder.setPositiveButton("Save Note", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText temp = (EditText) inf2.findViewById(R.id.editCountry);
                            if(temp != null){
                                strCountry = temp.getText().toString();

                            }

                            temp = (EditText) inf2.findViewById(R.id.editCity);
                            if(temp != null){
                                strCity = temp.getText().toString();
                            }

                            temp = (EditText) inf2.findViewById(R.id.editStreet);
                            if(temp != null){
                                strStreet = temp.getText().toString();
                            }

                            temp = (EditText) inf2.findViewById(R.id.editZipCode);
                            if(temp != null){
                                strZip = temp.getText().toString();
                            }

                            temp = (EditText) inf2.findViewById(R.id.editNote);
                            //save note
                            if(temp != null){
                                String temp2 = temp.getText().toString();
                                mainSession.updateNote(temp2);
                            }else{
                                System.out.println("ERROR: NULL");
                            }
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    if(strCountry != null){
                        EditText tempCountry = (EditText) inf2.findViewById(R.id.editCountry);
                        tempCountry.setText(strCountry);
                    }

                    if(strCity != null){
                        EditText tempCity = (EditText) inf2.findViewById(R.id.editCity);
                        tempCity.setText(strCity);
                    }
                    if(strStreet != null){
                        EditText tempStreet = (EditText) inf2.findViewById(R.id.editStreet);
                        tempStreet.setText(strStreet);
                    }
                    if(strZip != null){
                        EditText tempZip = (EditText) inf2.findViewById(R.id.editZipCode);
                        tempZip.setText(strZip);
                    }
                    AlertDialog dialog = builder.create();
                    dialog.show();


                }else{
                    //go to existing sessions list
                    Intent i = new Intent(MyActivity.this, ExistingSessionListActivity.class);
                    startActivity(i);
                }
            }
        });

    }

    private void runMap(){
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
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable(){
                @Override
                public void run(){
                    googleMap.setMyLocationEnabled(true);
                    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    Criteria criteria = new Criteria();
                    String provider = locationManager.getBestProvider(criteria, true);
                    LocationListener locationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            //draw marker on change
                            loc = location;
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
                    if(bFirstRun){
                        if(loc != null){
                            bFirstRun = false;
                            LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
                            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                            googleMap.moveCamera(update);
                        }
                    }
                }
            });
            //googleMap.setMyLocationEnabled(true);
            // LocationManager object from LOCATION_SERVICE
            //LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            // Creating a criteria object to retrieve provider
            //Criteria criteria = new Criteria();
            // name of the best provider
            //String provider = locationManager.getBestProvider(criteria, true);

            /*LocationListener locationListener = new LocationListener() {
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

            };*/

            //locationManager.requestLocationUpdates(provider, 600000, 10, locationListener);

        }
    }

    private void drawMarker(Location loc){
        googleMap.clear();
        LatLng currentSpot = new LatLng(loc.getLatitude(),loc.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(currentSpot).snippet("Lat:" + loc.getLatitude() + "Lng:"+ loc.getLongitude()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title("ME"));

    }

}
