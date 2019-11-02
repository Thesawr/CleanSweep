package edu.depaul.cdm;

public class Locator {

	int x = 0;
	int y = 0;
	int[][] starter;
	
	public void setStarter (int[][] starter)
	{
		this.starter = starter;
		int xLength = starter[0].length;
		int yLength = starter.length;
		
		System.out.println("Length of Rows: " + xLength);
		System.out.println("Length of Columns: " + yLength);
		
		for (int row = 0; row < yLength; row++)
		{
			for (int col = 0; col < xLength; col++)
			{
				if(starter[row][col] == 6)
				{
					this.x = col;
					this.y = row;
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
