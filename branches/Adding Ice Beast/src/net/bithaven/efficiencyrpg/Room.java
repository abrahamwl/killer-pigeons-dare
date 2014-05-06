package net.bithaven.efficiencyrpg;


import java.util.ArrayList;
import java.util.HashMap;

import net.bithaven.efficiencyrpg.entity.*;
import net.bithaven.efficiencyrpg.entity.Character;

import org.newdawn.slick.*;

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
	
	public int width = 0;
	public int height = 0;
	
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
			if(es.equals("i")) entity = new Ice();
			if(es.equals("s")) entity = new Start();
			if(es.equals("f")) entity = new Finish();
			if(es.equals("X")) entity = new ClosedDoor();
			if(es.matches("[0-9]+")) 
				entity = new Door(new Integer(es));
		}

		entity.x = ex;
		entity.y = ey;
		entity.room = this;

		entities.add(entity);

		return entity;
	}
	
	private Entity getOrCreateCharacter() {
		if (game.hero == null) {
			game.hero = new Character();
		}

		return game.hero;
	}

	// This function is passed a series of class names 
	// representing entities.  Columns are broken up by 
	// commas, rows broken up by semicolons.
	public Room (Game game, String[] roomStrings, long roomNumber) {
		this.roomNumber = roomNumber;
		this.game = game;
		
		entities = new ArrayList<Entity>();
		try {
			for(String roomString : roomStrings) {
				String mtdt = null;
				String[][] roomGrid = null;
				String[] roomRow = null;

				// Room has metadata (before the "|" character, 
				// format is "key,value;", whitespace is not removed)
				if(roomString.contains("|")) { 
					mtdt = roomString.split("[|]")[0];
					roomString = roomString.split("[|]")[1];

					for(String data : mtdt.split(";")) 
						metadata.put(data.split(",")[0], data.split(",")[1]);
				}
				
				if (metadata.containsKey("level")) {
					enemyLevel = Integer.parseInt(metadata.get("level").toString());
				}

				roomString = roomString.replaceAll("\\s", ""); // Remove all whitespace

				roomRow = roomString.split(";");
				roomGrid = new String[roomRow.length][];
				for(int r = 0; r < roomRow.length; r++) roomGrid[r] = roomRow[r].split(",");

				for(int r = 0; r < roomGrid.length; r++) 
					for(int c = 0; c < roomGrid[r].length; c++)
						if(!roomGrid[r][c].equals("")) 
							addEntity(roomGrid[r][c], c, r);
				
				height = roomGrid.length;
				width = roomGrid[0].length;
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
			if(e == null) continue;
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
		if (waiting.get(waiting.size() - 1).execute()) {
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

	@SuppressWarnings("unchecked")
	public <E> ArrayList<E> entitiesAt (int x, int y, Class<E> type) {
		ArrayList<E> out = new ArrayList<E>();
		for (Entity e : entities) {
			if (e.x == x && e.y == y) {
				if (type.isInstance(e)) out.add((E)e);
			}
		}

		return out;
	}

	public <E extends Entity> ArrayList<E> entitiesAt (int x, int y, Entity.Layer layer) {
		ArrayList<E> out = new ArrayList<E>();
		for (Entity e : entities) {
			if (e.x == x && e.y == y) {
				if (e.layers.contains(layer)) out.add((E)e);
			}
		}

		return out;
	}

	public boolean checkForPassableAt (int x, int y, Actor a) {
		boolean pass = true;
		for (Entity e : entities) {
			if (e.x == x && e.y == y) {
				if (!e.passableFor(a)) {
					System.out.println("Cannot pass because of " + e.toString() + " at " + e.x + "," + e.y);//DEBUG
					pass = false;
				}
			}
		}

		return pass;
	}

	public void cleanup() {
		for (Entity e : entities) {
			if (!(e instanceof Character)) e.cleanup();
		}
	}
}
