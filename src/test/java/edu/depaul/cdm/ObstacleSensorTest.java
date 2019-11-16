package edu.depaul.cdm;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ObstacleSensorTest {

    @Test
    public void checkObstacleSensorWithVariousObstacles() {
        //Dummy floor plan, need to check coords (1,2), (2,2), (3,2), (4,2), (5,2), (6,2), (7,2)
        int[][] floorplan = {
                {-1,-1,-1,-1,-1},
                {-1,3,3,3,-1},
                {-1,3,0,3,-1},
                {-1,3,1,3,-1},
                {-1,3,2,3,-1},
                {-1,3,4,3,-1},
                {-1,3,6,3,-1},
                {-1,3,5,3,-1},
                {-1,3,3,3,-1},
                {-1,-1,-1,-1,-1}
        };


        //Create a sensor object
        ObstacleSensor sensor = new ObstacleSensor(floorplan);

        //Test each non-obstacle type
        assertThat(sensor.checkObstacle(2,2), is(true));
        assertThat(sensor.checkObstacle(3,2), is(true));
        assertThat(sensor.checkObstacle(4,2), is(true));
        assertThat(sensor.checkObstacle(5,2), is(true));
        assertThat(sensor.checkObstacle(6,2), is(true));

        //Test each obstacle type
        assertThat(sensor.checkObstacle(1,2), is(false));
        assertThat(sensor.checkObstacle(7,2), is(false));

        //Test out-of-bounds case
        assertThat(sensor.checkObstacle(0,2), is(false));


    }

}