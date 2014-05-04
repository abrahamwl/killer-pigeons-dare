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
	public static Map<String,String> mtdt = new HashMap<String, String>();
	public static List<String[][]> grids = new ArrayList<String[][]>();

	public static HashMap<Integer, Level> levelMap = new HashMap<Integer, Level>();
	
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
		int prior = 0; // Previous location
		
		// id is the distance travelled to get to this room
		int distTravelled = 0;		
		int id = distTravelled; 
		
		// The random variable that generates it all
		Random rand = new Random(seed);
		
		while (distTravelled < journey) {
			String[][] environment = null;
			String[][] entities = null;
			boolean goodLevel = true;
			
			do {
				// Init level size parameters
				Coord start = null;
				int avgDist = journey/(2 * chunks);
				int distance = Math.max(5, rand.nextInt(avgDist) + avgDist);
				int r = Math.max(2, rand.nextInt(distance));
				int c = Math.max(2, rand.nextInt(distance - r));
				
				// Set up the grids
				environment = new String[c + 1][r + 1];
				Util.fill(environment, "g");
	
				entities = new String[c + 1][r + 1];
				Util.fill(entities, " ");
	
				// Generate key locations: doors and player start
				List<Coord> doors = null;
				Coord entrance = null;
				
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
					environment[door.x][door.y] = "d";
					entities[door.x][door.y] = " ";
				}
				
				environment[start.x][start.y] = "d";
				entities[start.x][start.y] = "C";
	
				// Test whether there are paths to the doors
				Room room = new Room(test, new String[] {Util.convertGridToRoomString(environment), Util.convertGridToRoomString(entities)}, 0);
				
				HashMap<Coord, Integer> doorDist = new HashMap<Coord, Integer>();
				goodLevel = true;
				Coord path = null;
				for(Coord door : doors) {
					path = Util.pathCheck(start.x, start.y, door.x, door.y, room, test.hero, searchDepth);
					
					if(path == null) {
						// Unreachable door, bad level
						goodLevel = false;
						doorDist.put(door, -1);
					} else {
						// Door is reachable, potentially a good level
						Util.copy(environment, createPathGrid(environment, path, "d"));
						doorDist.put(door, prior + pathLength);
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
				environment[entrance.x][entrance.y] = new Long(prior).toString();
					
				// Print out the level
				if(goodLevel) {
					System.out.println("Well done."); // DEBUG
					levelMap.put(id, 
							Level.newBuilder()
							.id(id)
							.prior(prior)
							.start(start)
							.environment(environment)
							.entities(entities)
							.exits(doorDist.values().toArray(new Integer[0]))
							.mtdt(new HashMap<String,String>(mtdt))
							.create());
				}
				else 
					System.out.println("No bueno, senor."); // DEBUG
				
				System.out.println(Util.convertGridToRoomString(environment).replace(";", ";\n").replace("-1", "X")); // DEBUG
				System.out.println(Util.convertGridToRoomString(entities).replace(";", ";\n")); // DEBUG
	
				if(!goodLevel) tries--; else break;
	
			} while (!goodLevel && tries > 0);
		}
	}
}
