package edu.depaul.cdm;

public class FloorTypeSensor {

    static int floorplan [][];

    public FloorTypeSensor(int floor [][]){
        this.floorplan = floor;
    }

    public FloorType checkFloorType(int x, int y){
        if (floorplan[x][y] == 2) return FloorType.HIGH;
        else if (floorplan[x][y] == 1) return FloorType.MED;
        else if (floorplan[x][y] == 0) return FloorType.LOW;
        else return FloorType.OBS;
    }

}