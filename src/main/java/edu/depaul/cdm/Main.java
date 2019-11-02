package edu.depaul.cdm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {
	
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
		
		Object obj = new JSONParser().parse(new FileReader("src/main/resources/BareHallway.json"));
		int[][] twoDArray = ParseFloorPlan.getInstance().parse_func(obj);

		Locator locator = new Locator();
		locator.setStarter(twoDArray);
		int x = locator.getX();
		int y = locator.getY();

		ShortestPath shortestPath = new ShortestPath(x, y, twoDArray);
		shortestPath.allPointsShortestDistance();  //Calculates the shortest distance 
		int[][]shortestDist = shortestPath.getShortestPath(); //will get the 2D Array
		
		print2DArray(shortestDist);
		
//		MoveRobot moveRobo = new MoveRobot(twoDArray, x, y);
//		moveRobo.move();

	}
}
