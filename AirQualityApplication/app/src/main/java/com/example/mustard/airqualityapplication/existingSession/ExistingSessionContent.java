package com.example.mustard.airqualityapplication.existingSession;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mustard.airqualityapplication.DataHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ExistingSessionContent {

    public static List<ExistingSessionItem> ITEMS = new ArrayList<ExistingSessionItem>();
    private DataHandler dbHandler;
    private SQLiteDatabase database;
    private Cursor cr;

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, ExistingSessionItem> ITEM_MAP = new HashMap<String, ExistingSessionItem>();

    public static void setContext(Context ct){
        DataHandler dbHandler;
        SQLiteDatabase database;
        Cursor cr;
        ITEMS.clear();
        ITEM_MAP.clear();
        dbHandler = new DataHandler(ct);
        database = dbHandler.getWritableDatabase();

        if(database.isOpen() == false){
            //System.out.println("Database is not open");
        }else{
            //System.out.println("Database is open");
        }


        cr = database.rawQuery("SELECT id, startTime, endTime, notes FROM session ORDER BY id DESC ", new String[]{});
        if(cr.moveToFirst()){
            do{
                double unixTime = cr.getDouble(cr.getColumnIndex("startTime"));
                java.util.Date time = new java.util.Date((long)unixTime*1000);
                ExistingSessionItem tempItem = new ExistingSessionItem(cr.getString(cr.getColumnIndex("id")), time.toString());

                Cursor cr2 = database.rawQuery("SELECT locationId, airSampleId FROM sessionData WHERE sessionID = " + cr.getInt(cr.getColumnIndex("id")), new String[]{});
                //set location information
                if(cr2.moveToFirst()){
                    int intTopLocationID = cr2.getInt(cr2.getColumnIndex("locationId"));
                    //System.out.println("intTopLocationID: " + intTopLocationID);
                    Cursor cr3 = database.rawQuery("SELECT country, city, street, zipCode FROM location WHERE id = " + intTopLocationID, new String[]{});
                    if(cr3.moveToFirst()){
                        tempItem.strCountry = cr3.getString(cr3.getColumnIndex("country"));
                        tempItem.strCity = cr3.getString(cr3.getColumnIndex("city"));
                        tempItem.strStreet = cr3.getString(cr3.getColumnIndex("street"));
                        tempItem.strZip = cr3.getString(cr3.getColumnIndex("zipCode"));
                        //System.out.println("We have a TOP LOCATION");
                        //System.out.println("Country: " + tempItem.strCountry);
                    }
                    cr3.close();

                    do{
                        cr3 = database.rawQuery("SELECT co, no2, humidity, temp FROM airSample WHERE id = " + cr2.getInt(cr2.getColumnIndex("airSampleId")),  new String[]{});
                        if(cr3.moveToFirst()){
                            tempItem.alCo.add(cr3.getInt(cr3.getColumnIndex("co")));
                            tempItem.alNo.add(cr3.getInt(cr3.getColumnIndex("no2")));
                            tempItem.alHum.add(cr3.getInt(cr3.getColumnIndex("humidity")));
                            tempItem.alTemp.add(cr3.getInt(cr3.getColumnIndex("temp")));
                        }
                        cr3.close();
                    }while(cr2.moveToNext());

                }
                cr2.close();


                //set start time
                double dbStartTime = cr.getDouble(cr.getColumnIndex("startTime"));
                java.util.Date tempStartTime = new java.util.Date((long)dbStartTime*1000);
                tempItem.strStartTime = tempStartTime.toString();

                //set end time
                double dbEndTime = cr.getDouble(cr.getColumnIndex("endTime"));
                java.util.Date tempEndTime = new java.util.Date((long)dbEndTime*1000);
                tempItem.strEndTime = tempEndTime.toString();

                tempItem.strNote = cr.getString(cr.getColumnIndex("notes"));

                addItem(tempItem);
            }while(cr.moveToNext());

        }else{
            //System.out.println("No SessionID");
        }
        cr.close();
        /*if(cr.moveToFirst()){
            do{
                System.out.println("STUFF");
                if(cr.getString(cr.getColumnIndex("id")) != null){
                    System.out.println("Not null");
                }else{
                    System.out.println("SUPER null");
                }
                //String temp = cr.getString(cr.getColumnIndex("id"));
                //System.out.println(temp);
            }while(cr.moveToNext());

        }else{
            System.out.println("No session ids");
        }*/
        cr.close();
    }

    /*static {
        // Add 3 sample items.
        addItem(new ExistingSessionItem("1", "10/02/2014 16:22"));
        addItem(new ExistingSessionItem("2", "10/15/2014 10:52"));
        addItem(new ExistingSessionItem("3", "10/22/2014 11:16"));
    }*/

    private static void addItem(ExistingSessionItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ExistingSessionItem {
        public String id;
        public String strTitle;
        public String content;
        public String strStartTime;
        public String strEndTime;
        public String strCountry;
        public String strCity;
        public String strStreet;
        public String strZip;
        public String strNote;
        public ArrayList<Integer> alNo;
        public ArrayList<Integer> alCo;
        public ArrayList<Integer> alHum;
        public ArrayList<Integer> alTemp;

        public ExistingSessionItem(String id, String strTitleIn) {
            this.id = id;
            this.strTitle = strTitleIn;
            this.alNo = new ArrayList<Integer>();
            this.alCo = new ArrayList<Integer>();
            this.alHum = new ArrayList<Integer>();
            this.alTemp = new ArrayList<Integer>();
        }

        @Override
        public String toString() {
            return strTitle;
        }
    }
}
