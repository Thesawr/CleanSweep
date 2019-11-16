package edu.depaul.cdm;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class PowerManagementTest {

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException{
        Field instance = PowerManagement.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null,null);
    }

    /*
    @Test
    public void testAverageCost(){

        //Tests for correct math
        PowerManagement power = PowerManagement.getInstance();
        assertThat(power.average_cost(0,0),equalTo(0));
        assertThat(power.average_cost(0,1),equalTo(0.5));
        assertThat(1.5,equalTo(power.average_cost(1,2)));
        assertThat(1,equalTo(power.average_cost(0,2)));

    }*/

    @Test
    public void testUpdatingThresholdAndLowPowerFlag(){

        PowerManagement power = PowerManagement.getInstance();

        //Check that threshold is updated and lowPower flag is not set
        power.updateThreshold(50);
        assertThat(power.getPowerThreshold(), equalTo(50.0));
        assertThat(power.lowPowerAlert(), is(not(true)));

        //Check that threshold is updated and lowPower flag is set
        power.updateThreshold(175);
        assertThat(power.getPowerThreshold(), equalTo(225.0));
        assertThat(power.lowPowerAlert(), is(true));
    }


    @Test
    public void testVacuumingOnDifferentFloorTypes(){

        int[][] dummyFloor = new int[][] {
                {-1,-1,-1,-1,-1},
                {-1,3,3,3,-1},
                {-1,3,0,3,-1},
                {-1,3,1,3,-1},
                {-1,3,2,3,-1},
                {-1,3,3,3,-1},
                {-1,-1,-1,-1,-1}
        };

        PowerManagement power = PowerManagement.getInstance();
        power.set_floor(dummyFloor);

        //Check initial threshold
        assertThat(power.getPowerThreshold(), equalTo(0.0));

        //Vacuum Different Floor types and check that threshold is updated correctly
        power.vacuum(2,2);
        assertThat(power.getPowerThreshold(), equalTo(1.0));
        power.vacuum(3,2);
        assertThat(power.getPowerThreshold(), equalTo(3.0));
        power.vacuum(4,2);
        assertThat(power.getPowerThreshold(), equalTo(6.0));


    }


}
