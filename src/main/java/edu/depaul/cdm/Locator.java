package main.java.edu.depaul.cdm;

public class Locator {

	int x = 0;
	int y = 0;
	int[][] starter;
	
	public void setStarter (int[][] starter)
	{
		this.starter = starter;
		int xLength = starter.length;
		int yLength = starter[0].length;
		
		System.out.println("Length of Rows: " + xLength);
		System.out.println("Length of Columns: " + yLength);
		
		for (int row = 0; row < xLength; row++)
		{
			for (int col = 0; col < yLength; col++)
			{
				if(starter[row][col] == 6)
				{
					this.x = row;
					this.y = col;
					System.out.println("Start point is " + x + ":" + y);
				}
			}
		}
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	/*
	//For Testing
	public static void main(String[] args) {
		Locator loc = new Locator();
		int[][] twoDArray = {
							{0,0,0,0,0},
							{0,0,0,0,0},
							{0,0,0,4,0},
							{0,0,0,0,0},
		                   };
		
		loc.setStarter(twoDArray);
	}
	*/
}
