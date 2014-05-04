package net.bithaven.efficiencyrpg.levelgenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import net.bithaven.efficiencyrpg.Dir;
import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.entity.*;
import net.bithaven.efficiencyrpg.entity.Character;

public class LevelGenerator {
	public static List<String[][]> grids = new ArrayList<String[][]>();

	public static class Level {
		public Coord start = null;
		public int prior = 0;
		public int[] exits;
		public Map<String, String> mtdt = new HashMap<String, String>();
		public String[][] environment = null;
		public String[][] entities = null;
		
		public Level(Coord start, int prior, int[] exits, Map<String,String> mtdt, String[][] environment, String[][] entities) {
			this.start = start;
			this.prior = prior;
			this.exits = exits;
			this.mtdt = mtdt;
			this.environment = environment;
			this.entities = entities;
		}
	}
	
	public static Coord path = null;
	
	// Simple breadth first search to make sure there's a path
	public static boolean pathCheck(int sx, int sy, int ex, int ey, int limit) {
		// Create the room and hero for path checking.
		List<String> roomStrings = new ArrayList<String>();
		for(String[][] grid : grids) 
			roomStrings.add(convertGridToRoomString(grid));
		
		Room room = new Room(null, roomStrings.toArray(new String[0]), 0);
		Character hero = new Character();
		
		return pathCheck(sx, sy, ex, ey, room, hero, limit);
	}

	public static boolean pathCheck(int sx, int sy, int ex, int ey, Room room,
			Character hero, int limit) {
		// Search uses a PriorityQueue to 
		// make it a rudimentary A* search
		Queue<Coord> open = new PriorityQueue<Coord>();
		List<Coord> closed = new ArrayList<Coord>();
		
		Coord start = new Coord(sx, sy, null, Float.MAX_VALUE);
		Coord end = new Coord(ex, ey, null, Float.MIN_VALUE);
		open.add(start);
		closed.add(start);
		
		// Starting with an initial position, it adds 
		// all passable neighboring positions that 
		// haven't been added yet.  Each position gets 
		// a heuristic value marking its distance from 
		// the destination.  The priority queue will 
		// always remove the next closest point.
		while(open.size() > 0 && limit-- > 0) {
			Coord currPos = open.remove();
			for(Dir d : Dir.values()) {
				if(d == Dir.NO_DIRECTION) continue;
				
				int cx = currPos.x + d.x;
				int cy = currPos.y + d.y;
				if(cx < 0 || cx == room.width || cy < 0 || cy == room.height) continue;

				Coord newCoord = new Coord(cx, cy, currPos, end);
				if(closed.contains(newCoord)) continue;
				if(!room.checkForPassableAt(newCoord.x, newCoord.y, hero)) continue;

				if(newCoord.equals(end)) {
					path = newCoord;
					return true;
				}

				open.add(newCoord);
				closed.add(newCoord);
			}	
		}
		
		// If all passable positions have been accessed 
		// without reaching the destination, or the search 
		// depth has been exceeded, mark the destination 
		// unreachable.
		return false;
	}
	
