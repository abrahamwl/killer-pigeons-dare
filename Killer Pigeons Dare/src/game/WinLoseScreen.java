package game;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import game.ui.SuppliesMusic;
import game.ui.UILayer;

class WinLoseScreen extends UILayer implements SuppliesMusic {
	enum State {
		LOST,
		WON,
		LEVEL_UP,
		GAMEOVER,
		START,
		FINISHED;
	}
	State state;
	
	private static final Image IMAGE_WIN;
	private static final Image IMAGE_LOST;
	private static final Image IMAGE_FINISHED;
	private static final Image IMAGE_START;
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
		try {
			temp = new Image("res/text_start_screen.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IMAGE_START = temp;
		try {
			temp = new Image("res/text_win_screen.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IMAGE_FINISHED = temp;
	}
	
	static final int IMAGE_WIDTH = 350;
	static final int OUTER_WIDTH = IMAGE_WIDTH + 10;
	static final int IMAGE_HEIGHT = 82;
	static final int OUTER_HEIGHT = IMAGE_HEIGHT + 4 * 14 + 10;
	static final int OUTER_LEFT = (800 - IMAGE_WIDTH) / 2;
	static final int LEFT = 5;
	static final int OUTER_TOP = 64;
	static final int TOP = 5;
	
	int turnAllBaddiesKilled = -1;
	int expEarned;
	int selectsLeft;
	ArrayList<Ability.Type> options;
	private RoomLayer rLayer;
	
	WinLoseScreen (RoomLayer rLayer) {
		super(rLayer.game, OUTER_LEFT, OUTER_TOP);
		this.rLayer = rLayer;
	}

	public void draw(GameContainer gc, Graphics g) {
		g.setColor(InfoPanel.BROWN);
		g.fillRoundRect(0, 0, OUTER_WIDTH, OUTER_HEIGHT, 5);
		g.setColor(Color.lightGray);
		g.drawRoundRect(0, 0, OUTER_WIDTH, OUTER_HEIGHT, 5);
		
		g.setColor(Color.black);
		if (state == State.WON) {
			IMAGE_WIN.draw(LEFT, TOP);
			if (turnAllBaddiesKilled == -1) {
				g.drawString("You escaped on turn " + rLayer.room.turnCount, LEFT, TOP + IMAGE_HEIGHT + 14);
				g.drawString("This earned you " + expEarned + " experience points.", LEFT, TOP + IMAGE_HEIGHT + 2 * 14);
			} else {
				g.drawString("You defeated all the enemies on turn " + turnAllBaddiesKilled + ".", LEFT, TOP + IMAGE_HEIGHT + 14);
				g.drawString("This earned you " + expEarned + " experience points.", LEFT, TOP + IMAGE_HEIGHT + 2 * 14);
			}
		} else if (state == State.LOST) {
			IMAGE_LOST.draw(LEFT, TOP);
			g.drawString("You died on turn " + rLayer.room.turnCount, LEFT, TOP + IMAGE_HEIGHT + 14);
		} 
		else if (state == State.START) {
			IMAGE_START.draw(LEFT, TOP);
			g.drawString("Killer Pidgeons ravaged your village.", LEFT, TOP + IMAGE_HEIGHT + 14);
			g.drawString("Avenge the dead. Eliminate the birds.", LEFT, TOP + IMAGE_HEIGHT + 2 * 14);
		}
		else if (state == State.FINISHED) {
			IMAGE_FINISHED.draw(LEFT, TOP);
			g.drawString("You have defeated the Killer Pidgeons.", LEFT, TOP + IMAGE_HEIGHT + 14);				
			g.drawString("They shall kill and destroy nevermore.", LEFT, TOP + IMAGE_HEIGHT + 2 * 14);				
		}
		g.drawString("Click to continue...", LEFT, TOP + IMAGE_HEIGHT + 3 * 14);
	}

	public void process(GameContainer gc) {
		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if (state == State.WON) {
				rLayer.game.popUILayer();
				rLayer.game.pushUILayer(new LevelUp(rLayer.game));
			} else {
				rLayer.game.popUILayer();
			}
		}
	}

	@Override
	public String musicToPlay() {
		if (state == State.FINISHED) {
			return "music_Win";
		} else {
			return rLayer.musicToPlay();
		}
	}
	
}