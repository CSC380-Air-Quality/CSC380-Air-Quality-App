package com.example.mustard.airqualityapplication;

import java.util.Random;

public class DummySensor {
    private int NO2, CO, humidity, cel, kel, fahren;
    private int[] values = new int[6];
    Random r = new Random();

    /**
     * Constructor that initializes the variables to 0
     *
     * @Params none
     */
    public DummySensor(){
        NO2 = 0;
        CO = 0;
        humidity = 0;
        cel = 0;
        kel = 0;
        fahren = 0;
    }

    /**
     * Gets a random value for NO2 between 0 and 100, inclusive
     *
     * @return int
     */
    public int getNO2(){
        return NO2 = r.nextInt(101);
    }

    /**
     * Gets a random value for CO between 0 and 100, inclusive
     *
     * @return int
     */
    public int getCO(){
        return CO = r.nextInt(101);
    }

    /**
     * Gets a random value for humidity between 0 and 100, inclusive
     *
     * @return int
     */
    public int getHumidity(){
        return humidity = r.nextInt(101);
    }

    /**
     * Generates random values for Celsius between 0 and 65, converts that to other temperatures
     *
     * @param t
     * @return int
     */
    public int getTemp(char t){
        int min = 0, max = 65;
        int intReturn = 0;
        float val = r.nextFloat() * (max - min);
        cel = (int)val;
        kel = (int)(val + 273.15);
        fahren = (int)((val * 9/5) + 32);
        if (t == 'c') {
            return cel = (int) val;
        }else if (t == 'k') {
            return kel = (int) (val + 273.15);
        }else if (t == 'f') {
            return fahren = (int) ((val * 9 / 5) + 32);
        }
        return -1;
    }
}
