package com.example.mustard.airqualityapplication;

/**
 * Created by BlackHawk31 on 10/26/2014.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.Vector;

public class DataHandler extends SQLiteOpenHelper
{
    private static final String DB_NAME = "airQuality.db";
    private static final String SESSION_TABLE = "session";
    private static final String LOCATION_TABLE = "location";
    private static final String AIR_SAMPLE_TABLE = "airSample";
    private static final String SESSION_DATA_TABLE = "sessionData";
    private static final String SETTINGS_TABLE = "sessionData";
    private static final String NAME_COLUMN = "name";
    private static final String SETTING1_COLUMN = "setting1";
    private static final String SETTING2_COLUMN = "setting2";
    private static final String SETTING3_COLUMN = "setting3";
    private static final String SETTING4_COLUMN = "setting4";
    private static final String ID_COLUMN = "id";
    private static final String START_TIME_COLUMN = "startTime";
    private static final String END_TIME_COLUMN = "endTime";
    private static final String NOTES = "notes";
    private static final String LOCATION_ID_COLUMN = "locationId";
    private static final String AIR_SAMPLE_ID_COLUMN = "airSampleId";
    private static final String STREET_COLUMN = "street";
    private static final String CITY_COLUMN = "city";
    private static final String ZIP_CODE_COLUMN = "zipCode";
    private static final String X_AXIS_COLUMN = "xAxis";
    private static final String Y_AXIS_COLUMN = "yAxis";
    private static final String COUNTRY_COLUMN = "country";
    private static final String CO_COLUMN = "co";
    private static final String NO2_COLUMN = "no2";
    private static final String HUMIDITY_COLUMN = "humidity";
    private static final String PARTICLES_COLUMN = "particles";
    private static final String TEMPERATURE_COLUMN = "temp";
    private static final String CREATE_TABLE= "CREATE TABLE";
    private static final String INSERT = "INSERT INTO";
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET=");";
    private static final String _PRIM_KEY = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String _INT = "INTEGER";
    private static final String _STR = "STRING";
    private static final String _DOUBLE = "DOUBLE";
    private static final String _BOOLEAN= "BOOLEAN";
    private static final String _COMMA =",";
    private static final String VALUES = ") VALUES (";




    public DataHandler(Context context)
    {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("On Create Called");
        try {
            db.execSQL(CREATE_TABLE + " " + SESSION_TABLE + " " + LEFT_BRACKET + " " + ID_COLUMN + " " + _PRIM_KEY + " " + _COMMA
                    + " " + START_TIME_COLUMN + " " + _DOUBLE + " " + _COMMA
                    + " " + END_TIME_COLUMN + " " + _DOUBLE + " " + _COMMA
                    + " " + NOTES + " " + _STR + " " + RIGHT_BRACKET);
            System.out.println("debug code 1");
            db.execSQL(CREATE_TABLE + " " + AIR_SAMPLE_TABLE + " " + LEFT_BRACKET + " " + ID_COLUMN + " " + _PRIM_KEY + " " + _COMMA
                    + " " + CO_COLUMN + " " + _DOUBLE + " " + _COMMA
                    + " " + NO2_COLUMN + " " + _DOUBLE + " " + _COMMA
                    + " " + HUMIDITY_COLUMN + " " + _DOUBLE + " " + _COMMA
                    + " " + PARTICLES_COLUMN + " " + _DOUBLE + " " + _COMMA
                    + " " + TEMPERATURE_COLUMN + " " + _DOUBLE + " " + RIGHT_BRACKET);
            System.out.println("debug code 2");
            db.execSQL(CREATE_TABLE + " " + LOCATION_TABLE + " " + LEFT_BRACKET + " " + ID_COLUMN + " " + _PRIM_KEY + " " + _COMMA
                    + " " + STREET_COLUMN + " " + _STR + " " + _COMMA
                    + " " + CITY_COLUMN + " " + _STR + " " + _COMMA
                    + " " + ZIP_CODE_COLUMN + " " + _STR + " " + _COMMA
                    + " " + X_AXIS_COLUMN + " " + _STR + " " + _COMMA
                    + " " + Y_AXIS_COLUMN + " " + _STR + " " + _COMMA
                    + " " + COUNTRY_COLUMN + " " + _STR + " " + RIGHT_BRACKET);
            System.out.println("debug code 3");
            db.execSQL(CREATE_TABLE + " " + SESSION_DATA_TABLE + " " + LEFT_BRACKET + " " + ID_COLUMN + " " + _INT + " " + _COMMA
                    + " sessionID " + _INT + " " + _COMMA
                    + " " + LOCATION_ID_COLUMN + " " + _INT + " " + _COMMA
                    + " " + AIR_SAMPLE_ID_COLUMN + " " + _INT + " " + RIGHT_BRACKET);
            System.out.println("debug code 4");
            /*db.execSQL(CREATE_TABLE + " " + SETTINGS_TABLE + " " + LEFT_BRACKET + " " + ID_COLUMN + " " + _PRIM_KEY + " " + _COMMA
                    + " " + NAME_COLUMN + " " + _STR + " " + _COMMA
                    + " " + SETTING1_COLUMN + " " + _BOOLEAN + " " + _COMMA
                    + " " + SETTING2_COLUMN + " " + _BOOLEAN + " " + _COMMA
                    + " " + SETTING3_COLUMN + " " + _BOOLEAN + " " + _COMMA
                    + " " + SETTING4_COLUMN + " " + _BOOLEAN + " " + RIGHT_BRACKET);*/
            System.out.println("debug code 5");
        }catch (SQLiteException e)
        {
            e.printStackTrace();
           e.getLocalizedMessage();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2)
    {

    }


    public int startTime(double start)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(INSERT+SESSION_TABLE+LEFT_BRACKET+START_TIME_COLUMN+VALUES+ start+RIGHT_BRACKET);
        int id =0;
        return id;

    }
    public void endTime(double end)
    {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(INSERT + SESSION_TABLE + LEFT_BRACKET + END_TIME_COLUMN + VALUES + end + RIGHT_BRACKET);

    }

    public void addNote(String note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(INSERT+SESSION_TABLE+LEFT_BRACKET+NOTES+VALUES+note+RIGHT_BRACKET);
    }

    public int addLocation(String street, String zip, String country)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(INSERT+LOCATION_TABLE+LEFT_BRACKET+STREET_COLUMN+ZIP_CODE_COLUMN+COUNTRY_COLUMN+VALUES+street+zip+country+RIGHT_BRACKET);
        int id =0;
        return id;
    }

    public int addLocation(double x, double y)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(INSERT+LOCATION_TABLE+LEFT_BRACKET+X_AXIS_COLUMN+Y_AXIS_COLUMN+VALUES+x+y+RIGHT_BRACKET);
        int id =0;
        return id;
    }

    public void modifyLocation(int id, String street,String city, String zip, String country)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(INSERT+LOCATION_TABLE+LEFT_BRACKET+STREET_COLUMN+CITY_COLUMN+ZIP_CODE_COLUMN+COUNTRY_COLUMN+VALUES+street+city+zip+country+RIGHT_BRACKET);
    }

    public void modifyLocation(int id, double x, double y)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(INSERT+LOCATION_TABLE+LEFT_BRACKET+X_AXIS_COLUMN+Y_AXIS_COLUMN+VALUES+x+y+RIGHT_BRACKET);
    }

    public int addAirSample(double co, double no2, double temp, double particle, double humidity)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(INSERT+AIR_SAMPLE_TABLE+LEFT_BRACKET
                +CO_COLUMN+NO2_COLUMN+TEMPERATURE_COLUMN+HUMIDITY_COLUMN+PARTICLES_COLUMN
                +VALUES+co+no2+temp+humidity+particle+RIGHT_BRACKET);
        int id =0;
        return id;
    }

    public void addSessionData(int session, int locationID, int airSampleID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(INSERT+SESSION_DATA_TABLE+LEFT_BRACKET+ID_COLUMN+LOCATION_ID_COLUMN+AIR_SAMPLE_ID_COLUMN+VALUES+session+locationID+airSampleID+RIGHT_BRACKET);
    }
}
