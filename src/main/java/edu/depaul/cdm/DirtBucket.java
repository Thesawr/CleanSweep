package edu.depaul.cdm;

public class DirtBucket {

    private int capacity;
    private static DirtBucket dirtBucket;
    private static LogService logService;

    private DirtBucket(){
        capacity = 50;
    }

    public static DirtBucket getInstance(){
        if (dirtBucket == null){
            dirtBucket = new DirtBucket();
            logService = LogService.getInstance();
        }
        return dirtBucket;
    }

    public boolean bucketFull(){
        logService.logFullBucket();
        return (capacity == 0) ? true : false;
    }

    public void vacuumDirt(){
        if (capacity > 0 ){
            capacity -= 1;
        }
    }

    public void emptyBucket(){
        capacity = 50;
        logService.logEmptyBucket();
    }

    public int getCapacity(){
        return capacity;
    }

}