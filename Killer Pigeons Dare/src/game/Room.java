package game;

import game.controller.AttackController;
import game.entity.*;
import game.entity.Character;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Room {
	public Game game = null;
	Random random;
	ArrayList<Entity> ent;
	ArrayList<Entity> waiting = new ArrayList<Entity>();
	GameContainer gc;
	InfoPanel panel;
	
	enum State {
		PLAYING,
		LOST,
		WON,
		LEVEL_UP;
	}
	State state = State.PLAYING;
	
	int turnCount = 0;
	int monsterCount = 0;
	int totalMonsterLevels = 0;
	
	private Polygon moveCursor = new Polygon(new float[] {0, 0, -32, 16, -32, -16});
	private Shape drawCursor = moveCursor;
	

	// This function is passed a series of class names representing entities.  Columns are broken up by commas, rows broken up by semicolons.
	public Entity addEntity(String es, int ex, int ey) {
		Entity entity = new Emptity();

		if(es.equals("C")) entity = new Character();
		if(es.equals("F")) entity = new Flameo();
		if(es.equals("G")) entity = new Goblin();
		if(es.equals("S")) entity = new Snake();
		if(es.equals("K")) entity = new KillerPidgeon();
		if(es.equals("R")) entity = new Wall();
		if(es.equals("W")) entity = new Water();
		if(es.equals("t")) entity = new Tree();
		if(es.equals("g")) entity = new Grass();
		if(es.equals("d")) entity = new Dirt();
		if(es.matches("[0-9]+")) entity = new Door(new Integer(es));

		entity.x = ex;
		entity.y = ey;
		
		ent.add(entity);
		
		return entity;
	}

	public Room (Game game, String[] roomStrings) {
		//UI
		panel = new InfoPanel(512, 0, Game.MARGIN, 512);

		ent = new ArrayList<Entity>();
		try {
			for(String roomString : roomStrings) {
				String[][] roomGrid = null;
				String[] roomRow = null;
				this.game = game;
				roomString = roomString.replaceAll("\\s", ""); // Remove all whitespace

				roomRow = roomString.split(";");
				roomGrid = new String[roomRow.length][];
				for(int r = 0; r < roomRow.length; r++) roomGrid[r] = roomRow[r].split(",");

				for(int r = 0; r < roomGrid.length; r++) for(int c = 0; c < roomGrid[r].length; c++) {
					Entity newEntity = null;
					if(!roomGrid[r][c].equals("")) newEntity = addEntity(roomGrid[r][c], c, r);
					if(roomGrid[r][c].equals("C")) game.hero = (Character) newEntity; // TODO HACK to not instantiate multiple heroes
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Room (Game game, Random random)	{
		this.game = game;
		this.random = random;
		ent = new ArrayList<Entity>();
		
		//UI
		panel = new InfoPanel(512, 0, Game.MARGIN, 512);

		//Generate the room.
		ent.add(game.hero);
		game.hero.x = 1;
		game.hero.y = 1;

		Goblin goblin = new Goblin();
		goblin.x = 6;
		goblin.y = 6;
		ent.add(goblin);
		KillerPidgeon pidge = new KillerPidgeon();
		pidge.x = 5;
		pidge.y = 6;
		ent.add(pidge);
		Snake snake = new Snake();
		snake.x = 6;
		snake.y = 5;
		ent.add(snake);

		for (int i = 0; i < 8; i++) {
			Wall wall = new Wall();
			wall.x = i;
			wall.y = 0;
			ent.add(wall);
			wall = new Wall();
			wall.x = 7;
			wall.y = i;
			ent.add(wall);
			wall = new Wall();
			wall.x = 7 - i;
			wall.y = 7;
			ent.add(wall);
			wall = new Wall();
			wall.x = 0;
			wall.y = 7 - i;
			ent.add(wall);
		}
		for (int i = 0; i < 4; i++) {
			Water water = new Water();
			water.x = 4;
			water.y = 6 - i;
			ent.add(water);
		}
		Water water = new Water();
		water.x = 5;
		water.y = 3;
		ent.add(water);
	}
	
	void init () {
		for (Entity e : ent) {
			e.init();
			if (e instanceof Actor && !(e instanceof Character)) {
				monsterCount++;
				totalMonsterLevels += ((Actor)e).getLevel();
			}
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		for (Entity e : ent) {
			if (!(e instanceof Actor)) e.render(gc, g);
		}
		for (Entity e : ent) {
			if (e instanceof Actor) e.render(gc, g);
		}
		
		// UI
		panel.render(gc, g);
		
		// Cursor
		int x = gc.getInput().getMouseX();
		int y = gc.getInput().getMouseY();
		int hX = game.hero.x  * Entity.CELL_SIZE;
		int hY = game.hero.y  * Entity.CELL_SIZE;
		if (x > hX + Entity.CELL_SIZE) {
			if (y < hY) {
				drawCursor = moveCursor.transform(Transform.createRotateTransform(-(float)(Math.PI * .25)));
			} else if (y > hY + Entity.CELL_SIZE) {
				drawCursor = moveCursor.transform(Transform.createRotateTransform((float)(Math.PI * .25)));
			} else {
				drawCursor = moveCursor;
			}
		} else if (x < hX) {
			if (y < hY) {
				drawCursor = moveCursor.transform(Transform.createRotateTransform(-(float)(Math.PI * .75)));
			} else if (y > hY + Entity.CELL_SIZE) {
				drawCursor = moveCursor.transform(Transform.createRotateTransform((float)(Math.PI *.75)));
			} else {
				drawCursor = moveCursor.transform(Transform.createRotateTransform((float)Math.PI));
			}
		} else {
			if (y < hY) {
				drawCursor = moveCursor.transform(Transform.createRotateTransform(-(float)(Math.PI * .5)));
			} else if (y > hY + Entity.CELL_SIZE) {
				drawCursor = moveCursor.transform(Transform.createRotateTransform((float)(Math.PI *.5)));
			} else {
				//Make it like a pointing arrow.
				drawCursor = moveCursor.transform(Transform.createRotateTransform(-(float)(Math.PI *.625)));
			}
		}
		
		drawCursor = drawCursor.transform(Transform.createTranslateTransform(x,y));
		g.setColor(Color.white);
		g.fill(drawCursor);
	}

	public void update(GameContainer gc) {
		this.gc = gc;
		if (state == State.PLAYING) {
			if (waiting.isEmpty()) {
				waiting = (ArrayList<Entity>)ent.clone();
				turnCount++;
			}
			if (waiting.get(waiting.size() - 1).execute(this)) {
				waiting.remove(waiting.size() - 1);
			}
		

			// Update the InfoPanel
			int x = gc.getInput().getMouseX();
			int y = gc.getInput().getMouseY();
			ArrayList<Entity> actors = entitiesAt(x / Entity.CELL_SIZE, y / Entity.CELL_SIZE, Actor.class);
			if (!actors.isEmpty()) {
				if (panel.target != (Actor)actors.get(0)) {
					panel.target = (Actor)actors.get(0);
					panel.triggerRedraw();
				}
			}
		
			// Check for loss.
			if (game.hero.isDead()) {
				state = State.LOST;
				return;
			}
			
			// Check for win.
			int baddyCount = 0;
			for (Entity e : ent) {
				if (e instanceof Actor && !(e instanceof Character)) {
					if (!((Actor)e).isDead()) {
						baddyCount++;
					}
				}
			}
			if (baddyCount == 0) {
				state = State.WON;
			}
		} else if (state == State.LOST) {
			//TODO: Hero died. Reset the room.
		} else if (state == State.WON) {
			//TODO: Add game won screen.
			state = state.LEVEL_UP;
			game.hero.addXP(turnCount, monsterCount, totalMonsterLevels);

		} else if (state == State.LEVEL_UP) {
			if (game.hero.doLevelUp(gc)) {
				// doLevelUp() will return true when the level up is finished.
				//TODO: Move to the next level.
			}
		}
	}

	public boolean checkForTypeAt (int x, int y, Class<? extends Entity> type) {
		for (Entity e : ent) {
			if (e.x == x && e.y == y) {
				if (type.isInstance(e)) return true;
			}
		}

		return false;
	}

	public ArrayList<Entity> entitiesAt (int x, int y, Class<? extends Entity> type) {
		ArrayList<Entity> out = new ArrayList<Entity>();
		for (Entity e : ent) {
			if (e.x == x && e.y == y) {
				if (type.isInstance(e)) out.add(e);
			}
		}

		return out;
	}
	
	public boolean checkForPassableAt (int x, int y, Actor a) {
		for (Entity e : ent) {
			if (e.x == x && e.y == y) {
				if (!e.passableFor(a)) {
					return false;
				}
			}
		}

		return true;
	}
}
