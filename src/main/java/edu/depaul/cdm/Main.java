package edu.depaul.cdm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

	//Copy of Original Floor Plan
	static void make2DCopy (int[][] array, int[][] arrayCopy) {
		for (int i=0; i<array.length; i++){
			for (int j=0; j<array[0].length; j++){
				arrayCopy[i][j] = array[i][j];
			}
		}
	}
	
	//Print 2D Array 
	static void print2DArray (int[][] array) {
		for (int i=0; i<array.length; i++){
			for (int j=0; j<array[0].length; j++){
				System.out.print(array[i][j] + " | ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) throws 
	InterruptedException, FileNotFoundException, IOException, ParseException {
		
		Object obj = new JSONParser().parse(new FileReader("src/main/resources/MultiRoomWithObjectsWithFloorTypes.json"));
		int[][] twoDArray = ParseFloorPlan.getInstance().parse_func(obj);
		int[][] twoDArrayCopy = new int[twoDArray.length][twoDArray[0].length];   
		make2DCopy(twoDArray, twoDArrayCopy); //Avoiding global state in ShortestPath Singleton
		
		Locator locator = Locator.getInstance();
		locator.setStarter(twoDArray);
		int x = locator.getX();
		int y = locator.getY();
		
		PowerManagement powerManagement = PowerManagement.getInstance();
		powerManagement.set_floor(twoDArray);
		
		print2DArray(twoDArray);
		
		ShortestPath shortestPath = ShortestPath.getInstance();
		shortestPath.setCordsnArray(x, y, twoDArrayCopy);
		shortestPath.allPointsShortestDistance();  //Calculates the shortest distance 
		int[][]shortestDist = shortestPath.getShortestPath(); //will get the 2D Array for Shortest Distance to Charger

//		print2DArray(shortestDist); //Uncomment to Print all Traversable points shortest distance to charger
		
		MoveRobot moveRobo = new MoveRobot(twoDArray, x, y, shortestPath.getTraverableUnits(), shortestDist);
		moveRobo.move();
	}
}
