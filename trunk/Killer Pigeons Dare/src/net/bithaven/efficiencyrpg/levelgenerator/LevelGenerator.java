package net.bithaven.efficiencyrpg.levelgenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

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
			if(!(path.x < 0 && path.x >= grid.length && path.y < 0 && path.y >= grid[0].length)) grid[path.x][path.y] = pathMarker;
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
		long seed = new Random().nextLong(); // Change to create a different level set
		int journey = 100; // Make this longer to create more levels
		
		if(args.length == 2) {
			// Allow the values to be set from the command line
			seed = new Long(args[0]);
			journey = new Integer(args[1]);
		}
		
		// How much effort we want to put into level generation
		int tries = 1000;
		int searchDepth = 1000;

		// Parameters to make gameplay interesting
		float forestDensity = 0.3f;
		float enemyDensity = 0.01f;

		float badLandObstacleDensity = 0.3f;
		float badLandEnemyDensity= 0.3f;

		// Variables to track the journey
		int prior = 0; // Previous location
		
		// id is the distance travelled to get to this room
		int distTravelled = 0;		
		int id = distTravelled;
		
		// Create a room for each destination 
		// until there are none left
		LinkedList<Integer> destinations = new LinkedList<Integer>();
		destinations.add(id);
		Set<Integer> visited = new HashSet<Integer>(); // Tracks where we've been, so no endless revisits
		visited.add(id);
		
		while (destinations.size() > 0) {
			// The random variable that generates it all
			id = destinations.pop();
			distTravelled = id;
			Random rand = new Random(seed + id);
			
			// Farther you go the harder the enemies
			enemyDensity = distTravelled * 0.0025f;
			
			System.out.println("Current id: " + id); // DEBUG
			
			String[][] environment = null;
			String[][] entities = null;
			boolean goodLevel = true;
			
			do {
				// Init level size parameters
				Coord start = null;
				int r = Math.max(8, rand.nextInt(12));
				int c = Math.max(10, rand.nextInt(15));
				System.out.println("c: " + c + " r: " + r); // DEBUG
				
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
					int maxDoors = (r * c * 2) / (r + c);
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
				int enemies = 0;
				if(journey - distTravelled > 15 || journey < 40) {
					int trees = (int) Math.round((r * c) * forestDensity);
					Util.forest(entities, trees, seed);
					int forestEnemies = (int) Math.round((r * c) * enemyDensity);
					Util.forestEnemies(entities, forestEnemies, seed);
					enemies = forestEnemies;
				} else {
					Util.randomFill(environment, new String[]{"c", "d"}, seed);
					int badLandObstacles = (int) Math.round((r * c) * badLandObstacleDensity);
					Util.badLand(entities, badLandObstacles, seed);
					int badLandEnemies = (int) Math.round((r * c) * badLandEnemyDensity);
					Util.badLandEnemies(entities, badLandEnemies, seed);
					enemies = badLandEnemies;
				}
				
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
						doorDist.put(door, pathLength);
					}
				}
				
				// Place the door destinations;
				// Make sure there's at least one good length path;
				goodLevel = false;
				int goodPathLength = Math.max(4, (r + c) / 6);
				for(Coord door : doors) {
					int pathLength = doorDist.get(door);
					
					if(pathLength > goodPathLength) {
						goodLevel = true;
						
						// The room is labeled by how far it takes the player
						int totalDistTravelled = distTravelled + pathLength;
						
						// Make sure doors never lead somewhere already visited
						while(visited.contains(totalDistTravelled)) totalDistTravelled++; 
						visited.add(totalDistTravelled);

						if(totalDistTravelled < journey) {
							// Set the room number door on the map
							environment[door.x][door.y] = new Integer(totalDistTravelled).toString();
							destinations.add(totalDistTravelled);
						} else {
							// The player has found the end!
							environment[door.x][door.y] = "f";
						}
					}
				}
				
				// Allow the player to return to the previous level through the entrance door
				environment[entrance.x][entrance.y] = new Long(prior).toString();
				
				// Or not
				environment[entrance.x][entrance.y] = "X";
				
				// Put in walls
				environment = Util.wallOffGrid(environment, "R");
				entities = Util.wallOffGrid(entities, "R");
					
				// Print out the level
				if(goodLevel) {
					int enemyLevel = Math.max(1, (distTravelled - (enemies)) / 10);
					System.out.println("Enemy level: " + enemyLevel); // DEBUG
					levelMap.put(id, 
							Level.newBuilder()
							.id(id)
							.prior(prior)
							.start(start)
							.environment(environment)
							.entities(entities)
							.exits(doorDist.values().toArray(new Integer[0]))
							// Increment the enemy level formula
							.enemyLevel(enemyLevel)
							.create());

					System.out.println(Util.convertGridToRoomString(environment).replace(";", ";\n").replace("-1", "X")); // DEBUG
					System.out.println(Util.convertGridToRoomString(entities).replace(";", ";\n")); // DEBUG
				} 
				
				if(!goodLevel) tries--; else break;
	
			} while (!goodLevel && tries > 0);
		}
		
		int farthest = 0;
		for(Integer dist : visited) farthest = Math.max(farthest, dist);
		System.out.println("Farthest distance seen: " + farthest); // DEBUG
		
		for(Integer dist : levelMap.keySet()) farthest = Math.max(farthest, dist);
		System.out.println("Farthest actually travelled: " + farthest); // DEBUG
		
		// Delete previous levels
		for(File file : new File("./").listFiles())
			if(file.toString().contains("room"))
				file.delete();
		
		// Create new levels
		for(Level level : levelMap.values()) {
			try {
				PrintWriter envpw = new PrintWriter(new File("room_" + level.id + "_a"));
				envpw.print("level," + level.enemyLevel + ";");
				envpw.print("|");
				envpw.print(Util.convertGridToRoomString(level.environment).replace(";", ";\n").replace("-1", "X"));
				envpw.close();
				
				PrintWriter entpw = new PrintWriter(new File("room_" + level.id + "_b"));
				entpw.print(Util.convertGridToRoomString(level.entities).replace(";", ";\n"));
				entpw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
