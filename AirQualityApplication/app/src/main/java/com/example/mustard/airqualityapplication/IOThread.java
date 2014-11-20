package com.example.mustard.airqualityapplication;

/**
 * Created by Mark Williams on 10/25/2014.
 */
public class IOThread extends Thread {

    protected DataQueue queue;
    private boolean bRunning;
    private long startTime;
    private long currentTime;
    private long elapsedTime;
    private double temp1 = 0;
    private double temp2 = 0;
    private double temp3 = 0;
    private int temp4 = 0;
    private int counter = 0;
    private DummySensor ds;
    //http://stackoverflow.com/questions/924208/how-to-convert-nanoseconds-to-seconds-using-the-timeunit-enum

    /**
     *
     * @param queueIn
     */
    public IOThread(DataQueue queueIn){
        this.queue = queueIn;
        this.bRunning = true;
        this.startTime = System.nanoTime();
        this.currentTime = 0;
        this.elapsedTime = 0;
        ds = new DummySensor();
        //System.out.println("IOThread: Thread created");
    }


    /**
     * NEVER CALL THIS METHOD ON THE THREAD!
     * call thread.start();
     * if you ever call this I will find you...
     */
    public void run(){
        DummySensor dummySensor = new DummySensor();
        while(bRunning){
            try{
                //This is how we control how often we generate a new data point
                this.sleep(1000);
            }catch(InterruptedException ie){

            }

            DataQueueNode temp = new DataQueueNode(new LocationDataPoint(ds.getNO2(),ds.getCO(),ds.getHumidity(),ds.getTemp('f')));
            queue.enqueue(temp);
            temp1 ++;
            temp2 ++;
            temp3 ++;
            temp4 ++;
            counter ++;
            if(counter > 100){
                this.halt();
            }

            /*this.currentTime = System.nanoTime();
            this.elapsedTime = currentTime - startTime;
            double seconds = (double) elapsedTime / 1000000000.0;

            if(seconds > 180){
                this.halt();
                //System.out.println("Nodes Left: " + this.queue.size());
            }*/

        }
    }

    /**
     * This is used to kindly ask the thread to kill itself
     */
    public void halt(){
        this.bRunning = false;
    }
}
