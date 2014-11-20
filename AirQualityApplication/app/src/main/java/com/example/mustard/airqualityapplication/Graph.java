package com.example.mustard.airqualityapplication;

import java.util.ArrayList;

/**
 * Created by Mark Williams on 10/25/2014.
 */
public class Graph {
    private ArrayList<LocationDataPoint> alData;
    private boolean bNewData;
    /**
     * Constructor
     */
    public Graph(){
        this.alData = new ArrayList<LocationDataPoint>();
        this.bNewData = false;
    }

    /**
     * Adds a LocationDataPoint to the alData ArrayList
     * @param dataPointIn the new LocationDataPoint
     */
    public synchronized void giveData(LocationDataPoint dataPointIn){
        this.alData.add(dataPointIn);
        this.bNewData = true;
        //System.out.println("Grid: Data Received");

    }

    /**
     * Asks the session if it has new data
     * @return boolean
     */
    public synchronized boolean hasNewData(){
        return this.bNewData;
    }

}
