package edu.depaul.cdm;

public class DoorSensor {

	int floorplan [][];
	int xLength;
	int yLength;
	
	
	
	public DoorSensor(int floor [][])
	{
	        this.floorplan = floor;
	        this.xLength = floor[0].length;
			this.yLength = floor.length;
	}
	
	public int[][] checkDoor()
	{
		int OpenCounter = 0;
		int CloseCounter = 0;
		int xClose = 0;
		int yClose = 0;
		
		for (int row = 0; row < yLength; row++)
		{
			for (int col = 0; col < xLength; col++)
			{
				if(floorplan[row][col] == 4)
				{
					OpenCounter += 1;
				}
				else if(floorplan[row][col] == 5)
				{
					CloseCounter += 1;
					yClose = row;
					xClose = col;
				}
			}
		}
				
				System.out.println("Door Found : " + (OpenCounter+CloseCounter));
		
		if ((OpenCounter+CloseCounter) > 0)
		{
			System.out.println("Open : " + OpenCounter);
			System.out.println("Close : " + CloseCounter);
		}
		
		if (CloseCounter != 0)
		{
			Locator locator = Locator.getInstance();
			int xstart = locator.getX();
			int ystart = locator.getY();
			System.out.println("Door closed detect at " + xClose + ":" + yClose);
			
			if (xstart < xClose) //If starter point is on left side of closed door, change all right side of the door to obstacle
			{
				for (int row = 0; row < yLength; row++)
				{
					for (int col = xClose+1; col < xLength; col++)
					{
						if(floorplan[row][col] != -1)
						{
							floorplan[row][col] = 3;
						}
					}
				}
			}
			
			else if(xstart > xClose) //If starter point is on right side of closed door, change all left side of the door to obstacle
			{
				for (int row = 0; row < yLength; row++)
				{
					for (int col = xClose-1; col > 0; col--)
					{
						if(floorplan[row][col] != -1)
						{
							floorplan[row][col] = 3;
						}
					}
				}
			}
		}
		
		return this.floorplan;
	}
}
