package edu.depaul.cdm;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;

/**
 * 
 * @author hamood
 *	Takes a Constructor parameters of coordinates for a particular floor unit
 *  and returns the shortest distance to the charger in terms of battery units for that floor unit.  
 */

public class ShortestPath {
	
	static int columns = 4;
	static int rows = 3;
	static int x=0, y=0;     //charger coordinates and then the next minimum
	static int[][] minUnits = new int [rows][columns];
	static HashSet<Point> tbaUnits = new HashSet<Point>(); //Set for coordinates not included in the shortest path yet
	static HashSet<Point> finalUnits = new HashSet<Point>(); //Set for coordinates with final minimum values from the charger 
	static int[][] twoDArray = {
								{0, 4, 3, 6},
								{3, 5, 2, 3},
								{2, 1, 4, 1}
							   };
	
	//Print the Shortest path array
	static void printArray(){
		for (int i=0; i<rows; i++){
			for (int j=0; j<columns; j++){
				System.out.print(minUnits[i][j] + " | ");
			}
			System.out.println();
		}
	}
	
	//Next minimum in the tbaSet
	static Point nextMinimum(){
		int u=0, v=0;
		int min = Integer.MAX_VALUE;
		Iterator<Point> i = tbaUnits.iterator(); 
		while(i.hasNext()){
			Point p = i.next();
			if (minUnits[p.y][p.x] < min){
				u = p.y;
				v = p.x;
				min = minUnits[u][v];
			}
		}
		return new Point (v, u);
	}
	
	//relax neighbors of the minimum value coordinates in clockwise motion
	static void relaxNeighbors(int y, int x){
		if (x+1 >= 0 && x+1 < columns && tbaUnits.contains(new Point(x+1, y)) && minUnits[y][x] + twoDArray[y][x+1] < minUnits[y][x+1])
			{minUnits[y][x+1] = minUnits[y][x] + twoDArray[y][x+1];}
		if (y+1 >= 0 && y+1 < rows && tbaUnits.contains(new Point(x, y+1)) && minUnits[y][x] + twoDArray[y+1][x] < minUnits[y+1][x])
			{minUnits[y+1][x] = minUnits[y][x] + twoDArray[y+1][x];}
		if (x-1 >= 0 && x-1 < columns && tbaUnits.contains(new Point(x-1, y)) && minUnits[y][x] + twoDArray[y][x-1] < minUnits[y][x-1])
			{minUnits[y][x-1] = minUnits[y][x] + twoDArray[y][x-1];}
		if (y-1 >= 0 && y-1 < rows && tbaUnits.contains(new Point(x, y-1)) && minUnits[y][x] + twoDArray[y-1][x] < minUnits[y-1][x])
			{minUnits[y-1][x] = minUnits[y][x] + twoDArray[y-1][x];}
	}
	
	static void allPointsShortestDistance(){
		//Initializing the Array to Infinite and inserting the coordinates into the HashSet as Well. 
		for (int i=0; i<rows; i++){
			for (int j=0; j<columns; j++){
				minUnits[i][j] = Integer.MAX_VALUE; 
				tbaUnits.add(new Point(j, i));     
			}
		}
		minUnits[y][x] = 0; //Setting the starting point as a Charger
		
	   while (!tbaUnits.isEmpty()){
			Point minPoint = nextMinimum();
			y = minPoint.y;
			x = minPoint.x;
			relaxNeighbors(y, x);
			tbaUnits.remove(new Point(x, y));
			finalUnits.add(new Point(x, y));
		}
	   
	}

}
