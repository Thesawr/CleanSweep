package main;

public class ObstacleSensor{
    //Get floor plan reader singleton to share floor  plan

    static int floorplan [][];

    public ObstacleSensor(int floor [][]){
        ObstacleSensor.floorplan = floor;
    }


    public boolean checkObstacle(int x, int y){
        if(floorplan[x][y] == 0) return true; //Empty tile
        if(floorplan[x][y] == 1) return true; //Low-dirt tile
        if(floorplan[x][y] == 2) return true; //High-dirt tile
        if(floorplan[x][y] == 4) return true; //Open door
        if(floorplan[x][y] == 6) return true; //Charging station

        return false;
    }

    /*
    public boolean checkRight(){
        //TODO: Check if (x+1, y) is traversable

    }

    public boolean checkLeft(){
        //TODO: Check if (x-1, y) is traversable
    }

    public boolean checkUp(){
        //TODO: Check if (x, y-1) is traversable
    }

    public boolean checkDown(){
        //TODO: Check if (x, y+1) is traversable
    }
    */
}