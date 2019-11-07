package edu.depaul.cdm;

public class DirtLevelSensor
{
    static int floorplan [][];

    public DirtLevelSensor(int floor [][]){
        DirtLevelSensor.floorplan = floor;
    }

    public boolean checkDirtLevel(int x, int y){
        if(DirtLevelSensor.floorplan[x][y] == 0) return false; //No dirt
        if(DirtLevelSensor.floorplan[x][y] == 1) return true; //Low-dirt level
        if(DirtLevelSensor.floorplan[x][y] == 2) return true; //Med-dirt level
        if(DirtLevelSensor.floorplan[x][y] == 3) return true; //High-dirt level

        return false;
    }
}
