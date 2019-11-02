package edu.depaul.cdm;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;

/**
 * 
 * @author hamood
 *	Takes a Constructor parameters of coordinates for a particular floor unit
 *  and returns the shortest distance to the charger in terms of battery power units.  
 */

public class ShortestPath {
	
	static HashSet<Point> tbaUnits = new HashSet<Point>(); //Set for coordinates not included in the shortest path yet
	static HashSet<Point> finalUnits = new HashSet<Point>(); //Set for coordinates with final minimum values from the charger 
	static int[][] twoDArray;
	static int columns;
	static int rows;
	static int x, y;     //charger coordinates and then the next minimum
	static int[][] minUnits; 
	
	public ShortestPath(int xCord, int yCord, int[][]twoDArray){ //Charger coordinates and Floor-Plan
		ShortestPath.y = yCord;
		ShortestPath.x = xCord;
		ShortestPath.twoDArray = twoDArray;
		ShortestPath.rows = twoDArray.length;
		ShortestPath.columns = twoDArray[0].length;
		ShortestPath.minUnits = new int [rows][columns];
	}
	
	public int[][] getShortestPath() {
		return minUnits;	
	}
	

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
		int u=y, v=x;
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
	
	public void allPointsShortestDistance(){
		for (int i=0; i<rows; i++){ //Initialize Array to Infinite & insert only traversable points in Set.
			for (int j=0; j<columns; j++){
				if(twoDArray[i][j] >= 0 && twoDArray[i][j] <3 || twoDArray[i][j] == 4) {	
					if (twoDArray[i][j] >= 0 && twoDArray[i][j] <3){
					twoDArray[i][j] += 1; //1 added as the legend is 0 to 2 which in distance is 1 to 3.
					}
					else if (twoDArray[i][j] == 4){ //1 power unit for Door
						twoDArray[i][j] = 1;
					}
					minUnits[i][j] = Integer.MAX_VALUE; 
					tbaUnits.add(new Point(j, i)); 
			}	
				else{
					minUnits[i][j] = -2;
				}
			}
		}
		minUnits[y][x] = 0; //Setting the Charging Station as the starting point
//	   printArray();
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
