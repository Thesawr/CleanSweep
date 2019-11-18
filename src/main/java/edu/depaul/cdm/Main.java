package edu.depaul.cdm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

	static int[][] twoDArrayCopy;

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
				if (array[i][j] >= 0 && array[i][j] < 10){
					System.out.print(" " + array[i][j] + " | ");
				}
				else{System.out.print(array[i][j] + " | ");}
			}
			System.out.println();
		}
	}

	public static void main(String[] args) throws
	InterruptedException, FileNotFoundException, IOException, ParseException {
		
		String openDoor =   "MultiRoomWithObjectsWithFloorTypes.json";
		String closedDoor = "MultiRoomWithObjectsWithFloorTypesRightDoorClosed.json";
		
		@SuppressWarnings("resource")
		Scanner IO = new Scanner(System.in);
		System.out.println("Please enter the Floor plan, enter 1 for Open Door and enter 2 for Closed Door:");
		
		String input = IO.nextLine();
		String floorPlan, type;
		if (input.contentEquals("2")){floorPlan = closedDoor; type = "Closed Door";}
		else {floorPlan = openDoor; type = "Open Door";}
		
   		 Main main = new Main();
       JSONParser jsonParser = new JSONParser();
       Object obj = jsonParser.parse(
       new InputStreamReader(main.getClass().getClassLoader().getResourceAsStream(floorPlan)));
        
       int[][] twoDArray = ParseFloorPlan.getInstance().parse_func(obj);
       twoDArrayCopy = new int[twoDArray.length][twoDArray[0].length];
       make2DCopy(twoDArray, twoDArrayCopy); //Avoiding global state in ShortestPath Singleton
		
		//Clean Sweep Pretty Print
		String cleanSweep = "     _____   __      _____   ___           __       __\n" +
						   "    /"+" ___/  / /     / ___/  / _ \\         /  \\     / /\n" +
						  "   /"+" /     / /     / /__   / /_\\ \\       / /\\ \\   / /\n" +
						 "  /"+" /     / /     / ___/  / _____ \\     / /  \\ \\ / /\n" +
						" /"+" /___  / /___  / /___  / /     \\ \\   / /    \\ / /\n" +
					    " \\_____/ \\_____/ \\____/ /_/       \\_\\ /_/      \\_/\n"  +
					    "   ______  __    __  ___    ___    ____\n" +
					    "  / ____/ / /   / / / _ \\  /  _\\  / __ \\\n" +
					   " (___  ) / / / / / /  __/ /  __/ / /_/ /\n" +
					    " _____/  \\__/\\__/  \\___/  \\___/ / ____/\n"+
					   "                               /_/"
				;
		
		System.out.println(cleanSweep);
		System.out.println("######################################################################");
		System.out.println("Robot Starting up...");
		System.out.println("######################################################################");
		Thread.sleep(2500);
		
		System.out.println("Floorplan: " + type);
		print2DArray(twoDArray);
		System.out.println();
		
		Locator locator = Locator.getInstance();
		locator.setStarter(twoDArray);
		int x = locator.getX();
		int y = locator.getY();

		
		DoorSensor door = new DoorSensor(twoDArrayCopy);
		int[][] doorCheckedArray = door.checkDoor();
		System.out.println("After check door");
		print2DArray(doorCheckedArray);
		System.out.println();
		
		PowerManagement powerManagement = PowerManagement.getInstance();
		powerManagement.set_floor(twoDArray);	
		
		System.out.println();
		ShortestPath shortestPath = ShortestPath.getInstance();
		shortestPath.setCordsnArray(x, y, doorCheckedArray);
		shortestPath.allPointsShortestDistance();  //Calculates the shortest distance 
		int[][]shortestDist = shortestPath.getShortestPath(); //will get the 2D Array for Shortest Distance to Charger

		System.out.println("Shortest Diatance from the Charger: ");
		print2DArray(shortestDist); //Uncomment to Print all Traversable points shortest distance to charger
		MoveRobot moveRobo = new MoveRobot(twoDArray, x, y, shortestPath.getTraverableUnits(), shortestDist);
		moveRobo.move();
	}
}
