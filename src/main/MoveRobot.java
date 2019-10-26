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
 * 
 * The Robot Backtracks if it reaches a space where it cannot go further and there are still some units left to be traversed. 
 * The initial coordinates for x and y will be fetched from Locator
 * And after every successful move the Locator would be passed on the current coordinates.
 *   
 */

public class MoveRobot {
	
	static private int[][] floor;
	static private int[][] dirtFloor;
	private static int x;
	private static int y;
	static int colLength;
	static int rowLength;
	static int floorLength;  //The complete number of units for the Floor Plan 
	static Stack<Point> trail = new Stack<Point>(); //Robots Trail
	static HashSet<Point> visited = new HashSet<Point>(); //Visited Floor Units
	
	public MoveRobot (int[][] floor, int xCord, int yCord){  
		MoveRobot.floor = floor;
		MoveRobot.y = yCord;
		MoveRobot.x = xCord;
		MoveRobot.colLength = floor[0].length;
		MoveRobot.rowLength = floor.length;
		MoveRobot.floorLength = colLength * rowLength; 
		System.out.println("Floor Length: " + floorLength);
	}
	
	//Gives the Current Coordinates (might not be called)
	public int[] getCords(){
		int[] cords = {x, y};
		return cords;
	}

	private static boolean safePath(){	
		//1st Priority to Move rightward
		if (x+1 >=0 && x+1 <colLength && !visited.contains(new Point(x+1, y)))
		{ x++; return true; }
		//Clockwise Priority from here onward: 
		else if (y+1 >=0 && y+1 <rowLength && !visited.contains(new Point(x, y+1))) 
		{ y++; return true; }
		else if (x-1 >=0 && x-1 <colLength && !visited.contains(new Point(x-1, y)))
		{ x--; return true; }
		else if (y-1 >=0 && y-1 <rowLength && !visited.contains(new Point(x, y-1)))
		{ y--; return true; }		
		else{ return false; }
	}
	
	private static void backTrack(){
		trail.pop();
		x = trail.peek().x;
		y = trail.peek().y;
		System.out.println("Backtracked to: " + "y= " + y + " x= " + x);
	}
	
	public void move() throws InterruptedException{
		
		Locator locator = new Locator();
		
		dirtFloor = DirtLevel.getDirtLevel(floor);
		ObstacleSensor obsSensor = new ObstacleSensor(floor);
		
		while (visited.size() < floorLength){

			if (safePath()){
				System.out.println("Visited Y&X Cords: " + y + " | "+ x);
				visited.add(new Point(x, y));
				if ((obsSensor.checkObstacle(y, x)) == true){
					locator.setX(x);
					locator.setY(y);
					trail.push(new Point(x, y));
					
					if (dirtFloor[y][x]==1 || dirtFloor[y][x]==2 || dirtFloor[y][x]==3){
						System.out.println("Cleaning: Y&X " + y + " | " + x);
						Thread.sleep(1000); //1 second delay
						System.out.println();
					}
				}	
			}
			else {
				backTrack(); //pops the last element and assigns the last coordinates to x and y
			}			
		}
		System.out.println("Current floor is cleaned.");
	}
}
