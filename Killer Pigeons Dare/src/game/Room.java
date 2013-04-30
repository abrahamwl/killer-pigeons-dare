package game;

import game.entity.*;
import game.entity.Character;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Room {
	public Game game = null;
	long roomNumber;
	ArrayList<Entity> entities;
	HashMap<String,String> metadata = new HashMap<String, String>();
	ArrayList<Entity> waiting = new ArrayList<Entity>();
	
	int enemyLevel = 1;

	int turnCount = 0;
	int initialMonsterCount = 0;
	int totalMonsterLevels = 0;

	public Entity addEntity(String es, int ex, int ey) {
		Entity entity = null;

		entity = GenericEnemy.newEnemyFromCharacter(es.charAt(0), enemyLevel);
		if (entity == null) {
			if(es.equals("C")) entity = getOrCreateCharacter();
			if(es.equals("R")) entity = new Wall();
			if(es.equals("W")) entity = new Water();
			if(es.equals("t")) entity = new Tree();
			if(es.equals("g")) entity = new Grass();
			if(es.equals("d")) entity = new Dirt();
			if(es.equals("c")) entity = new Cobblestone();
			if(es.equals("h")) entity = new Hellstone();
			if(es.equals("s")) entity = new Start();
			if(es.equals("f")) entity = new Finish();
			if(es.equals("X")) entity = new ClosedDoor();
			if(es.matches("[0-9]+")) 
				entity = new Door(new Integer(es));
		}

		System.out.print("-" + es + "-");//DEBUG
		entity.x = ex;
		entity.y = ey;

		entities.add(entity);

		return entity;
	}

	private Entity getOrCreateCharacter() {
		if (game.hero == null) {
			game.hero = new Character();
		}

		return game.hero;
	}

	// This function is passed a series of class names representing entities.  Columns are broken up by commas, rows broken up by semicolons.
	public Room (Game game, String[] roomStrings, long roomNumber) {
		this.roomNumber = roomNumber;
		
		entities = new ArrayList<Entity>();
		try {
			for(String roomString : roomStrings) {
				String mtdt = null;
				String[][] roomGrid = null;
				String[] roomRow = null;
				this.game = game;

				if(roomString.contains("|")) { // Room has metadata (before the "|" character, format is "key,value;", whitespace is not removed)
					mtdt = roomString.split("[|]")[0];
					roomString = roomString.split("[|]")[1];

					for(String data : mtdt.split(";")) metadata.put(data.split(",")[0], data.split(",")[1]);
				}
				
				if (metadata.containsKey("level")) {
					enemyLevel = Integer.parseInt(metadata.get("level").toString());
				}

				roomString = roomString.replaceAll("\\s", ""); // Remove all whitespace

				roomRow = roomString.split(";");
				roomGrid = new String[roomRow.length][];
				for(int r = 0; r < roomRow.length; r++) roomGrid[r] = roomRow[r].split(",");

				for(int r = 0; r < roomGrid.length; r++) for(int c = 0; c < roomGrid[r].length; c++)
					if(!roomGrid[r][c].equals("")) addEntity(roomGrid[r][c], c, r);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Put the hero at the end of the list so he always moves first.
		entities.remove(game.hero);
		entities.add(game.hero);
		
		init();
	}

	private void init () {
		for (Entity e : entities) {
			e.init(this);
			if (e instanceof Actor && !(e instanceof Character)) {
				initialMonsterCount++;
				totalMonsterLevels += ((Actor)e).getLevel();
			}
		}
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		for (Entity e : entities) {
			if (!(e instanceof Actor)) e.render(gc, g);
		}
		for (Entity e : entities) {
			if (e instanceof Actor) e.render(gc, g);
		}
	}

	@SuppressWarnings("unchecked")
	public void update(GameContainer gc) {
		if (waiting.isEmpty()) {
			waiting = (ArrayList<Entity>)entities.clone();
			turnCount++;
		}
		if (waiting.get(waiting.size() - 1).execute(this)) {
			waiting.remove(waiting.size() - 1);
		}
	}

	public boolean containsType(Class<? extends Entity> type) {
		for (Entity e : entities) {
			if (type.isInstance(e)) return true;
		}

		return false;
	}
	
	public ArrayList<Entity> allEntitiesOfType (Class<? extends Entity> type) {
		ArrayList<Entity> out = new ArrayList<Entity>();
		for (Entity e : entities) {
				if (type.isInstance(e)) out.add(e);
		}

		return out;
	}

	public boolean checkForTypeAt (int x, int y, Class<? extends Entity> type) {
		for (Entity e : entities) {
			if (e.x == x && e.y == y) {
				if (type.isInstance(e)) return true;
			}
		}

		return false;
	}

	public ArrayList<Entity> entitiesAt (int x, int y, Class<? extends Entity> type) {
		ArrayList<Entity> out = new ArrayList<Entity>();
		for (Entity e : entities) {
			if (e.x == x && e.y == y) {
				if (type.isInstance(e)) out.add(e);
			}
		}

		return out;
	}

	public boolean checkForPassableAt (int x, int y, Actor a) {
		for (Entity e : entities) {
			if (e.x == x && e.y == y) {
				if (!e.passableFor(a)) {
					return false;
				}
			}
		}

		return true;
	}
}
