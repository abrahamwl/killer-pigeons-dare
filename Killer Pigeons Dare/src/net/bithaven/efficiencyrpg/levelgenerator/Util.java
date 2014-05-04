package net.bithaven.efficiencyrpg.levelgenerator;

import java.util.Random;

public class Util {

	public static void fill(String[][] grid, String item) {
		for(int r = 0; r < grid.length; r++)
			for(int c = 0; c < grid[r].length; c++)
				grid[r][c] = item;
	}
	
	public static void copy(String[][] dest, String[][] source) {
		for(int r = 0; r < dest.length; r++)
			for(int c = 0; c < dest[r].length; c++)
				if(source[r][c] != "") 
					dest[r][c] = source[r][c];
	}
	
	public static void forest(String[][] grid, int count, long seed) {
		Random r = new Random(seed);
		int w = grid.length;
		int h = grid[0].length;
		for(int i = 0; i < count; i++)
			grid[r.nextInt(w)][r.nextInt(h)] = "t";
	}
	
	public static void enemies(String[][] grid, int count, long seed) {
		Random r = new Random(seed);
		int w = grid.length;
		int h = grid[0].length;
		for(int i = 0; i < count; i++)
			grid[r.nextInt(w)][r.nextInt(h)] = new String[]{"F", "G", "S", "O"}[r.nextInt(4)];
	}
	
}
