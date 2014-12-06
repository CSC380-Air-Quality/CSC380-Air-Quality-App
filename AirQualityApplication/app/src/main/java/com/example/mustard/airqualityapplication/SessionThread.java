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
public class SessionThread extends Thread {
    protected Session mainSession;
    private boolean bRunning;
    private long startTime;
    private long currentTime;
    private long elapsedTime;
    private int counter = 0;

    /**
     *
     * @param sessionIn
     */
    public SessionThread(Session sessionIn){
        this.mainSession = sessionIn;
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
                this.sleep(500);
            }catch(InterruptedException ie){

            }
            /*
            if(this.mainSession.hasNewData()){
                //do some shit
            }*/
            mainSession.updateTextViews();
            counter ++;
            if(counter > 200){
                this.halt();
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
