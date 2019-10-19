package main;

public class Main {
	
	
	public static void main(String[] args) {
		
		//TODO initialize Locator to get initial Coordinates
		
		//Putting in Dummy Data:
		int x = 0;
		int y = 0;
		
		int[][] twoDArray = {
				{0,0,0,0},
				{0,0,0,0},
				{0,0,0,0},
				{0,0,0,0}
		};
		
		MoveRobot moveRobo = new MoveRobot(twoDArray, x, y);
		moveRobo.move();

	}
}
