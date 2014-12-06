package com.example.mustard.airqualityapplication;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Handler;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Mark Williams on 10/25/2014.
 */
/*
*Brandon Agostinelli
*Keith Fosmire
*Alexander Piechowicz-Merlizzi
*Douglas Sherwood
*Mark Williams
 */
public class Session {
    private ArrayList<LocationDataPoint> alData;
    private ArrayList<Integer> alLocationId;
    private boolean bNewData;
    private int intLastUsed;
    private int intSessionID;
    private int intLocationID;
    private int intAirSampleID;
    private int intSessionDataID;
    private String strCountry;
    private String strCity;
    private String strStreet;
    private String strZip;
    private TextView tvNo;
    private TextView tvCo;
    private TextView tvTemp;
    private TextView tvHumid;
    private Handler handler;
    private DataHandler dbHandler;
    private SQLiteDatabase database;
    private Cursor c;
    private String strNote;
    private boolean bInSession = false;
    public Location loc;
    /**
     *Constructor
     */
    public Session(Activity actIn, TextView tvNoIn, TextView tvCoIn, TextView tvTempIn, TextView tvHumidIn, Location locIn){
        this.alData = new ArrayList<LocationDataPoint>();
        this.alLocationId = new ArrayList<Integer>();
        this.bNewData = false;
        this.strCountry = "";
        this.strCity = "";
        this.strStreet = "";
        this.strZip = "";
        this.intLastUsed = 0;
        this.intSessionID = 0;
        this.tvNo = tvNoIn;
        this.tvCo = tvCoIn;
        this.tvTemp = tvTempIn;
        this.tvHumid = tvHumidIn;
        this.handler = new Handler();
        this.dbHandler = new DataHandler(actIn);
        this.database = dbHandler.getWritableDatabase();
        this.loc = locIn;
        //String tempString =

    }

    /**
     * Adds a LocationDataPoint to the alData ArrayList
     * @param dataPointIn the new LocationDataPoint
     */
    public synchronized void giveDataPoint(LocationDataPoint dataPointIn){
        this.alData.add(dataPointIn);
        this.bNewData = true;
        //System.out.println("Session: Data Received");
        if(this.bInSession){
            //create a new location
            //get next location id
            this.c = database.rawQuery("SELECT DISTINCT id FROM location ORDER BY id DESC LIMIT 1", new String[]{});
            if(c.moveToFirst()){
                //System.out.println("WE HAVE Location ID");
                String temp = c.getString(c.getColumnIndex("id"));
                //System.out.println(temp);
                this.intLocationID = Integer.parseInt(temp) + 1;
                this.alLocationId.add(intLocationID);

            }else{
                System.out.println("No locattion id");
                this.intLocationID = 0;
                this.alLocationId.add(intLocationID);
            }
            c.close();

            //save location info
            System.out.println("Lat: " + loc.getLatitude());
            System.out.println("Long: " + loc.getLongitude());
            database.execSQL("INSERT INTO location (id, xAxis, yAxis)VALUES(" + intLocationID + ", \"" + loc.getLatitude() + "\", \"" + loc.getLongitude() + "\" );");

            //add air sample infor
            //create a new air sample
            //get next airsample id
            this.c = database.rawQuery("SELECT DISTINCT id FROM airSample ORDER BY id DESC LIMIT 1", new String[]{});
            if(c.moveToFirst()){
                //System.out.println("WE HAVE airsample ID");
                String temp = c.getString(c.getColumnIndex("id"));
                //System.out.println(temp);
                this.intAirSampleID = Integer.parseInt(temp) + 1;

            }else{
                System.out.println("No air sample id");
                this.intAirSampleID = 0;
            }
            c.close();

            database.execSQL("INSERT INTO airSample (id, co, no2, humidity, temp)VALUES(" + intAirSampleID + ", " + dataPointIn.getCo() + ", " + dataPointIn.getNo()
                    + ", " + dataPointIn.getHumidity() + ", " + dataPointIn.getTemp() + ");");

            //link to session
            this.c = database.rawQuery("SELECT DISTINCT id FROM sessionData ORDER BY id DESC LIMIT 1", new String[]{});
            if(c.moveToFirst()){
                //System.out.println("WE HAVE sessionData ID");
                String temp = c.getString(c.getColumnIndex("id"));
                //System.out.println(temp);
                this.intSessionDataID = Integer.parseInt(temp) + 1;

            }else{
                System.out.println("No air sample id");
                this.intSessionDataID = 0;
            }
            c.close();
            database.execSQL("INSERT INTO sessionData (id, sessionID, locationId, airSampleId)VALUES(" + intSessionDataID + ", " + intSessionID + ", " + intLocationID + ", " + intAirSampleID + ");");
        }
    }

