package edu.depaul.cdm;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class DirtBucketTest {

    private DirtBucket bucket;

    @Before
    public void setup() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        Field instance = DirtBucket.class.getDeclaredField("dirtBucket");
        instance.setAccessible(true);
        instance.set(null,null);
    }


    @Test
    public void testVacuumingOneUnitOfDirt(){

        //Get instance of DirtBucket
        bucket = DirtBucket.getInstance();

        //Check that the bucket is empty
        assertThat(bucket.getCapacity(), equalTo(50));

        //Vacuum One unit of dirt
        bucket.vacuumDirt();

        //Check that capacity is one less
        assertThat(bucket.getCapacity(), equalTo(49));
    }


    @Test
    public void testVacuumingMultipleTimes(){

        //Get instance of DirtBucket
        bucket = DirtBucket.getInstance();

        //Check that the bucket is empty
        assertThat(bucket.getCapacity(), equalTo(50));

        //Make multiple cleaning operations
        for (int i = 0; i < 10; i++){
            bucket.vacuumDirt();
        }

        //Check that the capacity is 10 less
        assertThat(bucket.getCapacity(), equalTo(40));
    }

    @Test
    public void checkBucketFullWhenNotFull(){

        //Get instance of DirtBucket
        bucket = DirtBucket.getInstance();

        //Check that the bucket is empty
        assertThat(bucket.getCapacity(), equalTo(50));

        //Check that the bucket is not considered full
        assertFalse(bucket.bucketFull());
    }

    @Test
    public void checkBucketFullWhenBucketIsFull(){

        //Get instance of DirtBucket
        bucket = DirtBucket.getInstance();

        //Check that the bucket is empty
        assertThat(bucket.getCapacity(), equalTo(50));

        //Fill bucket until there is no capacity
        for (int i = 0; i < 50; i++){
            bucket.vacuumDirt();
        }

        //Check that the bucket is considered full
        assertThat(bucket.bucketFull(), is(true));
    }

    @Test
    public void checkEmptyBucketReturnsCapacityTo50FromMultipleLevelsOfFilled(){

        //Get instance of DirtBucket
        bucket = DirtBucket.getInstance();

        //Check that the bucket is empty
        assertThat(bucket.getCapacity(), equalTo(50));

        //Empty the bucket
        bucket.emptyBucket();

        //Check that the capacity has not changed
        assertThat(bucket.getCapacity(), equalTo(50));

        //Fill the bucket with 25 units of dirt
        for (int i = 0; i < 25; i++){
            bucket.vacuumDirt();
        }

        //Check that the bucket is half full
        assertThat(bucket.getCapacity(), equalTo(25));

        //Empty the bucket
        bucket.emptyBucket();

        //Check that the capacity has been reset to 50
        assertThat(bucket.getCapacity(), equalTo(50));

        //Fill the bucket completely
        for (int i = 0; i < 50; i++){
            bucket.vacuumDirt();
        }

        //Check that the bucket is full
        assertThat(bucket.bucketFull(), is(true));

        //Empty the bucket
        bucket.emptyBucket();

        //Check that the capacity has been reset to 50
        assertThat(bucket.getCapacity(), equalTo(50));
    }



}