

public class LocationDataPoint{
    
    private double dbXCoord;
    private double dbYCoord;
    private double dbNOlvl;
    private double dbCOlvl;
    private double dbHumidity;
    private int intTemp;
    
    
    /**
    *
    *@Params xIn 
    *@Params yIn
    *@Params noIn
    *@Params coIn
    *@Params humidityIn
    *@Params tempIn
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
    *
    *@Params noIn
    *@Params coIn
    *@Params humidityIn
    *@Params tempIn
    */
    LocationDataPoint(double noIn, double coIn, double humidityIn, int tempIn){
        
        dbNOlvl = noIn;
        dbCOlvl = coIn;
        dbHumidity = humidityIn;
        intTemp = tempIn;
        
    }
    
    /**
    *
    */
    public void setX(double xIn){
        dbXCoord = xIn;
    
    }
    
    /**
    *
    */
    public double getX(){
        return dbXCoord;
        
    }
    
    /**
    *
    */
    public void setY(double yIn){
        dbYCoord = yIn;
        
    }
    
    /**
    *
    */
    public double getY(){
        return dbYCoord;
    }
    
    /**
    *
    */
    public void setNo(double noIn){
        dbNOlvl = noIn;
    
    }
    
    /**
    *
    */
    public double getNo(){
        return dbNOlvl;
        
    }
    
    /**
    *
    */
    public void setCo(double coIn){
        dbCOlvl = coIn;
        
    }
    
    /**
    *
    */
    public double getCo(){
        return dbCOlvl;
        
    }
    
    /**
    *
    */
    public void setHumidity(double humidityIn){
        dbHumidity = humidityIn;
        
    }

    /**
    *
    */
    public double getHumidity(){
        return dbHumidity;
        
    }
    
    /**
    *
    */
    public void setTemp(int tempIn){
        intTemp = tempIn;
        
    }
    
    /**
    *
    */    
    public int getTemp(){
        return intTemp;
        
    }
}