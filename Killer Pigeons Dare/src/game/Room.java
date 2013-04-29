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
	Random random;
	ArrayList<Entity> ent;
	HashMap<String,String> metadata = new HashMap<String, String>();
	ArrayList<Entity> waiting = new ArrayList<Entity>();
	GameContainer gc;
	InfoPanel panel;

	static Sound soundEffectLeaveRoom = null; 
	
	{
		String extension = "ogg";
		File[] f = (new File("./res/")).listFiles(new RegexpFilter(".*aif"));
		if(f.length != 0) extension = "aif";
		
		try {
			soundEffectLeaveRoom = new Sound("res/sound_effect_leave_room." + extension);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	enum State {
		PLAYING,
		LOST,
		WON,
		LEVEL_UP;
	}
	State state = State.PLAYING;

	int turnCount = 0;
	int turnAllBaddiesKilled = -1;
	int monsterCount = 0;
	int totalMonsterLevels = 0;

	private Polygon moveCursor = new Polygon(new float[] {0, 0, -32, 16, -32, -16});
	private Shape drawCursor = moveCursor;
	
	static final Image IMAGE_WIN;
	static final Image IMAGE_LOST;
	static {
		Image temp = null;
		try {
			temp = new Image("res/text_room_complete.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IMAGE_WIN = temp;
		try {
			temp = new Image("res/text_room_failed.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IMAGE_LOST = temp;
	}
	
	WinLoseScreen winLose = new WinLoseScreen();


	// This function is passed a series of class names representing entities.  Columns are broken up by commas, rows broken up by semicolons.
	public Entity addEntity(String es, int ex, int ey) {
		Entity entity = new Emptity();

		if(es.equals("C")) entity = getOrCreateCharacter();
		if(es.equals("F")) entity = new Flameo();
		if(es.equals("G")) entity = new Goblin();
		if(es.equals("S")) entity = new Snake();
		if(es.equals("K")) entity = new KillerPidgeon();
		if(es.equals("R")) entity = new Wall();
		if(es.equals("W")) entity = new Water();
		if(es.equals("t")) entity = new Tree();
		if(es.equals("g")) entity = new Grass();
		if(es.equals("d")) entity = new Dirt();
		if(es.equals("X")) entity = new ClosedDoor();
		if(es.matches("[0-9]+")) 
			entity = new Door(new Integer(es));

		entity.x = ex;
		entity.y = ey;

		ent.add(entity);

		return entity;
	}

	private Entity getOrCreateCharacter() {
		if (game.hero == null) {
			game.hero = new Character();
		}

		return game.hero;
	}

	public Room (Game game, String[] roomStrings, long roomNumber) {
		this.roomNumber = roomNumber;
		
		//UI
		panel = new InfoPanel(512, 0, Game.MARGIN, 512);

		ent = new ArrayList<Entity>();
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

				roomString = roomString.replaceAll("\\s", ""); // Remove all whitespace

				roomRow = roomString.split(";");
				roomGrid = new String[roomRow.length][];
				for(int r = 0; r < roomRow.length; r++) roomGrid[r] = roomRow[r].split(",");

				for(int r = 0; r < roomGrid.length; r++) for(int c = 0; c < roomGrid[r].length; c++) {
					Entity newEntity = null;
					if(!roomGrid[r][c].equals("")) newEntity = addEntity(roomGrid[r][c], c, r);
					//if(roomGrid[r][c].equals("C")) game.hero = (Character) newEntity; // TODO HACK to not instantiate multiple heroes
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Put the hero at the end of the list so he always moves first.
		ent.remove(game.hero);
		ent.add(game.hero);
	}

	public Room (Game game, Random random)	{
		this.game = game;
		this.random = random;
		ent = new ArrayList<Entity>();

		//UI
		panel = new InfoPanel(512, 0, Game.MARGIN, 512);

		//Generate the room.
		ent.add(getOrCreateCharacter());
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

	Music musc = null;
	void init () {
		// Metadata initiation

		// Set up and play music
		if(metadata.containsKey("music")) {
			String musicName = metadata.get("music");
			File[] f = (new File("./res/")).listFiles(new RegexpFilter(musicName + ".aif"));
			try {
				if(f.length != 0) {
					musc = new Music("res/" + musicName + ".aif");
				}
				else {
					musc = new Music("res/" + musicName + ".ogg");
				}
			} catch (SlickException e1) {
				e1.printStackTrace();
			}
			musc.play();			
		}

		for (Entity e : ent) {
			e.init(this);
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

		// Hero UI
		game.hero.deferredRender(gc, g);
		
		// Win/Lose Screen
		if (state == State.WON || state == State.LOST) {
			winLose.render(gc, g);
		}
		
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

		if (metadata.containsKey("music")) {
			if(!musc.playing()) musc.play(); // loop music
		}

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

			// Check for all baddies killed.
			if (turnAllBaddiesKilled == -1) {
				int baddyCount = 0;
				for (Entity e : ent) {
					if (e instanceof Actor && !(e instanceof Character)) {
						if (!((Actor)e).isDead()) {
							baddyCount++;
						}
					}
				}
				if (baddyCount == 0) {
					turnAllBaddiesKilled = turnCount;
				}
			}
			
			//Check for win.
			if (checkForTypeAt(game.hero.x, game.hero.y, Door.class)) {
				state = State.WON;
				if (turnAllBaddiesKilled == -1) {
					Door door = (Door)entitiesAt(game.hero.x, game.hero.y, Door.class).get(0);
					winLose.expEarned = game.hero.addXP(turnCount, door.getDistanceFromCharacterStart(), totalMonsterLevels / 2);
				} else {
					winLose.expEarned = game.hero.addXP(turnAllBaddiesKilled, monsterCount, totalMonsterLevels);
				}
			}
		} else if (state == State.LOST) {
			winLose.update(gc);
		} else if (state == State.WON) {
			winLose.update(gc);
		} else if (state == State.LEVEL_UP) {
			if (game.hero.doLevelUp(gc)) {
				game.loadRoom(((Door)entitiesAt(game.hero.x, game.hero.y, Door.class).get(0)).roomNumber);
				soundEffectLeaveRoom.play();
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

	class WinLoseScreen {
		static final int IMAGE_WIDTH = 350;
		static final int OUTER_WIDTH = IMAGE_WIDTH + 10;
		static final int IMAGE_HEIGHT = 82;
		static final int OUTER_HEIGHT = IMAGE_HEIGHT + 4 * 14 + 10;
		static final int OUTER_LEFT = (800 - IMAGE_WIDTH) / 2;
		static final int LEFT = OUTER_LEFT + 5;
		static final int OUTER_TOP = 64;
		static final int TOP = OUTER_TOP + 5;
		
		int expEarned;
		int selectsLeft;
		ArrayList<Ability.Type> options;
		
		WinLoseScreen () {
		}

		public void render(GameContainer gc, Graphics g) {
			g.setColor(InfoPanel.BROWN);
			g.fillRoundRect(OUTER_LEFT, OUTER_TOP, OUTER_WIDTH, OUTER_HEIGHT, 5);
			g.setColor(Color.lightGray);
			g.drawRoundRect(OUTER_LEFT, OUTER_TOP, OUTER_WIDTH, OUTER_HEIGHT, 5);
			
			g.setColor(Color.black);
			if (state == State.WON) {
				IMAGE_WIN.draw(LEFT, TOP);
				if (turnAllBaddiesKilled == -1) {
					g.drawString("You escaped on turn " + turnCount, LEFT, TOP + IMAGE_HEIGHT + 14);
					g.drawString("This earned you " + expEarned + " experience points.", LEFT, TOP + IMAGE_HEIGHT + 2 * 14);
				} else {
					g.drawString("You defeated all the enemies on turn " + turnAllBaddiesKilled + ".", LEFT, TOP + IMAGE_HEIGHT + 14);
					g.drawString("This earned you " + expEarned + " experience points.", LEFT, TOP + IMAGE_HEIGHT + 2 * 14);
				}
			} else if (state == State.LOST) {
				IMAGE_LOST.draw(LEFT, TOP);
				g.drawString("You died on turn " + turnCount, LEFT, TOP + IMAGE_HEIGHT + 14);
			}
			g.drawString("Click to continue...", LEFT, TOP + IMAGE_HEIGHT + 3 * 14);
		}

		public void update(GameContainer gc) {
			if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				if (state == State.WON) {
					game.hero.refresh();
					state = State.LEVEL_UP;
				} else if (state == State.LOST){
					game.hero.refresh();
					game.loadRoom((int)roomNumber);
				}
			}
		}
		
	}
}
