package net.bithaven.efficiencyrpg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.Character;
import net.bithaven.efficiencyrpg.entity.Door;
import net.bithaven.efficiencyrpg.entity.Entity;
import net.bithaven.efficiencyrpg.entity.Finish;
import net.bithaven.efficiencyrpg.entity.Start;
import net.bithaven.efficiencyrpg.event.effect.SoundEffect;
import net.bithaven.efficiencyrpg.ui.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;


public class RoomLayer extends UILayer implements DrawsMouseCursor, SuppliesMusic {
	Game game;
	Room room;
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
		super(game, 0, 0);
		this.game = super.game;
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
		
		// Check for win.
		ArrayList<Door> doors = room.entitiesAt(game.hero.x, game.hero.y, Door.class);
		if (doors.size() > 0) {
			Door door = (Door)doors.get(0);
			winLoseScreen.state = WinLoseScreen.State.WON;
			Character.Record r = game.hero.record.get((int)room.roomNumber);
			if (winLoseScreen.turnAllBaddiesKilled == -1) {
				winLoseScreen.expEarned = game.hero.calcXP(room.turnCount, door.getDistanceFromCharacterStart(), room.totalMonsterLevels / 2);
				if (game.hero.record.get((int)room.roomNumber).getEscapeTurn() != null) {
					int prevTurns = game.hero.record.get((int)room.roomNumber).getEscapeTurn();
					if (room.turnCount >= prevTurns) {
						winLoseScreen.expEarned = 0;
					} else {
						winLoseScreen.expEarned -= game.hero.calcXP(prevTurns, door.getDistanceFromCharacterStart(), room.totalMonsterLevels / 2);
						if (r == null) {
							game.hero.record.put((int)room.roomNumber, game.hero.new Record((int)room.roomNumber, room.turnCount, null));
						} else {
							r.set(room.turnCount, null);
						}
					}
				} else {
					if (r == null) {
						game.hero.record.put((int)room.roomNumber, game.hero.new Record((int)room.roomNumber, room.turnCount, null));
					} else {
						r.set(room.turnCount, null);
					}
				}
				game.hero.addXP(winLoseScreen.expEarned);
			} else {
				winLoseScreen.expEarned = game.hero.calcXP(winLoseScreen.turnAllBaddiesKilled, room.initialMonsterCount, room.totalMonsterLevels);
				if (game.hero.record.get((int)room.roomNumber).getKillTurn() != null) {
					int prevTurns = game.hero.record.get((int)room.roomNumber).getKillTurn();
					if (winLoseScreen.turnAllBaddiesKilled >= prevTurns) {
						winLoseScreen.expEarned = 0;
					} else {
						winLoseScreen.expEarned -= game.hero.calcXP(prevTurns, room.initialMonsterCount, room.totalMonsterLevels);
						if (r == null) {
							game.hero.record.put((int)room.roomNumber, game.hero.new Record((int)room.roomNumber, null, winLoseScreen.turnAllBaddiesKilled));
						} else {
							r.set(null, winLoseScreen.turnAllBaddiesKilled);
						}
					}
				} else {
					if (r == null) {
						game.hero.record.put((int)room.roomNumber, game.hero.new Record((int)room.roomNumber, null, winLoseScreen.turnAllBaddiesKilled));
					} else {
						r.set(null, winLoseScreen.turnAllBaddiesKilled);
					}
				}
				game.hero.addXP(winLoseScreen.expEarned);
			}
			game.pushUILayer(winLoseScreen);
			loadRoomNumber = door.roomNumber;
		}
		
		// Check for start.
		ArrayList<Entity> start = room.allEntitiesOfType(Start.class);
		if(start.size() > 0 && !((Start) start.get(0)).started) {
			((Start) start.get(0)).started = true;
			winLoseScreen.state = WinLoseScreen.State.START;
			game.pushUILayer(winLoseScreen);
		}
		
		// Check for finish.
		if(room.checkForTypeAt(game.hero.x, game.hero.y, Finish.class)) {
			winLoseScreen.state = WinLoseScreen.State.FINISHED;
			game.pushUILayer(winLoseScreen);
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
		
		if (room != null) {
			room.cleanup();
		}
		room = new Room(game, roomStrings, (long)loadRoomNumber);
		
		winLoseScreen.turnAllBaddiesKilled = -1;
		
		Character.Record r = game.hero.record.get((int)room.roomNumber);
		if (r == null) {
			game.hero.record.put((int)room.roomNumber, game.hero.new Record((int)room.roomNumber, null, null));
		}
		
		game.events.add(new SoundEffect(SOUND_ENTER_ROOM));
		
		if (room.metadata.containsKey("music")) {
			musicToPlay = room.metadata.get("music");
		}
		
		if (panel != null) panel.target = null;
		
		loadRoomNumber = -1;
	}

	public String musicToPlay() {
		return musicToPlay;
	}

	public void loadRoom(int roomNumber) {
		loadRoomNumber  = roomNumber;
	}

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
