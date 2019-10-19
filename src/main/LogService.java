package main;

public class LogService {


    public void LogUpMovement(){
        System.out.println("CleanSweep moved up");
    }

    public void LogDownMovement(){
        System.out.println("CleanSweep moved down");
    }

    public void LogRightMovement(){
        System.out.println("CleanSweep moved right");
    }

    public void LogLeftMovement(){
        System.out.println("CleanSweep moved left");
    }

    public void LogFullBucket(){
        System.out.println("The dirt bucket is full");
    }

    public void LogEmptyBucket(){
        System.out.println("The dirt bucket has been emptied");
    }

    public void LogDepletedBattery(){
        System.out.println("The battery has run out of power");
    }

    public void LogFullBattery(){
        System.out.println("The battery is full");
    }

    public void LogCharging(){
        System.out.println("The battery is charging");
    }

    public void LogFinishedCycle(){
        System.out.println("The CleanSweep has finished cleaing");
    }

    public void LogCustom(String message){
        System.out.println(message);
    }

}
