package net.bithaven.efficiencyrpg.levelgenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.bithaven.efficiencyrpg.Dir;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.entity.*;
import net.bithaven.efficiencyrpg.entity.Character;

public class Component {
	List<Component> subComponents = new ArrayList<Component>();
	public static Map<String, String> mtdt = new HashMap<String, String>();
	public static List<String[][]> grids = new ArrayList<String[][]>();

	public static void init() {
		mtdt = new HashMap<String, String>();
		grids = new ArrayList<String[][]>();
	}
	
	public static void forest(String[][] grid, int size) {
		Random r = new Random();
		int w = grid.length;
		int h = grid[0].length;
		for(int i = 0; i < size; i++)
			grid[r.nextInt(w)][r.nextInt(h)] = "t";
	}
	
	private static class Coord {
		public int x;
		public int y;
		
		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public boolean equals(Object c) {
			return  c instanceof Coord && 
					this.x == ((Coord) c).x && 
					this.y == ((Coord) c).y;
		}
	}
	
	// Simple breadth first search to make sure there's a path
	public static boolean pathCheck(int sx, int sy, int ex, int ey) {
		// Create the room for path checking.
		List<String> roomStrings = new ArrayList<String>();
		for(String[][] grid : grids) roomStrings.add(convertGridToRoomString(new HashMap<String,String>(), grid));
		Room room = new Room(null, roomStrings.toArray(new String[0]), 0);
		Character hero = new Character();
		
		LinkedList<Coord> open = new LinkedList<Coord>();
		List<Coord> closed = new ArrayList<Component.Coord>();
		
		Coord start = new Coord(sx, sy);
		Coord end = new Coord(ex, ey);
		open.add(start);
		closed.add(start);
		
		while(open.size() > 0) {
			Coord currPos = open.pop();
			for(Dir d : Dir.values()) {
				if(d == Dir.NO_DIRECTION) continue;

				Coord newCoord = new Coord(currPos.x + d.x, currPos.y + d.y);
				if(closed.contains(newCoord)) continue;
				if(!room.checkForPassableAt(newCoord.x, newCoord.y, hero)) continue;

				if(newCoord.equals(end)) return true;

				open.add(newCoord);
				closed.add(newCoord);
			}	
		}
		
		return false;
	}
	
	public static String convertGridToRoomString(Map<String,String> mtdt, String[][] grid) {
		String roomString = "";
		
		for(Entry<String, String> entry : mtdt.entrySet())
			roomString += entry.getKey() + "," + entry.getValue() + ";";
		
		roomString += "|";
		
		for(int i = 0; i < grid.length; i++) { 
			for(int j = 0; j < grid[i].length; j++) {
				roomString += grid[i][j] + ",";
			}
			roomString += ";";
		}
				
		return roomString;
	}
}
