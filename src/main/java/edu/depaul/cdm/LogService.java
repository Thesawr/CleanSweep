package main.java.edu.depaul.cdm;

import java.io.*;

public class LogService {

    private static LogService logService;
    private File file;

    private LogService(){
        file = new File("log.txt");
        try{
            FileWriter writer = new FileWriter(file);
            writer.write("");
        } catch (IOException ie){
            System.out.println(ie);
        }
    }

    public static LogService getInstance(){
        if (logService == null){
            return new LogService();
        }
        return logService;
    }

    private void writeToFile(String message){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
            writer.write(message+"\n");
            writer.close();
        } catch (IOException ie){
            System.out.println(ie);
        }
    }

    public void logUpMovement(){
        writeToFile("CleanSweep moved up");
        System.out.println("CleanSweep moved up");
    }

    public void logDownMovement(){
        writeToFile("CleanSweep moved down");
        System.out.println("CleanSweep moved down");
    }

    public void logRightMovement(){
        writeToFile("CleanSweep moved right");
        System.out.println("CleanSweep moved right");
    }

    public void logLeftMovement(){
        writeToFile("CleanSweep moved left");
        System.out.println("CleanSweep moved left");
    }

    public void logFullBucket(){
        writeToFile("The dirt bucket is full");
        System.out.println("The dirt bucket is full");
    }

    public void logEmptyBucket(){
        writeToFile("The dirt bucket has been emptied");
        System.out.println("The dirt bucket has been emptied");
    }

    public void logDepletedBattery(){
        writeToFile("The battery has run out of power");
        System.out.println("The battery has run out of power");
    }

    public void logFullBattery(){
        writeToFile("The battery is full");
        System.out.println("The battery is full");
    }

    public void logCharging(){
        writeToFile("The battery is charging");
        System.out.println("The battery is charging");
    }

    public void logFinishedCycle(){
        writeToFile("The CleanSweep has finished cleaning");
        System.out.println("The CleanSweep has finished cleaning");
    }

    public void logCustom(String message){
        writeToFile(message);
        System.out.println(message);
    }

}
