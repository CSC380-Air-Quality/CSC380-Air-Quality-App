package com.example.mustard.airqualityapplication;

import java.util.ArrayList;

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
public class DataQueue {
    //private ArrayList<LocationDataPoint> queue = new ArrayList<LocationDataPoint>;

    private DataQueueNode head;
    private DataQueueNode tail;
    private int intSize;

    /**
     * Empty Constructor
     */
    public DataQueue(){
        this.head = null;
        this.tail = null;
        this.intSize = 0;

    }

    /**
     * Constructor that sets the first node using a LocatioNDataPoint
     * @param nodeIn
     */
    public DataQueue(LocationDataPoint nodeIn){
        this.head = new DataQueueNode(nodeIn);
        this.tail = this.head;
        this.intSize = 0;

    }

    /**
     * Constructor that sets the first node using a DataQueueNode
     * @param nodeIn
     */
    public DataQueue(DataQueueNode nodeIn){
        this.head = nodeIn;
        this.tail = head;
        this.intSize = 0;

    }

    /**
     * Added an item to the queue as the tail
     * @param nodeIn
     */
    public synchronized void enqueue(DataQueueNode nodeIn){
        if(head == null){
            head = nodeIn;
            tail = nodeIn;
        }else{
            tail.setNext(nodeIn);
            tail = tail.getNext();

        }
        this.intSize ++;
    }

    /**
     * Removes an item from the queue and returns it to the caller
     * @return DataQueueNode
     */
    public synchronized DataQueueNode dequeue(){
        DataQueueNode tempNode = head;
        head = head.getNext();
        this.intSize --;
        return tempNode;

    }

    /**
     * Removes an item from the queue and returns it to the caller
     * @return LocationDataPoint
     */
    public synchronized LocationDataPoint dequeueLDP(){
        DataQueueNode tempNode = head;
        head = head.getNext();
        this.intSize --;
        return tempNode.getNode();

    }

    /**
     * Return a reference to the head node
     * @return DataQueueNode
     */
    public synchronized DataQueueNode first(){
        return this.head;

    }

    /**
     * Returns a reference to the tail node
     * @return DataQueueNode
     */
    public synchronized DataQueueNode last(){
        return this.tail;

    }

    /**
     * Checks if the queue is empty
     * @return boolean
     */
    public synchronized boolean isEmpty(){
        return (this.head == null);

    }

    public synchronized int size(){
        return this.intSize;
    }

}
