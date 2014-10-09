public class LocationDataPoint{
    
    private double dbXCoord;
    private double dbYCoord;
    private double dbNOlvl;
    private double dbCOlvl;
    private double dbHumidity;
    private int intTemp;
    
    
    /**
    *Constructor that sets the latitude, longitude, nitrogen dioxide level,
    *carbon monoxide level, relative humidity, and temperature of the location
    *data point.
    *
    *@Params xIn the latitude of the location
    *@Params yIn the longitude of the location
    *@Params noIn the nitrogen dioxide level
    *@Params coIn the carbon monoxide level
    *@Params humidityIn the humidity level
    *@Params tempIn the temperature
    */
    LocationDataPoint(double xIn, double yIn, double noIn, double coIn,
        double humidityIn, int tempIn){
        
        dbXCoord = xIn;
        dbYCoord = yIn;
        dbNOlvl = noIn;
        dbCOlvl = coIn;
        dbHumidity = humidityIn;
        intTemp = tempIn;
    
    }
    
    /**
    *Constructor that sets the nitrogen dioxide level, carbon monoxide level, 
    *relative humidity, and temperature of the location data point.
    *
    *@Params noIn the nitrogen dioxide level
    *@Params coIn the carbon monoxide level
    *@Params humidityIn humidityIn the humidity level
    *@Params tempIn the temperature
    */
    LocationDataPoint(double noIn, double coIn, double humidityIn, int tempIn){
        
        //The max latitude reading is 90
        //The max longitude reading is 180
        dbXCoord = 91;
        dbYCoord = 181;
        dbNOlvl = noIn;
        dbCOlvl = coIn;
        dbHumidity = humidityIn;
        intTemp = tempIn;
        
    }
    
    /**
    *Sets the latitude value for the location data point.
    *
    *@Params xIn the latitude of the location
    */
    public void setX(double xIn){
        dbXCoord = xIn;
    
    }
    
    /**
    *Returns the latitude value for the location data point.
    *
    *@return the latitude of the location data point
    */
    public double getX(){
        return dbXCoord;
        
    }
    
    /**
    *Sets the longitude value for the location data point.
    *
    *@Params yIn the longitude of the location
    */
    public void setY(double yIn){
        dbYCoord = yIn;
        
    }
    
    /**
    *Returns the longitude value for the location data point.
    *
    *@return the longitude of the location data point
    */
    public double getY(){
        return dbYCoord;
    }
    
    /**
    *Sets the nitrogen dioxide level for the location data point.
    *
    *@Params noIn the nitrogen dioxide level
    */
    public void setNo(double noIn){
        dbNOlvl = noIn;
    
    }
    
    /**
    *Returns the nitrogen dioxide level for the location data point.
    *
    *@return the nitrogen dioxide level of the location data point
    */
    public double getNo(){
        return dbNOlvl;
        
    }
    
    /**
    *Sets the carbon monoxide level for the location data point.
    *
    *@Params coIn the carbon monoxide level
    */
    public void setCo(double coIn){
        dbCOlvl = coIn;
        
    }
    
    /**
    *Returns the carbon monoxide level of the location data point.
    *
    *@return the carbon monoxide level of the location data point
    */
    public double getCo(){
        return dbCOlvl;
        
    }
    
    /**
    *Sets the humidity of the location data point.
    *
    *@Params humidityIn humidityIn the humidity level
    */
    public void setHumidity(double humidityIn){
        dbHumidity = humidityIn;
        
    }

    /**
    *Returns the relative humidity of the location data point.
    *
    *@return the relative humidity of the location data point
    */
    public double getHumidity(){
        return dbHumidity;
        
    }
    
    /**
    *Sets the temperature of the location data point.
    *
    *@Params tempIn the temperature
    */
    public void setTemp(int tempIn){
        intTemp = tempIn;
        
    }
    
    /**
    *Returns the temperature of the location data point.
    *
    *@return the temperature of the location data point
    */    
    public int getTemp(){
        return intTemp;
        
    }
}
