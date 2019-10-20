package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {
	
	public static void main(String[] args) throws 
	InterruptedException, FileNotFoundException, IOException, ParseException {
		
		Object obj = new JSONParser().parse(new FileReader("src/resources/BareHallway.json"));
		int[][] twoDArray = parse_floor_plan.getInstance().parse_func(obj);

		//TODO initialize Locator to get initial Coordinates
		Locator locator = new Locator();
		locator.setStarter(twoDArray);
		int x = locator.getX();
		int y = locator.getY();

		MoveRobot moveRobo = new MoveRobot(twoDArray, x, y);
		moveRobo.move();

	}
}
