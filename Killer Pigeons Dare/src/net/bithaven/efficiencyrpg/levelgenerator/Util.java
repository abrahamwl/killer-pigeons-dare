package net.bithaven.efficiencyrpg.levelgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Map.Entry;

import net.bithaven.efficiencyrpg.Dir;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.entity.Character;

public class Util {
	/*
	 * Generic grid manipulation functions
	 */
	public static String[][] wallOffGrid(String[][] grid, String wall) {
		String[][] walledGrid = new String[grid.length + 2][grid[0].length + 2];
		copy(walledGrid, grid, 1, 1);
		for(int i = 0; i < walledGrid.length; i++) {
			walledGrid[i][0] = wall;
			walledGrid[i][walledGrid[0].length-1] = wall;
		}
		for(int i = 0; i < walledGrid[0].length; i++) {
			walledGrid[0][i] = wall;
			walledGrid[walledGrid.length-1][i] = wall;
		}
		return walledGrid;
	}
	
	public static String convertGridToRoomString(String[][] grid) {
		String roomString = "";
		
		for(int i = 0; i < grid.length; i++) { 
			for(int j = 0; j < grid[i].length; j++)
				roomString += grid[i][j] + ",";
			roomString += ";";
		}
				
		return roomString;
	}

	public static String convertMetadataToRoomString(Map<String, String> mtdt) {
		String roomString = "";

		for(Entry<String, String> entry : mtdt.entrySet())
			roomString += entry.getKey() + "," + entry.getValue() + ";";
		
		return roomString;
	}
	
	public static void fill(String[][] grid, String item) {
		for(int r = 0; r < grid.length; r++)
			for(int c = 0; c < grid[r].length; c++)
				grid[r][c] = item;
	}
	
	public static void randomFill(String[][] grid, String[] items, long seed) {
		Random rand = new Random(seed);
		for(int r = 0; r < grid.length; r++)
			for(int c = 0; c < grid[r].length; c++)
				grid[r][c] = items[rand.nextInt(items.length)];
	}
	
	public static void copy(String[][] dest, String[][] source) {
		for(int r = 0; r < dest.length; r++)
			for(int c = 0; c < dest[r].length; c++)
				if(source[r][c] != "") 
					dest[r][c] = source[r][c];
	}
	
	public static void copy(String[][] dest, String[][] source, int x, int y) {
		for(int r = 0; r < source.length; r++)
			for(int c = 0; c < source[r].length; c++)
				if(source[r][c] != "") 
					dest[x + r][y + c] = source[r][c];
	}
	
	public static int createPathGrid(String[][] grid, Coord path, String pathMarker) {
		int pathLength = 0;
		while(path != null) {
			if(!(path.x < 0 || path.x >= grid.length || path.y < 0 || path.y >= grid[0].length)) grid[path.x][path.y] = pathMarker;
			path = path.parent;
			pathLength++;
		}
		
		return pathLength;
	}
	
	/*
	 * Room type specific grid functions
	 */
	public static void arctic(String[][] grid, int count, long seed) {
		Random r = new Random(seed);
		int w = grid.length;
		int h = grid[0].length;
		for(int i = 0; i < count; i++) {
			grid[r.nextInt(w)][r.nextInt(h)] = new String[]{"d", "W"}[r.nextInt(2)];;
		}
	}
	
	public static void arcticEnemies(String[][] grid, int count, long seed) {
		Random r = new Random(seed);
		int w = grid.length;
		int h = grid[0].length;
		for(int i = 0; i < count; i++)
			grid[r.nextInt(w)][r.nextInt(h)] = new String[]{"I","G","K"}[r.nextInt(3)];
	}
	
	public static void forest(String[][] env, String[][] foreground, int count, long seed) {
		Random r = new Random(seed);
		int w = env.length;
		int h = env[0].length;
		int x = 0;
		int newx = 0;
		int y = 0;
		int newy = 0;
		String item = "";
		for(int i = 0; i < count; i++) {
			if(env[x][y].equals("W") && r.nextFloat() < 0.75f) {
				// Try to make water squares contiguous
				do {
					newx = r.nextInt(w);
					newy = r.nextInt(h);
				} while(Math.hypot(x - newx, y - newy) > 1.0 || (newx == x && newy == y));
				env[newx][newy] = "W";
			} else {
				// Otherwise, place trees and water around randomly
				newx = r.nextInt(w);
				newy = r.nextInt(h);
				switch(r.nextInt(2)) {
				case 0:
					// Trees are placed in the foreground, so they sit on top of dirt
					foreground[newx][newy] = "t";
					break;
				case 1:
					env[newx][newy] = "W";
					break;
				}
			}
			x = newx;
			y = newy;
		}
	}
	
