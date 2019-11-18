package edu.depaul.cdm;

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
	static private int traversableUnits;
	static private int[][] dirtFloor;
	private static int x;
	private static int y;
	static int colLength;
	static int rowLength;
	static int floorLength;  //The complete number of units for the Floor Plan 
	static Stack<Point> trail = new Stack<Point>(); //Robots Trail
	static HashSet<Point> visited = new HashSet<Point>(); //Visited Floor Units
	static private int[][] shortestDist;
	//static PowerManagement power;

	public int peek_x;
	public int peek_y;
	public int return_to_charger_counter;

	public static PowerManagement powerManagement = PowerManagement.getInstance();
	static ShortestPath shortestPath = ShortestPath.getInstance();
	static Locator locator = Locator.getInstance();
	
	public MoveRobot (int[][] floor, int xCord, int yCord, int traversableUnits, int[][] shortestDistToCharger){  
		MoveRobot.floor = floor;
		MoveRobot.y = yCord;
		MoveRobot.x = xCord;
		MoveRobot.colLength = floor[0].length;
		MoveRobot.rowLength = floor.length;
		MoveRobot.floorLength = colLength * rowLength;
		//MoveRobot.power = new PowerManagement(x, y);
		MoveRobot.traversableUnits = traversableUnits;
		MoveRobot.shortestDist = shortestDistToCharger;
		System.out.println("Floor Length: " + floorLength);

		this.peek_x = 0;
		this.peek_y = 0;
		this.return_to_charger_counter = 0;
	}
	
	//Gives the Current Coordinates (might not be called)
	public int[] getCords(){
		int[] cords = {x, y};
		return cords;
	}

	public void set_peek_values(int x_, int y_)
	{
		this.peek_x = x_;
		this.peek_y = y_;
	}
	
	private static void printReverseChargerTrail(ArrayList<Point> chargerTrail) throws InterruptedException{
		for (int i=chargerTrail.size()-1; i>=0; i--){
			System.out.println("Moving Back from charger: y=" + chargerTrail.get(i).y + ", x=" +chargerTrail.get(i).x);
			Thread.sleep(500);
		}
	}
	
	private static void printChargerTrail(ArrayList<Point> chargerTrail) throws InterruptedException{
		for (int i=0; i<chargerTrail.size(); i++){
			System.out.println("Moving towards charger: y=" + chargerTrail.get(i).y + ", x=" +chargerTrail.get(i).x);
			Thread.sleep(500);
		}
	}
	
	private static void returnToWork(double backToChargerPower, int lastY, int lastX) throws InterruptedException{
		ArrayList<Point> chargerTrail = new ArrayList<Point>();
		System.out.println("Going back to where it last left...");
		powerManagement.updateThreshold(backToChargerPower); //Adding the power again to go back to where it last left. backToChargerPower value would be same in both cases
		y = lastY; x = lastX; //setting co-ordinates back to where it last before going to charger
		locator.setY(y); locator.setX(x);
		chargerTrail = shortestPath.chargerTrail(lastY, lastX);
		printReverseChargerTrail(chargerTrail);
		Thread.sleep(1000);
		System.out.println("Reached back to y=" + y + " , x=" +x);
	}

	private static boolean safePath(){	
		ObstacleSensor obsSensor = new ObstacleSensor(floor);
		//1st Priority to Move rightward
		if (x+1 >=0 && x+1 <colLength && !visited.contains(new Point(x+1, y)) && obsSensor.checkObstacle(y, x+1) == true)
		{ x++; return true; }
		//Clockwise Priority from here onward: 
		else if (y+1 >=0 && y+1 <rowLength && !visited.contains(new Point(x, y+1)) && obsSensor.checkObstacle(y+1, x) == true) 
		{ y++; return true; }
		else if (x-1 >=0 && x-1 <colLength && !visited.contains(new Point(x-1, y)) && obsSensor.checkObstacle(y, x-1) == true)
		{ x--; return true; }
		else if (y-1 >=0 && y-1 <rowLength && !visited.contains(new Point(x, y-1)) && obsSensor.checkObstacle(y-1, x) == true)
		{ y--; return true; }		
		else{ return false; }
	}

	// To see what next move is
	private boolean peek_safe_path(){
		//1st Priority to Move rightward
		if (peek_x+1 >=0 && peek_x+1 <colLength && !visited.contains(new Point(peek_x+1, peek_y)))
		{ peek_x++; return true;}
		//Clockwise Priority from here onward:
		else if (peek_y+1 >=0 && peek_y+1 <rowLength && !visited.contains(new Point(peek_x, peek_y+1)))
		{ peek_y++; return true;}
		else if (peek_x-1 >=0 && peek_x-1 <colLength && !visited.contains(new Point(peek_x-1, peek_y)))
		{ peek_x--; return true;}
		else if (peek_y-1 >=0 && peek_y-1 <rowLength && !visited.contains(new Point(peek_x, peek_y-1)))
		{ peek_y--; return true;}
		else{return false;}
	}
	
	private static void backTrack() throws InterruptedException{
		trail.pop();
		powerManagement.switch_floor_types(y,x,trail.peek().y,trail.peek().x);	//Will updated powerThreshold while backtracking
		x = trail.peek().x;
		y = trail.peek().y;
		System.out.println("Backtracked to: " + "y= " + y + " x= " + x);
		System.out.println("Power Threshold: "+powerManagement.getPowerThreshold());
		Thread.sleep(500);
	}
	
	public void move() throws InterruptedException{
		
		dirtFloor = DirtLevel.getDirtLevel(floor);
		DirtLevelSensor dirtSensor = new DirtLevelSensor(dirtFloor);
		trail.push(new Point(x, y)); //Pushing charger co-ordinates
		DirtBucket dirtBucket = DirtBucket.getInstance();
		
		int current_cell_cost = 0;
		int next_cell_cost = 0;
		int average_move_cost = 0;
		int bucketCapacity;
		double unitsToReachCharger;
		double currentPower;
		int lastY;
		int lastX;
		ArrayList<Point> chargerTrail = new ArrayList<Point>();

		while (visited.size() < traversableUnits)	{

			if (safePath()){

				System.out.println("Visited Y&X Cords: " + y + " | "+ x);
				checkRechargeStation();//Continually check for new recharge stations
				visited.add(new Point(x, y));
				locator.setX(x); locator.setY(y);
				powerManagement.switch_floor_types(locator.getY(),locator.getX(),trail.peek().y,trail.peek().x);
				trail.push(new Point(x, y));
					
				while (dirtSensor.checkDirtLevel(y, x) == true){	//Checks to see if current floor tile is clean or not
					System.out.println("Cleaning: Y&X " + y + " | " + x);
					dirtBucket.vacuumDirt(); //Vaccuming and Updating the dirt bucket.
					System.out.println("Dirt Bucket Capacity: "+dirtBucket.getCapacity());
					powerManagement.vacuum(y,x);	//Has to be (y,x)
					System.out.println("Power Threshold: "+powerManagement.getPowerThreshold());
                    dirtFloor[y][x]--;
					System.out.println("Dirt Floor value: "+dirtFloor[y][x]);
					bucketCapacity = dirtBucket.getCapacity();
						if (bucketCapacity <= 0){
							unitsToReachCharger = shortestDist[y][x];
							System.out.println("Dirt Bucket Full, Robot going back to Charger...");
							System.out.println(unitsToReachCharger + " Units needed to reach charger.");
							lastY = y; lastX = x;
							chargerTrail = shortestPath.chargerTrail(lastY, lastX);
							printChargerTrail(chargerTrail);
							
							y = shortestPath.getChargerY(); x = shortestPath.getChargerY();
							locator.setY(y); locator.setX(x);
							powerManagement.updateThreshold(unitsToReachCharger);
							System.out.println("Reached the Charger, EMPTY ME Indicator is On...");
							System.out.println("Robot going into Recharge mode.");
							Thread.sleep(3000);
							dirtBucket.emptyBucket();
							System.out.println("Bucket Emptied..");
							powerManagement.recharge();
							if (powerManagement.getPowerThreshold() == 0){
								returnToWork(unitsToReachCharger, lastY, lastX);
							}
						}
					Thread.sleep(500); //Half second delay
					System.out.println();
				}
			}	
			else {backTrack();} //pops the last element and assigns the last coordinates to x and y

			if(powerManagement.lowPowerAlert()){
				System.out.println("Low Power Alert, Current Power Threshold: " + powerManagement.getPowerThreshold());
				Thread.sleep(1000);
				currentPower = powerManagement.getBatteryPower();
				double backToChargerPower = shortestDist[y][x];
				System.out.println("Power required to go back to Charger:" +backToChargerPower);
				powerManagement.updateThreshold(backToChargerPower); //Adding the power that will be consumed to go back to the charger
				System.out.println("Moving back to Charger"); //for now just moving back to the charger, it should calculate if it can clean the next tile or not
				lastY = y; lastX = x;
				chargerTrail = shortestPath.chargerTrail(lastY, lastX);
				printChargerTrail(chargerTrail);
				
				y = shortestPath.getChargerY(); x = shortestPath.getChargerX();
				System.out.println("Reached the Charger at: y=" + y + " , x=" + x);
				locator.setY(y); locator.setX(x);					
				powerManagement.recharge();
				if (powerManagement.getPowerThreshold()==0){ //now once the battery is full
					returnToWork(backToChargerPower, lastY, lastX);
				}
			}
		}
		System.out.println("Charger's Last Co-ordinates (Row-Y, column-X): " + "(" + shortestPath.getChargerY() + ", " + shortestPath.getChargerX() + ")");
		System.out.println("Floor is cleaned!");
		//Now once the Floor is cleaned:
		if (shortestPath.getChargerY() != y && shortestPath.getChargerX() != x){
			double backToChargerPower = shortestDist[y][x];
			System.out.println("Power required to go back to Charger:" +backToChargerPower);
			powerManagement.updateThreshold(backToChargerPower);
			lastY = y; lastX = x;  
			chargerTrail = shortestPath.chargerTrail(lastY, lastX);
			printChargerTrail(chargerTrail); //Go back to the current charger
			System.out.println("Power Threshold: "+powerManagement.getPowerThreshold());
		}
		else {System.out.println("Robot already located at the Charger");}
		System.out.println();
		System.out.println("######Robot is DONE for the day with the Current Floor.######");
	}
	
	private void checkRechargeStation(){ 
		for(int offset = 1; offset < 3; offset++){
			if(floor[y][x+offset] == 6) {
				shortestPath.setCordsnArray(x+offset, y, Main.twoDArrayCopy);
				shortestPath.allPointsShortestDistance();  //Calculates the shortest distance
				shortestDist = shortestPath.getShortestPath(); //will get the 2D Array for Shortest Distance to Charger
			}
			else if(floor[y+offset][x] == 6) {
				shortestPath.setCordsnArray(x,y+offset, Main.twoDArrayCopy);
				shortestPath.allPointsShortestDistance();  //Calculates the shortest distance
				shortestDist = shortestPath.getShortestPath(); //will get the 2D Array for Shortest Distance to Charger
			}
			else if(floor[y][x-offset] == 6) {
				shortestPath.setCordsnArray(x-offset, y, Main.twoDArrayCopy);
				shortestPath.allPointsShortestDistance();  //Calculates the shortest distance
				shortestDist = shortestPath.getShortestPath(); //will get the 2D Array for Shortest Distance to Charger
			}
			else if(floor[y-offset][x] == 6) {
				shortestPath.setCordsnArray(x, y-offset, Main.twoDArrayCopy);
				shortestPath.allPointsShortestDistance();  //Calculates the shortest distance
				shortestDist = shortestPath.getShortestPath(); //will get the 2D Array for Shortest Distance to Charger
			}
		}
	}
}
