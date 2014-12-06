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
public class Note {
    private String noteBody;
    private int intSessionID;

    /**
     *
     */
    public Note(){
        this.noteBody = "";
    }


    /**
     *
     * @param strIn
     */
    public Note(String strIn){
        this.noteBody = strIn;
    }


    /**
     *
     * @param strIn
     */
    public void addToNote(String strIn){
       this.noteBody += strIn;
    }

    /**
     *
     * @return
     */
    public String getNoteBody(){
        return this.noteBody;
    }

    /**
     *
     * @param sessionIdIn
     */
    public void setSessionID(int sessionIdIn){
        this.intSessionID = sessionIdIn;
    }

    /**
     *
     * @return
     */
    public int getSessionID(){
        return this.intSessionID;
    }



}
