package com.example.mustard.airqualityapplication;

/**
 * Created by Mark Williams on 10/25/2014.
 */
public class DataQueueNode {
    private LocationDataPoint node;
    private DataQueueNode next;

    /**
     * Constructor
     * @param nodeIn LocationDataPoint for the node
     */
    public DataQueueNode(LocationDataPoint nodeIn){
        this.node = nodeIn;
        this.next = null;

    }

    /**
     * Returns the LocationDataPoint of the node
     * @return LocationDataPoint
     */
    public LocationDataPoint getNode(){
        return this.node;

    }

    /**
     * Returns a reference to the next node
     * @return DataQueueNode
     */
    public DataQueueNode getNext(){
        return this.next;

    }

    /**
     * Sets the reference for the next node
     * @param nextIn Then next node in the queue
     */
    public void setNext(DataQueueNode nextIn){
        this.next = nextIn;

    }

}
