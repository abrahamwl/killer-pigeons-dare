package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import game.entity.Actor;
import game.entity.Character;
import game.entity.Door;
import game.entity.Entity;
import game.ui.*;

public class RoomLayer extends UILayer implements DrawsMouseCursor, SuppliesMusic {
	Game game;
	Room room;
	private Music musc = null;
	private InfoPanel panel;
	private WinLoseScreen winLoseScreen;
	private String musicToPlay;
	private int loadRoomNumber = -1;

	private static final Polygon MOVE_CURSOR = new Polygon(new float[] {0, 0, -32, 16, -32, -16});
	private Shape drawCursor = MOVE_CURSOR;
	
	private static final Sound SOUND_ENTER_ROOM;
	static {
		String extension = "ogg";
		File[] f = (new File("./res/")).listFiles(new RegexpFilter(".*aif"));
		if(f.length != 0) extension = "aif";
		
		Sound temp = null;
		try {
			temp = new Sound("res/sound_effect_leave_room." + extension);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		SOUND_ENTER_ROOM = temp;
	}

	public RoomLayer(Game game) {
		this.game = game;
		winLoseScreen = new WinLoseScreen(this);
		loadRoomNumber = 0;
		loadRoomInternal();
		panel = new InfoPanel(this, 512, 0, Game.MARGIN, 512);
		children.add(panel);
	}

	@Override
	public void draw(GameContainer gc, Graphics g) throws SlickException {
		room.render(gc, g);
	}

	@Override
	public void process(GameContainer gc) {
		if (loadRoomNumber != -1) {
			loadRoomInternal();
		}
		room.update(gc);

		// Check for loss.
		if (game.hero.isDead()) {
			winLoseScreen.state = WinLoseScreen.State.LOST;
			game.pushUILayer(winLoseScreen);
			loadRoomNumber = (int)room.roomNumber;
		}

		// Check for all baddies killed.
		if (winLoseScreen.turnAllBaddiesKilled == -1) {
			int baddyCount = 0;
			for (Entity e : room.entities) {
				if (e instanceof Actor && !(e instanceof Character)) {
					if (!((Actor)e).isDead()) {
						baddyCount++;
					}
				}
			}
			if (baddyCount == 0) {
				winLoseScreen.turnAllBaddiesKilled = room.turnCount;
			}
		}
		
		//Check for win.
		ArrayList<Entity> doors = room.entitiesAt(game.hero.x, game.hero.y, Door.class);
		if (doors.size() > 0) {
			Door door = (Door)doors.get(0);
			winLoseScreen.state = WinLoseScreen.State.WON;
			if (winLoseScreen.turnAllBaddiesKilled == -1) {
				winLoseScreen.expEarned = game.hero.calcXP(room.turnCount, door.getDistanceFromCharacterStart(), room.totalMonsterLevels / 2);
				game.hero.addXP(winLoseScreen.expEarned);
			} else {
				winLoseScreen.expEarned = game.hero.calcXP(winLoseScreen.turnAllBaddiesKilled, room.initialMonsterCount, room.totalMonsterLevels);
				game.hero.addXP(winLoseScreen.expEarned);
			}
			game.pushUILayer(winLoseScreen);
			loadRoomNumber = door.roomNumber;
		}
	}

	private void loadRoomInternal() {
		File[] roomFiles = null;
		System.out.println("Loading room #" + String.valueOf(loadRoomNumber) + "..."); //DEBUG
		roomFiles = (new File("./")).listFiles(new RegexpFilter("room_" + loadRoomNumber + "_.*"));
		String[] roomStrings = new String[roomFiles.length];
		for(int i = 0; i < roomFiles.length; i++) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(roomFiles[i]));
				roomStrings[i] = new String();
				while(br.ready()) roomStrings[i] = roomStrings[i].concat(br.readLine());
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		room = new Room(game, roomStrings, (long)loadRoomNumber);
		
		winLoseScreen.turnAllBaddiesKilled = -1;
		
		game.hero.refresh();
		
		SOUND_ENTER_ROOM.play();
		
		if (room.metadata.containsKey("music")) {
			musicToPlay = room.metadata.get("music");
		}
		
		loadRoomNumber = -1;
	}

	@Override
	public String musicToPlay() {
		return musicToPlay;
	}

	public void loadRoom(int roomNumber) {
		loadRoomNumber  = roomNumber;
	}

	@Override
	public void renderCursor(GameContainer gc, Graphics g) {
		int x = gc.getInput().getMouseX();
		int y = gc.getInput().getMouseY();
		int hX = game.hero.x  * Entity.CELL_SIZE;
		int hY = game.hero.y  * Entity.CELL_SIZE;
		if (x > hX + Entity.CELL_SIZE) {
			if (y < hY) {
				drawCursor = MOVE_CURSOR.transform(Transform.createRotateTransform(-(float)(Math.PI * .25)));
			} else if (y > hY + Entity.CELL_SIZE) {
				drawCursor = MOVE_CURSOR.transform(Transform.createRotateTransform((float)(Math.PI * .25)));
			} else {
				drawCursor = MOVE_CURSOR;
			}
		} else if (x < hX) {
			if (y < hY) {
				drawCursor = MOVE_CURSOR.transform(Transform.createRotateTransform(-(float)(Math.PI * .75)));
			} else if (y > hY + Entity.CELL_SIZE) {
				drawCursor = MOVE_CURSOR.transform(Transform.createRotateTransform((float)(Math.PI *.75)));
			} else {
				drawCursor = MOVE_CURSOR.transform(Transform.createRotateTransform((float)Math.PI));
			}
		} else {
			if (y < hY) {
				drawCursor = MOVE_CURSOR.transform(Transform.createRotateTransform(-(float)(Math.PI * .5)));
			} else if (y > hY + Entity.CELL_SIZE) {
				drawCursor = MOVE_CURSOR.transform(Transform.createRotateTransform((float)(Math.PI *.5)));
			} else {
				//Make it like a pointing arrow.
				drawCursor = MOVE_CURSOR.transform(Transform.createRotateTransform(-(float)(Math.PI *.625)));
			}
		}

		drawCursor = drawCursor.transform(Transform.createTranslateTransform(x,y));
		g.setColor(Color.white);
		g.fill(drawCursor);
	}
}
