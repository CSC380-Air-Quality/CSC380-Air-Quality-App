package com.example.mustard.airqualityapplication;

/**
 * Created by Mark Williams on 10/25/2014.
 */
public class DataQueueThread extends Thread {

    protected Session mainSession;
    protected Graph mainGraph;
    private DataQueue queue;
    private LocationDataPoint tempData;
    private boolean bRunning;
    private long startTime;
    private long currentTime;
    private long elapsedTime;
    private int counter = 0;
    //http://stackoverflow.com/questions/924208/how-to-convert-nanoseconds-to-seconds-using-the-timeunit-enum

    /**
     *
     * @param sessionIn
     * @param graphIn
     * @param queueIn
     */
    public DataQueueThread(Session sessionIn, Graph graphIn, DataQueue queueIn){
        this.mainSession = sessionIn;
        this.mainGraph = graphIn;
        this.queue = queueIn;
        this.bRunning = true;
        this.startTime = System.nanoTime();
        this.currentTime = 0;
        this.elapsedTime = 0;

    }

    /**
     * NEVER CALL THIS METHOD ON THE THREAD!
     * call thread.start();
     * if you ever call this I will find you...
     */
    public void run(){

        while(bRunning){
            if(!(queue.isEmpty())){
                tempData = queue.dequeueLDP();
                mainSession.giveDataPoint(tempData);
                mainGraph.giveData(tempData);
            }

            try{
                this.sleep(1000);
            }catch(InterruptedException ie){

            }
            counter ++;
            if(counter > 100){
                this.halt();
                System.err.println("ALL DONE!!1!!!!!!");
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
        bRunning = false;
    }

}
