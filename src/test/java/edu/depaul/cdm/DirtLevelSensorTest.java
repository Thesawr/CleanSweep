package edu.depaul.cdm;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class DirtLevelSensorTest {


    @Test
    public void testDirtLevelSensorWithDifferentDirtLevels(){

        //Dummy floor plan, need to check coords (1,2), (2,2), (3,2)
        int[][] dirtplan = {
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,1,0,0},
                {0,0,2,0,0},
                {0,0,3,0,0},
                {0,0,0,0,0},
        };


        //Create a sensor object
        DirtLevelSensor sensor = new DirtLevelSensor(dirtplan);

        //Test each space with dirt
        assertThat(sensor.checkDirtLevel(2,2), is(true));
        assertThat(sensor.checkDirtLevel(3,2), is(true));
        assertThat(sensor.checkDirtLevel(4,2), is(true));

        //Test spaces without dirt
        assertThat(sensor.checkDirtLevel(1,2), is(false));

        //Change values in array
        dirtplan[2][2] = 0;
        dirtplan[3][2] = 1;
        dirtplan[4][2] = 2;

        //Retest check method to ensure new values are correctly recognized
        assertThat(sensor.checkDirtLevel(2,2), is(false));
        assertThat(sensor.checkDirtLevel(3,2), is(true));
        assertThat(sensor.checkDirtLevel(4,2), is(true));


    }

}