	public static void forestEnemies(String[][] grid, int count, long seed) {
		Random r = new Random(seed);
		int w = grid.length;
		int h = grid[0].length;
		for(int i = 0; i < count; i++)
			grid[r.nextInt(w)][r.nextInt(h)] = new String[]{"S","O","K","I"}[r.nextInt(4)];
	}
	
	public static void badLand(String[][] grid, int count, long seed) {
		Random r = new Random(seed);
		int w = grid.length;
		int h = grid[0].length;
		int x = 0;
		int newx = 0;
		int y = 0;
		int newy = 0;
		String item = "";
		
		for(int i = 0; i < count; i++) {
			if(grid[x][y].equals("h") && r.nextFloat() < 0.75f) {
				// Make contiguous HellStone sections
				item = "h";
				
				do {
					newx = r.nextInt(w);
					newy = r.nextInt(h);
				} while(Math.hypot(x - newx, y - newy) > 1.0 || (newx == x && newy == y));
			} if(grid[x][y].equals("W") && r.nextFloat() < 0.25f) {
				item = "W";
				
				do {
					newx = r.nextInt(w);
					newy = r.nextInt(h);
				} while(Math.hypot(x - newx, y - newy) > 1.0 || (newx == x && newy == y));
			} else if(grid[x][y].equals("R") && r.nextFloat() < 1.0f) {
				item = "R";
				
				// Tries to keep walls from forming clumps.
				// Give it a good college try, but otherwise 
				// just place randomly.
				boolean adjacent = true;
				boolean noSquare = false;
				int placementTries = 5; 
				do {
					newx = r.nextInt(w);
					newy = r.nextInt(h);
					if(Math.hypot(x - newx, y - newy) > 1.0) adjacent = false;
					for(int i2 = 1; i2 < Dir.values().length; i2 += 2) {
						int x1 = newx + Dir.values()[(i2 + 0) % (Dir.values().length - 1) + 1].x;
						int y1 = newy + Dir.values()[(i2 + 0) % (Dir.values().length - 1) + 1].y;
						if(!(x1 < 0 || x1 >= w || y1 < 0 || y1 >=h) && !grid[x1][y1].equals("R")) noSquare = true;
						int x2 = newx + Dir.values()[(i2 + 1) % (Dir.values().length - 1) + 1].x;
						int y2 = newy + Dir.values()[(i2 + 1) % (Dir.values().length - 1) + 1].y;
						if(!(x2 < 0 || x2 >= w || y2 < 0 || y2 >=h) && !grid[x2][y2].equals("R")) noSquare = true;
						int x3 = newx + Dir.values()[(i2 + 2) % (Dir.values().length - 1) + 1].x;
						int y3 = newy + Dir.values()[(i2 + 2) % (Dir.values().length - 1) + 1].y;
						if(!(x3 < 0 || x3 >= w || y3 < 0 || y3 >=h) && !grid[x3][y3].equals("R")) noSquare = true;
						int x4 = newx + Dir.values()[(i2 + 3) % (Dir.values().length - 1) + 1].x;
						int y4 = newy + Dir.values()[(i2 + 3) % (Dir.values().length - 1) + 1].y;
						if(!(x4 < 0 || x4 >= w || y4 < 0 || y4 >=h) && !grid[x4][y4].equals("R")) noSquare = true;
					}
				} while((!adjacent || !noSquare || (newx == x && newy == y)) && placementTries-- > 0);
			} else {
				newx = r.nextInt(w);
				newy = r.nextInt(h);
				item =  new String[]{"h", "h", "R", "W"}[r.nextInt(4)];
			}
			grid[newx][newy] = item;
			x = newx;
			y = newy;
		}
	}
	
	public static void badLandEnemies(String[][] grid, int count, long seed) {
		Random r = new Random(seed);
		int w = grid.length;
		int h = grid[0].length;
		for(int i = 0; i < count; i++)
			grid[r.nextInt(w)][r.nextInt(h)] = new String[]{"F","G","K"}[r.nextInt(3)];
	}
	
	// Helper class so different metrics can be used in A*
	public static class PathMetric {
		public double measure(int sx, int sy, int ex, int ey) {return 0.0;}
		public double measure(int sx, int sy, int ex, int ey, Character c, Room r) {return 0.0;}
	}
	
	/*
	 * Testing section
	 */
	public static void main(String[] args) {
		String[][] test = 
				{{" ", " "},
				 {" ", " "}};
		System.out.println(convertGridToRoomString(wallOffGrid(test, "R")).replace(";", ";\n"));
	}
}
