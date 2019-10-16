package main;

import java.awt.Point;
import java.util.HashSet;

/**
 * 
 * @author hamood
 * MoveRobot is responsible for all the movement. 
 * MoveRobot class takes in three arguments as follows: 
 * 		1. 2D-Array i.e. the Floor Plan
 * 		2. x - coordinate of a row  
 * 		3. y - coordinate of a column  
 * 
 *   **Currently the length and the width of the room is assumed same i.e. length of the 2-D floor Array.
 *   **Will be Adding a Backtracking Feature
 *   **The x and y coordinates will be fetched from Locator
 *   **And after every move the Locator would be passed on the current coordinates.
 *   
 */

public class MoveRobot {
	static private int[][] floor;
	private static int x;
	private static int y;
	static int nextX;
	static int nextY;
	static int length;
	static int[] newMove = new int[2];	
	//trail stack
	HashSet<Point> visited = new HashSet<Point>(); //visited set
	//Take the Row length (this will be consistent)
	
	public MoveRobot (int[][] floor, int yCord, int xCord){
		
		//Dirt level 2D Array 
		MoveRobot.floor = floor;
		MoveRobot.y = yCord;
		MoveRobot.x = xCord;
		MoveRobot.length = floor.length;
	}
	
	//Getter and Setters
	public int[] getCords(){
		int[] cords = {x, y};
		return cords;
	}

	
	private static boolean safePath(){
		
		//Check for Obstacle by calling Obstacle Sensor in each Check clockwise : John Wolf
		//
		//1st Priority to Move rightward
		if (x+1 >=0 && x <length){ nextY = y; nextX = x+1; return true; }
		
		//Clockwise Priority from here onward: 
		else if (y+1 >=0 && y <length){ nextY = y+1; nextX = x; return true; }
		else if (x-1 >=0 && x <length){ nextY = y; nextX = x-1; return true; }
		else if (y-1 >=0 && y <length){	nextY = y-1; nextX = x; return true; }
		
		else{ return false; }
	}
	
	public int[] move(){
		
		//if it is safe to Traverse:
		if (safePath()){
			//Conditions before moving
			//1st Condition: if Dirty
			
			//Move based on Priority
			newMove[0] = nextY;
			newMove[1] = nextX;
			//Call the locator on every move
		}
		return newMove;
	}



}
