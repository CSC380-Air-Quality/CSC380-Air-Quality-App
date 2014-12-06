package com.example.mustard.airqualityapplication;

/*
*Brandon Agostinelli
*Keith Fosmire
*Alexander Piechowicz-Merlizzi
*Douglas Sherwood
*Mark Williams
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.TabHost;
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
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.ValueDependentColor;

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
    private LocationManager locationManager;
    private GraphViewSeries.GraphViewSeriesStyle seriesStyle;
    GraphViewSeries noSeries;
    GraphViewSeries coSeries;
    GraphView graphView;
    GraphView lineGraphView;
    GraphViewSeries lineNoSeries;
    GraphViewSeries lineCoSeries;
    GraphViewSeries lineTempSeries;
    GraphViewSeries linehumidSeries;
    int counter = 1;

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

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        loc = locationManager.getLastKnownLocation(provider);

        //graph stuff
        seriesStyle = new GraphViewSeries.GraphViewSeriesStyle();


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

        th.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            public void onTabChanged(String tabId){
                System.out.println("Selected Tab: " + th.getCurrentTab());
                if(th != null){
                    if(th.getCurrentTab() == 0){
                        bMap = true;
                    }else if(th.getCurrentTab() == 1){
                        System.out.println("HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        if(googleMap != null) {
                            googleMap.clear();
                        }
                        System.out.println("HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        setUpBarGraphStuff();


                    }else if(th.getCurrentTab() == 2){
                        if(googleMap != null) {
                            googleMap.clear();

                        }
                        setUpLineGraph();

                    }else{
                        if(googleMap != null) {
                            googleMap.clear();
                        }


                    }
                }else{
                    System.out.println("NULL TAB HOST");
                }
            }
        });

        tvNo = (TextView) findViewById(R.id.tvNo);
        tvCo = (TextView) findViewById(R.id.tvCo);
        tvTemp = (TextView) findViewById(R.id.tvTemp);
        tvHumid = (TextView) findViewById(R.id.tvHumid);

        setUpBarGraphStuff();
        //Make data queue stuff
        dataQueue = new DataQueue();
        mainSession = new Session(this, tvNo,tvCo,tvTemp,tvHumid, loc);
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

        Thread graphUpdater = new Thread(){
          public void run(){
              for(;;){
                  try{
                      //This is how we control how often we generate a new data point
                      this.sleep(4000);
                  }catch(InterruptedException ie){

                  }

                  if(graphView != null){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            LocationDataPoint temp = mainSession.getDataPoint();
                            noSeries.resetData(new GraphView.GraphViewData[]{
                                    new GraphView.GraphViewData(1, temp.getNo())
                                    , new GraphView.GraphViewData(2, temp.getCo())
                                    , new GraphView.GraphViewData(3, temp.getTemp())
                                    , new GraphView.GraphViewData(4, temp.getHumidity())

                            });

                            coSeries.resetData(new GraphView.GraphViewData[]{
                                    new GraphView.GraphViewData(1, temp.getNo())
                                    , new GraphView.GraphViewData(2, temp.getCo())
                                    , new GraphView.GraphViewData(3, temp.getTemp())
                                    , new GraphView.GraphViewData(4, temp.getHumidity())

                            });

                        }
                    });
                  }

                  if(lineGraphView != null){
                    Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable(){
                        @Override
                        public void run(){
                            LocationDataPoint temp2 = mainSession.getDataPoint();
                            counter ++;
                            lineNoSeries.appendData(new GraphView.GraphViewData(counter, temp2.getNo()), true, 100);
                            lineCoSeries.appendData(new GraphView.GraphViewData(counter, temp2.getCo()), true, 100);
                            linehumidSeries.appendData(new GraphView.GraphViewData(counter, temp2.getHumidity()), true, 100);
                            lineTempSeries.appendData(new GraphView.GraphViewData(counter, temp2.getTemp()), true, 100);
                            //System.out.println("UPDATE");
                            //System.out.println("COUNTER: " + counter);
                            //System.out.println(temp2.getNo());
                            //System.out.println(temp2.getCo());
                            //System.out.println(temp2.getHumidity());
                            //System.out.println(temp2.getTemp());

                        }
                      });
                  }
              }
          }
        };
        graphUpdater.start();

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

        //This is where an andAlso would have been great!
        if(th != null){
            if(th.getCurrentTab() == 0){
                bMap = true;
            }else if(th.getCurrentTab() == 1){
                //System.out.println("HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                if(googleMap != null) {
                    googleMap.clear();
                }
                //System.out.println("HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                //setUpBarGraphStuff();


            }else if(th.getCurrentTab() == 2){
                if(googleMap != null) {
                    googleMap.clear();
                }


            }else{
                if(googleMap != null) {
                    googleMap.clear();
                }


            }
        }else{
            System.out.println("NULL TAB HOST");
        }
    }

    private void setButtonListeners(){

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bInSession){
                    //end the session
                    bInSession = false;

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
        }
    }

    private void drawMarker(Location loc){
        googleMap.clear();
        LatLng currentSpot = new LatLng(loc.getLatitude(),loc.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(currentSpot).snippet("Lat:" + loc.getLatitude() + "Lng:"+ loc.getLongitude()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title("ME"));

    }

    private void setUpBarGraphStuff(){
        seriesStyle = new GraphViewSeries.GraphViewSeriesStyle();
        seriesStyle.setValueDependentColor(new ValueDependentColor() {
            @Override
            public int get(GraphViewDataInterface data) {
                if(data.getY() >= 75){
                    return Color.rgb(191, 0, 0);
                }else if(data.getY() >= 50){
                    return Color.rgb(255,140,0);
                }else if(data.getY() >= 25){
                    return Color.rgb(229,229,0);
                }else{
                    return Color.rgb(0,127,0);
                }
            }
        });

        noSeries = new GraphViewSeries("NO",seriesStyle,new GraphView.GraphViewData[]{
                //GraphViewSeries noSeries = new GraphViewSeries(new GraphView.GraphViewData[]{
                new GraphView.GraphViewData(1, 0.0d)
                , new GraphView.GraphViewData(2, 0.0d)
                , new GraphView.GraphViewData(3, 0.0d)
                , new GraphView.GraphViewData(4, 0.0d)
        });

        coSeries = new GraphViewSeries("NO",seriesStyle,new GraphView.GraphViewData[]{
                //GraphViewSeries noSeries = new GraphViewSeries(new GraphView.GraphViewData[]{
                new GraphView.GraphViewData(1, 0.0d)
                , new GraphView.GraphViewData(2, 0.0d)
                , new GraphView.GraphViewData(3, 0.0d)
                , new GraphView.GraphViewData(4, 0.0d)
        });

        graphView = new BarGraphView(this,"");
        graphView.getGraphViewStyle().setNumHorizontalLabels(4);
        graphView.getGraphViewStyle().setNumVerticalLabels(10);
        graphView.getGraphViewStyle().setVerticalLabelsWidth(100);
        graphView.setManualYMinBound(0);
        graphView.setManualYMaxBound(100.0);

        graphView.setVerticalLabels(new String[]{"High","Med","Low"});
        graphView.setHorizontalLabels(new String[]{"NO","CO2","Temp","Humid"});

        graphView.addSeries(noSeries);
        graphView.addSeries(coSeries);

        //FragmentManager fm = (FragmentManager) findViewById(R.id.lyGrid);
        Thread thread = new Thread(){
            private boolean bRun = true;
            public void run(){
                while(bRun){
                    try{
                        //This is how we control how often we generate a new data point
                        this.sleep(1000);
                    }catch(InterruptedException ie){

                    }

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable(){
                        @Override
                        public void run(){
                            LinearLayout layout = (LinearLayout) th.findViewById(R.id.lyGrid);
                            if(layout == null){
                                System.err.println("null");
                            }else {
                                if(bRun) {
                                    layout.removeView(graphView);
                                    layout.addView(graphView);
                                }
                                bRun = false;
                            }
                        }
                    });
                }

            }
        };
        thread.start();

    }

    private void setUpLineGraph(){
        lineNoSeries = new GraphViewSeries("NO", new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(255, 0, 0), 3), new GraphView.GraphViewData[]{
                new GraphView.GraphViewData(1,0.0d)

        });

        lineCoSeries = new GraphViewSeries("CO2", new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(0, 255, 0), 3), new GraphView.GraphViewData[]{
                new GraphView.GraphViewData(1,0.0d)

        });

        lineTempSeries = new GraphViewSeries("Temp", new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(0, 0, 255), 3), new GraphView.GraphViewData[]{
                new GraphView.GraphViewData(1,0.0d)

        });

       linehumidSeries = new GraphViewSeries("Humid", new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(93, 0, 73), 3), new GraphView.GraphViewData[]{
                new GraphView.GraphViewData(1,0.0d)

        });
        lineGraphView = new LineGraphView(this,"");
        lineGraphView.setScrollable(true);

        lineGraphView.addSeries(lineNoSeries);

        lineGraphView.addSeries(lineCoSeries);
        lineGraphView.addSeries(lineTempSeries);
        lineGraphView.addSeries(linehumidSeries);
        lineGraphView.setTitle("");

        lineGraphView.setVerticalLabels(new String[]{"High","Med","Low"});
        lineGraphView.setHorizontalLabels(new String[]{"", "", ""});
        lineGraphView.getGraphViewStyle().setNumHorizontalLabels(0);
        lineGraphView.getGraphViewStyle().setNumVerticalLabels(3);
        lineGraphView.getGraphViewStyle().setVerticalLabelsWidth(100);
        lineGraphView.setShowLegend(true);
        lineGraphView.setLegendAlign(GraphView.LegendAlign.TOP);
        lineGraphView.isScrollable();
        lineGraphView.setLegendWidth(200);

        Thread thread = new Thread(){
            private boolean bRun = true;
            public void run(){
                while(bRun){
                    try{
                        //This is how we control how often we generate a new data point
                        this.sleep(1000);
                    }catch(InterruptedException ie){

                    }

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable(){
                        @Override
                        public void run(){
                            LinearLayout layout = (LinearLayout) th.findViewById(R.id.graphFragLayout);
                            if(layout == null){
                                System.err.println("null");
                            }else {
                                if(bRun) {
                                    layout.addView(lineGraphView);
                                }
                                bRun = false;
                            }
                        }
                    });
                }

            }
        };
        thread.start();
        System.out.println("Test Message");
    }

}
