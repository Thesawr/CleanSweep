package main;

import java.awt.Point;
import java.util.*;

/**
 * 
 * @author hamood
 * MoveRobot is responsible for all the movement. 
 * MoveRobot class takes in three arguments as follows: 
 * 		1. 2D-Array for the Floor Plan
 * 		2. x - coordinate of a row  
 * 		3. y - coordinate of a column  
 *		4. 2D-Array for the Dirt Level
 *   **Currently the length and the width of the room is assumed same i.e. length of the 2-D floor Array.
 *   **Will be Adding a Backtracking Feature
 *   **The x and y coordinates will be fetched from Locator
 *   **And after every move the Locator would be passed on the current coordinates.
 *   
 */

public class MoveRobot {
	static private int[][] floor;
	static private int[][] dirtFloor;
	private static int x;
	private static int y;
	static int nextX;
	static int nextY;
	static int colLength;
	static int rowLength;
	static int floorLength = colLength * rowLength;
	static Stack<Point> trail = new Stack<Point>(); //Robots Trail
	static HashSet<Point> visited = new HashSet<Point>(); //Visited Floor Units
	
	
	
	public MoveRobot (int[][] floor, int yCord, int xCord, int[][]dirtFloor){
		
		//Dirt level 2D Array 
		MoveRobot.floor = floor;
		MoveRobot.y = yCord;
		MoveRobot.x = xCord;
		MoveRobot.colLength = floor[0].length;
		MoveRobot.rowLength = floor.length;
		MoveRobot.dirtFloor = dirtFloor;
	}
	
	//Gives the Current Coordinates (might not be called)
	public int[] getCords(){
		int[] cords = {x, y};
		return cords;
	}

	
	private static boolean safePath(){
		
		//TODO : Check for Obstacle by calling Obstacle Sensor in each Check clockwise
		//1st Priority to Move rightward
		if (x+1 >=0 && x <colLength && !visited.contains(new Point(x, y)))
		{ nextY = y; nextX = x+1; return true; }
	
		//Clockwise Priority from here onward: 
		else if (y+1 >=0 && y <rowLength && !visited.contains(new Point(x, y)))
		{ nextY = y+1; nextX = x; return true; }
		else if (x-1 >=0 && x <colLength && !visited.contains(new Point(x, y)))
		{ nextY = y; nextX = x-1; return true; }
		else if (y-1 >=0 && y <rowLength && !visited.contains(new Point(x, y)))
		{ nextY = y-1; nextX = x; return true; }
		
		else{ return false; }
	}
	
	private static void backTrack(){
		trail.pop();
		x = trail.peek().x;
		y = trail.peek().y;
	}
	
	public void move(){
		
		Locator locator = new Locator();
		
		while (visited.size() < floorLength)	{
			//if it is safe to Traverse:
			if (safePath()){
				visited.add(new Point(nextX, nextY));
				locator.setX(nextX);
				locator.setY(nextY);
				trail.push(new Point(nextX, nextY));
				// TODO : Dirt Check
				// TODO : If not Dirty than Continue to next
			}
			else {
				backTrack(); //pops the last element and assigns the 2nd last coordinates to x and y
			}			
		}
	}

}