	public static String convertGridToRoomString(String[][] grid) {
		String roomString = "";
		
		for(int i = 0; i < grid.length; i++) { 
			for(int j = 0; j < grid[i].length; j++) {
				roomString += grid[i][j] + ",";
			}
			
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
	
	public static int pathLength = 1;
	public static String[][] createPathGrid(String[][] baseGrid, Coord path, String pathMarker) {
		int maxc = baseGrid.length;
		int maxr = baseGrid[0].length;
		String[][] grid = new String[maxc][maxr];
		for(int r = 0; r < maxr; r++)
			for(int c = 0; c < maxc; c++)
				grid[c][r] = "";
		
		pathLength = 0;
		while(path != null) {
			grid[path.x][path.y] = pathMarker;
			path = path.parent;
			pathLength++;
		}
		
		return grid;
	}
	
	public static void main(String[] args) {
		// Turn off image creation for testing purposes
		Entity.createImage = false;
		
		// Create convenience objects for testing paths later on
		Game test = new Game("test");
		test.hero = new Character();

		try {
			AppGameContainer app = new AppGameContainer(test);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		// Initial level generation parameters
		long seed = 5;
		int journey = 100;
		int chunks = 2;
		
		if(args.length == 3) {
			// Allow the values to be set from the command line
			seed = new Long(args[0]);
			journey = new Integer(args[1]);
			chunks = new Integer(args[2]);
		}
		
		// How much effort we want to put into level generation
		int tries = 30;
		int searchDepth = 20;

		// Parameters to make gameplay interesting
		float forestDensity = 0.2f;
		float enemyDensity = 0.05f;

		// Variables to track the journey
		int lastRoom = 0;
		int distTravelled = 0;
		
		// The random variable that generates it all
		Random rand = new Random(seed);
		
		String[][] environment = null;
		String[][] entities = null;
		boolean goodLevel = true;
		
		do {
			// Init level parameters
			Coord start = null;
			int avgDist = journey/(2 * chunks);
			int distance = Math.max(5, rand.nextInt(avgDist) + avgDist);
			int r = Math.max(2, rand.nextInt(distance));
			int c = Math.max(2, rand.nextInt(distance - r));
			
			List<Coord> doors = null;
			Coord entrance = null;
			
			environment = new String[c + 1][r + 1];
			Util.fill(environment, "g");

			entities = new String[c + 1][r + 1];
			Util.fill(entities, " ");

			// Generate key locations
			do {
				// Create entrance door and exit doors
				int maxDoors = Math.max((c * r) / distance, 1);
				int doorCount = rand.nextInt(maxDoors) + 1;
				doors = new ArrayList<Coord>();
				entrance = new Coord(rand.nextInt(c), rand.nextInt(r), null);
				doors.add(entrance);
				Coord door = null;
				for(int i = 0; i < doorCount; i++) {
					door = new Coord(rand.nextInt(c), rand.nextInt(r), null);
					while(doors.contains(door))
						door = new Coord(rand.nextInt(c), rand.nextInt(r), null);
					doors.add(door);
				}
				
				// Create player start position
				for(int i = 0; i < Dir.values().length; i++) {
					if(Dir.values()[i] == Dir.NO_DIRECTION) continue;
					int x = entrance.x + Dir.values()[i].x;
					int y = entrance.y + Dir.values()[i].y;
					if(x < 0 || x == c || y < 0 || y == r) continue;
					start = new Coord(x,y,null);
					if(!doors.contains(start)) break;
					start = null;
				}
				tries--;
			} while (start == null && tries > 0);

			// It's too hard
			if(tries == 0) {
				goodLevel = false;
				break;
			}

			// Place obstacles
			int trees = (int) Math.round((r * c) * forestDensity);
			Util.forest(environment, trees, seed);
			int enemies = (int) Math.round((r * c) * enemyDensity);
			Util.enemies(entities, enemies, seed);
			
			// Clear the doors and start positions on the grid
			for(Coord door : doors) {
				environment[door.x][door.y] = " ";
				entities[door.x][door.y] = " ";
			}
			
			environment[start.x][start.y] = "d";
			entities[start.x][start.y] = "C";

			// Test whether there are paths to the doors
			Room room = new Room(test, new String[] {convertGridToRoomString(environment), convertGridToRoomString(entities)}, 0);
			
			HashMap<Coord, Integer> doorDist = new HashMap<Coord, Integer>();
			goodLevel = true;
			for(Coord door : doors) {
				if(!pathCheck(start.x, start.y, door.x, door.y, room, test.hero, searchDepth)) {
					goodLevel = false; // Unreachable door, bad level
					doorDist.put(door, -1);
				} else {
					Util.copy(environment, createPathGrid(environment, path, "d"));
					doorDist.put(door, pathLength);
				}
			}
			
			// Place the door destinations;
			// Make sure there's at least one good length path;
			goodLevel = false;
			int goodPathLength = (r + c) / 2;
			for(Coord door : doors) {
				int pathLength = doorDist.get(door);
				System.out.println("pathLength: " + pathLength + "/" + goodPathLength); // DEBUG
				if(pathLength > goodPathLength) goodLevel = true;
				environment[door.x][door.y] = new Integer(pathLength).toString();
			}
			
			// Allow the player to return to the previous level through the entrance door
			environment[entrance.x][entrance.y] = new Long(lastRoom).toString();
				
			// Print out the level
			if(goodLevel) 
				System.out.println("Well done."); 
			else 
				System.out.println("No bueno, senor.");
			System.out.println(convertGridToRoomString(environment).replace(";", ";\n").replace("-1", "X"));
			System.out.println(convertGridToRoomString(entities).replace(";", ";\n"));

			if(!goodLevel) tries--; else break;

		} while (!goodLevel && tries > 0);
	}
}
