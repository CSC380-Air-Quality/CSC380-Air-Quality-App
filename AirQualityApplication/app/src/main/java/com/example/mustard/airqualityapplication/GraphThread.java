package com.example.mustard.airqualityapplication;

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
public class GraphThread extends Thread {
    protected Graph mainGraph;
    private boolean bRunning;
    private long startTime;
    private long currentTime;
    private long elapsedTime;

    /**
     *
     * @param graphIn
     */
    public GraphThread(Graph graphIn){
        this.mainGraph = graphIn;
        bRunning = true;
    }


    /**
     * NEVER CALL THIS METHOD ON THE THREAD!
     * call thread.start();
     * if you ever call this I will find you...
     */
    public void run(){
        while(bRunning){
            try{
                this.sleep(4000);
            }catch(InterruptedException ie){

            }

            if(this.mainGraph.hasNewData()){
                //graph some shit
            }

        }
    }


    /**
     * This is used to kindly ask the thread to kill itself
     */
    public void halt(){
        bRunning = false;
    }
}
