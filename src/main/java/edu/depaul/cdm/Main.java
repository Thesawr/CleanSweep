package edu.depaul.cdm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {
	
	public static void main(String[] args) throws 
	InterruptedException, FileNotFoundException, IOException, ParseException {
		
		Object obj = new JSONParser().parse(new FileReader("src/main/resources/BareHallway.json"));
		int[][] twoDArray = ParseFloorPlan.getInstance().parse_func(obj);

		Locator locator = new Locator();
		locator.setStarter(twoDArray);
		int x = locator.getX();
		int y = locator.getY();

		MoveRobot moveRobo = new MoveRobot(twoDArray, x, y);
		moveRobo.move();

	}
}
