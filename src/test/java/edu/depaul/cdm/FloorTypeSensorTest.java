package edu.depaul.cdm;

import org.junit.Test;

import static org.junit.Assert.*;

public class FloorTypeSensorTest {

    @Test
    public void checkFloorType() {

        //Dummy floor plan, need to check coords (2,2), (3,2), (4,2)
        int[][] floorplan = {
                {-1,-1,-1,-1,-1},
                {-1,3,3,3,-1},
                {-1,3,0,3,-1},
                {-1,3,1,3,-1},
                {-1,3,2,3,-1},
                {-1,3,3,3,-1},
                {-1,-1,-1,-1,-1}
        };

        //Create a sensor object
        FloorTypeSensor sensor = new FloorTypeSensor(floorplan);

        //Test each floor type
        assertEquals(FloorType.LOW, sensor.checkFloorType(2,2));
        assertEquals(FloorType.MED, sensor.checkFloorType(3,2));
        assertEquals(FloorType.HIGH, sensor.checkFloorType(4,2));

        //Test that walls are considered OBS (Obstacle) type
        assertEquals(FloorType.OBS , sensor.checkFloorType(1,1));

    }
}