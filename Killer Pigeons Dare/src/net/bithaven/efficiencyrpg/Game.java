package net.bithaven.efficiencyrpg;


import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import net.bithaven.efficiencyrpg.entity.Character;
import net.bithaven.efficiencyrpg.event.Event;
import net.bithaven.efficiencyrpg.event.effect.Effect;
import net.bithaven.efficiencyrpg.ui.DrawsMouseCursor;
import net.bithaven.efficiencyrpg.ui.SuppliesMusic;
import net.bithaven.efficiencyrpg.ui.UILayer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class Game extends BasicGame implements DrawsMouseCursor {
	public static SpriteSheet iconSheet;
	
	enum LogicState {
		ALL_LOGIC,
		NO_TURNS;
	}
	
	LogicState logicState = LogicState.ALL_LOGIC;
	
	public static final int FPS = 60;
	
	public static final int MARGIN = 800 - 512;
	Random random = null;
	
	public String tooltip = null;
	private String oldTooltip = null;
	private String tooltipToDisplay = null;
	
	boolean musicOn = false;
	Music music = null;
	String currentMusicName = null;
	SuppliesMusic musicSupplier = null;
	
	private Polygon cursor = new Polygon(new float[] {0, 0, -32, 16, -32, -16});
	
	public GameContainer gc;

	private LinkedList<UILayer> uiLayers = new LinkedList<UILayer>();

	public LinkedList<Event> events = new LinkedList<Event>();
	private LinkedList<Event> doEvents = new LinkedList<Event>();
	private LinkedList<Event> doNowEvents = new LinkedList<Event>();
	private LinkedList<Event> removeEvents = new LinkedList<Event>();
	private LinkedList<Event> addEvents = new LinkedList<Event>();
	
	public Character hero = null;

	public Game (String title) {
		super(title);
	}
	
	// Can be passed multiple room files on the command line to create a complex room
	public static void main(String[] args) throws SlickException {
		Game kpg = new Game("Killer Pigeons RPG");
		AppGameContainer app = new AppGameContainer(kpg);
		app.setDisplayMode(512 + MARGIN, 512, false);
		app.start();
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		this.gc = gc;
		
		//gc.setShowFPS(false);
		//gc.setMouseGrabbed(true);
		Image image = new Image(1, 1);
		gc.setMouseCursor(image, 0, 0);
		
		gc.setTargetFrameRate(FPS);
		gc.setMinimumLogicUpdateInterval(1);
		gc.setMaximumLogicUpdateInterval(1);
		
		random = new Random();
		
		iconSheet = new SpriteSheet(new Image("res/420__Pixel_Art__Icons_for_RPG_by_Ails.png"), 32, 32, 2, 1);
		
		pushUILayer(new RoomLayer(this));
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		this.gc = gc;
		UILayer last = null;
		Iterator<UILayer> it = uiLayers.descendingIterator();
		while (it.hasNext()) {
			UILayer layer = it.next();
			layer.render(gc, g);
			last = layer;
		}
		
		//Effects
		for (Event event : doEvents) {
			event.render(this, g);
		}
		
		if (last instanceof DrawsMouseCursor) {
			((DrawsMouseCursor)last).renderCursor(gc, g);
		} else {
			renderCursor(gc, g);
		}
		
		if (tooltip != null) {
			if (tooltip != oldTooltip) {
				String[] split = tooltip.split("\n");
				for (int i = 0; i < split.length; i++) {
					split[i] = WordUtils.wrap(split[i], 80);
				}
				tooltipToDisplay = StringUtils.join(split, "\n");
				oldTooltip = tooltip;
			}
			int width = g.getFont().getWidth(tooltipToDisplay) + 8;
			int height = g.getFont().getHeight(tooltipToDisplay) + 2;
			int x = gc.getInput().getMouseX() - width;
			x = Math.min(Math.max(x,  0), 800 - width);
			int y = gc.getInput().getMouseY();
			y = Math.min(y,  512 - height);
			
			g.setColor(Color.yellow);
			g.fillRect(x, y, width, height);
			g.setColor(Color.black);
			g.drawRect(x, y, width, height);
			g.drawString(tooltipToDisplay, x + 3, y + 1);
		}
	}
	
	@Override
	public void update(GameContainer gc, int timePassed) throws SlickException {
		//Turn Logic
		if (logicState == LogicState.ALL_LOGIC) {
			this.gc = gc;
			tooltip = null;
			uiLayers.peek().update(gc);
		}
		
		//Effects
		doEvents.addAll(events);
		events.clear();
		doNowEvents.addAll(doEvents);
		logicState = LogicState.ALL_LOGIC;
		while (!doNowEvents.isEmpty()) {
			for (Event event : doNowEvents) {
				event.update(this, timePassed);
				Effect.EventState step = event.getEventState();
				if (step == Effect.EventState.DONE) {
					removeEvents.add(event);
					addEvents.addAll(event.nextEvents);
				} else if (step == Effect.EventState.PREVENT_TURN) {
					logicState = LogicState.NO_TURNS;
				}
			}
			doEvents.removeAll(removeEvents);
			removeEvents.clear();
			doNowEvents.clear();
			doNowEvents.addAll(addEvents);
			doEvents.addAll(addEvents);
			addEvents.clear();
		}	
		
		//Music
		if (musicOn && musicSupplier != null) {
			if (musicSupplier.musicToPlay() != currentMusicName || music == null) {
				currentMusicName = musicSupplier.musicToPlay();
				playMusic(currentMusicName);
			} else if (!music.playing()) {
				music.play();
			}
		}
	}
	
	public void pushUILayer (UILayer layer) {
		if (uiLayers.size() > 0) uiLayers.peek().setEnabled(false);
		uiLayers.push(layer);
		layer.setEnabled(true);
		if (layer instanceof SuppliesMusic) {
			if (musicOn && music != null) music.stop();
			musicSupplier = (SuppliesMusic)layer;
		}
		gc.getInput().clearControlPressedRecord();
		gc.getInput().clearKeyPressedRecord();
		gc.getInput().clearMousePressedRecord();
	}
	
	public void popUILayer() {
		if (musicOn && uiLayers.peek() instanceof SuppliesMusic) {
			music.stop();
		}
		uiLayers.pop().setEnabled(false);
		uiLayers.peek().setEnabled(true);
		musicSupplier = null;
		for (UILayer mLayer : uiLayers) {
			if (mLayer instanceof SuppliesMusic) {
				musicSupplier = (SuppliesMusic)mLayer;
			}
		}
		gc.getInput().clearControlPressedRecord();
		gc.getInput().clearKeyPressedRecord();
		gc.getInput().clearMousePressedRecord();
	}

	private void playMusic(String musicName) {
		File[] f = (new File("./res/")).listFiles(new RegexpFilter(musicName + ".aif"));
		try {
			if(f.length != 0) {
				music = new Music("res/" + musicName + ".aif");
			}
			else {
				music = new Music("res/" + musicName + ".ogg");
			}
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
		music.stop();
		music.play();
	}

	public void renderCursor(GameContainer gc, Graphics g) {
		int x = gc.getInput().getMouseX();
		int y = gc.getInput().getMouseY();
		
		Shape drawCursor = cursor.transform(Transform.createRotateTransform(-(float)(Math.PI *.625)));

		drawCursor = drawCursor.transform(Transform.createTranslateTransform(x,y));
		g.setColor(Color.white);
		g.fill(drawCursor);
	}
}