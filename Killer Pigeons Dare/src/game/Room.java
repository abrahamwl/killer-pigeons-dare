package game;

import game.controller.AttackController;
import game.entity.*;

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
	
	private Polygon moveCursor = new Polygon(new float[] {0, 0, -32, 16, -32, -16});
	private Shape drawCursor = moveCursor;
	

	// This function is passed a series of class names representing entities.  Columns are broken up by commas, rows broken up by semicolons.
	public void addEntity(String es, int ex, int ey) {
		String cn = null;
		Entity entity = null;

		cn = es; // Default behavior is to use the passed string as classname, unless a known shortname is used
		if(es.equals("B")) cn = "Blueman";
		if(es.equals("C")) cn = "Character";
		if(es.equals("E")) cn = "End";
		if(es.equals("F")) cn = "Flameo";
		if(es.equals("G")) cn = "Goblin";
		if(es.equals("S")) cn = "Start";
		if(es.equals("W")) cn = "Wall";

		try {
			entity = (Entity) Class.forName("game.entity.".concat(cn)).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		entity.x = ex;
		entity.y = ey;
		
		ent.add(entity);
	}

	public Room (Game game, String[] roomStrings) {
		try {
			for(String roomString : roomStrings) {
				String[][] roomGrid = null;
				String[] roomRow = null;
				this.game = game;
				ent = new ArrayList<Entity>();
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
	}

	public Room (Game game, Random random)	{
		this.game = game;
		this.random = random;
		ent = new ArrayList<Entity>();

		//Generate the room.
		ent.add(game.hero);
		game.hero.x = 1;
		game.hero.y = 1;

		Goblin goblin = new Goblin();
		goblin.controller = new AttackController(goblin);
		goblin.x = 6;
		goblin.y = 6;
		ent.add(goblin);

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
			water.x = 5;
			water.y = 6 - i;
			ent.add(water);
		}
	}
	
	void init () {
		for (Entity e : ent) {
			e.init();
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		for (Entity e : ent) {
			if (!(e instanceof Actor)) e.render(gc, g);
		}
		for (Entity e : ent) {
			if (e instanceof Actor) e.render(gc, g);
		}
		
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
		if (waiting.isEmpty()) {
			waiting = (ArrayList<Entity>)ent.clone();
		}
		if (waiting.get(waiting.size() - 1).execute(this)) {
			waiting.remove(waiting.size() - 1);
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
