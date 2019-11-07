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
        return (capacity <= 0) ? true : false;
    }

    public void vacuumDirt(){
        capacity--;
    }

    public void emptyBucket(){
        capacity = 0;
        logService.logEmptyBucket();
    }

    public int getCapacity(){
        return capacity;
    }

}