    /**
     * Asks the session if it has new data
     * @return boolean
     */
    public boolean hasNewData(){
        return this.bNewData;
    }

    public void updateTextViews(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(alData.size() > 0 && intLastUsed > -1){
                    if(intLastUsed < alData.size()){
                        tvNo.setText("NO2\n" + alData.get(intLastUsed).getNo());
                        tvCo.setText("CO\n" + alData.get(intLastUsed).getCo());
                        tvTemp.setText("Temperature\n" + alData.get(intLastUsed).getTemp());
                        tvHumid.setText("Humidity\n" + alData.get(intLastUsed).getHumidity());
                        intLastUsed ++;
                    }else{
                        tvNo.setText("NO2\n" + alData.get(alData.size() - 1).getNo());
                        tvCo.setText("CO\n" + alData.get(alData.size() - 1).getCo());
                        tvTemp.setText("Temperature\n" + alData.get(alData.size() - 1).getTemp());
                        tvHumid.setText("Humidity\n" + alData.get(alData.size() - 1).getHumidity());
                    }
                }else{
                    tvNo.setText("NO2\nNo Data");
                    tvCo.setText("CO\nNo Data");
                    tvTemp.setText("Temperature\nNo Data");
                    tvHumid.setText("Humidity\nNo Data");
                }
            }
        });
    }

    public void startNewSession(){
        //get last ID
        this.c = database.rawQuery("SELECT DISTINCT id FROM session ORDER BY id DESC LIMIT 1", new String[]{});
        if(c.moveToFirst()){
            //System.out.println("WE HAVE C");
            String temp = c.getString(c.getColumnIndex("id"));
            //System.out.println(temp);
            this.intSessionID = Integer.parseInt(temp) + 1;

        }else{
            System.out.println("No C");
        }
        c.close();

        //create new session record in DB
        System.out.println("Session ID: " + intSessionID);
        database.execSQL("INSERT INTO session (id)VALUES(" + intSessionID + ");");
        //Long l = System.nanoTime();
        //Date temp = new Date();
        //System.out.println(temp.toString());
        Long unixTime = System.currentTimeMillis() / 1000L;
        System.out.println(unixTime);
        //java.util.Date time=new java.util.Date((long)unixTime*1000);
        //System.out.println(time);
        database.execSQL("UPDATE session SET startTime = " + unixTime.doubleValue() + " WHERE id = " + intSessionID + ";");

        //clear note
        this.strNote = " ";
        this.bInSession = true;
    }

    public void updateNote(String strNote){
        this.strNote += "\n" + strNote;

    }

    public void endSession(){
        //todo
        //save note
        //save location information
        //save your mom
        System.out.println("End Session Called!!!!!!!!!!!");
        Long lnEndTime = System.currentTimeMillis() / 1000L;
        System.out.println("SESSION ID: " + intSessionID);
        System.out.println("END TIME: " + lnEndTime.doubleValue());
        database.execSQL("UPDATE session SET endTime = " + lnEndTime.doubleValue() + ", notes = \"" + this.strNote + "\" WHERE id = " + intSessionID + ";");

        for(Integer i:alLocationId){
            System.out.println(i);
            database.execSQL("UPDATE location SET country = \"" + this.strCountry + "\", city  = \""
                    + this.strCity + "\", street = \"" + this.strStreet + "\", zipCode = \"" + this.strZip + "\" WHERE id = " + i + ";" );
        }

        this.bInSession = false;
    }

    public void setCountry(String strIn){
        this.strCountry = strIn;
    }

    public void setCity(String strIn){
        this.strCity = strIn;
    }

    public void setStreet(String strIn){
        this.strStreet = strIn;
    }

    public void setZip(String strIn){
        this.strZip = strIn;
    }

    public synchronized LocationDataPoint getDataPoint() {
        if (alData.size() > 0 && intLastUsed > -1) {
            if (intLastUsed < alData.size()) {
                return alData.get(intLastUsed);
            } else {
                return alData.get(alData.size() - 1);
            }
        }
        return new LocationDataPoint(0, 0, 0, 0);
    }
}
