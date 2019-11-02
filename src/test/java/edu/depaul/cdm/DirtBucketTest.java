package edu.depaul.cdm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Random;

import static org.junit.Assert.*;

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
        assertEquals(50,bucket.getCapacity());

        //Vacuum One unit of dirt
        bucket.vacuumDirt();

        //Check that capacity is one less
        assertEquals(49,bucket.getCapacity());
    }


    @Test
    public void testVacuumingMultipleTimes(){

        //Get instance of DirtBucket
        bucket = DirtBucket.getInstance();

        //Check that the bucket is empty
        assertEquals(50,bucket.getCapacity());

        //Make multiple cleaning operations
        for (int i = 0; i < 10; i++){
            bucket.vacuumDirt();
        }

        //Check that the capacity is 10 less
        assertEquals(40, bucket.getCapacity());
    }

    @Test
    public void checkBucketFullWhenNotFull(){

        //Get instance of DirtBucket
        bucket = DirtBucket.getInstance();

        //Check that the bucket is empty
        assertEquals(50,bucket.getCapacity());

        //Check that the bucket is not considered full
        assertFalse(bucket.bucketFull());
    }

    @Test
    public void checkBucketFullWhenBucketIsFull(){

        //Get instance of DirtBucket
        bucket = DirtBucket.getInstance();

        //Check that the bucket is empty
        assertEquals(50,bucket.getCapacity());

        //Fill bucket until there is no capacity
        for (int i = 0; i < 50; i++){
            bucket.vacuumDirt();
        }

        //Check that the bucket is considered full
        assertTrue(bucket.bucketFull());
    }

    @Test
    public void checkEmptyBucketReturnsCapacityTo50FromMultipleLevelsOfFilled(){

        //Get instance of DirtBucket
        bucket = DirtBucket.getInstance();

        //Check that the bucket is empty
        assertEquals(50,bucket.getCapacity());

        //Empty the bucket
        bucket.emptyBucket();

        //Check that the capacity has not changed
        assertEquals(50,bucket.getCapacity());

        //Fill the bucket with 25 units of dirt
        for (int i = 0; i < 25; i++){
            bucket.vacuumDirt();
        }

        //Check that the bucket is half full
        assertEquals(25, bucket.getCapacity());

        //Empty the bucket
        bucket.emptyBucket();

        //Check that the capacity has been reset to 50
        assertEquals(50, bucket.getCapacity());

        //Fill the bucket completely
        for (int i = 0; i < 50; i++){
            bucket.vacuumDirt();
        }

        //Check that the bucket is full
        assertTrue(bucket.bucketFull());

        //Empty the bucket
        bucket.emptyBucket();

        //Check that the capacity has been reset to 50
        assertEquals(50, bucket.getCapacity());
    }



}