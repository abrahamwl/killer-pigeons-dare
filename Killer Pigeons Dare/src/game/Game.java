package game;

import game.entity.Character;
import game.ui.DrawsMouseCursor;
import game.ui.SuppliesMusic;
import game.ui.UILayer;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class Game extends BasicGame implements DrawsMouseCursor {
	public static final int MARGIN = 800 - 512;
	Random random = null;
	
	public String tooltip = null;
	
	Music music = null;
	String currentMusicName = null;
	SuppliesMusic musicSupplier = null;
	
	private Polygon cursor = new Polygon(new float[] {0, 0, -32, 16, -32, -16});
	
	public GameContainer gc;

	private LinkedList<UILayer> uiLayers = new LinkedList<UILayer>();
	
	public Character hero = null;

	public Game (String title) {
		super(title);
	}
	
	// Can be passed multiple room files on the command line to create a complex room
	public static void main(String[] args) throws SlickException {
		
		AppGameContainer app = new AppGameContainer(new Game("Killer Pigeons RPG"));
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
		
		random = new Random();
		
		pushUILayer(new RoomLayer(this));
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		this.gc = gc;
		UILayer last = null;
		Iterator<UILayer> it = uiLayers.descendingIterator();
		while (it.hasNext()) {
			UILayer layer = it.next();
			layer.render(gc, g);
			last = layer;
		}
		
		if (last instanceof DrawsMouseCursor) {
			((DrawsMouseCursor)last).renderCursor(gc, g);
		} else {
			renderCursor(gc, g);
		}
		
		if (tooltip != null) {
			int width = g.getFont().getWidth(tooltip) + 6;
			int height = g.getFont().getLineHeight() + 2;
			int x = gc.getInput().getMouseX() - width;
			x = Math.min(Math.max(x,  0), 800 - width);
			int y = gc.getInput().getMouseY();
			y = Math.min(y,  512 - height);
			
			g.setColor(Color.yellow);
			g.fillRect(x, y, width, height);
			g.setColor(Color.black);
			g.drawRect(x, y, width, height);
			g.drawString(tooltip, x + 3, y + 1);
		}
	}
	
	@Override
	public void update(GameContainer gc, int timePassed) throws SlickException {
		this.gc = gc;
		tooltip = null;
		uiLayers.peek().update(gc);
		
		if (musicSupplier != null) {
			if (musicSupplier.musicToPlay() != currentMusicName || music == null) {
				currentMusicName = musicSupplier.musicToPlay();
				playMusic(currentMusicName);
			} else if (!music.playing()) {
				music.play();
			}
		}
	}
	
	public void pushUILayer (UILayer layer) {
		uiLayers.push(layer);
		layer.setEnabled(true);
		if (layer instanceof SuppliesMusic) {
			if (music != null) music.stop();
			musicSupplier = (SuppliesMusic)layer;
		}
	}
	
	public void popUILayer() {
		if (uiLayers.peek() instanceof SuppliesMusic) {
			music.stop();
		}
		uiLayers.pop().setEnabled(false);
		musicSupplier = null;
		for (UILayer mLayer : uiLayers) {
			if (mLayer instanceof SuppliesMusic) {
				musicSupplier = (SuppliesMusic)mLayer;
			}
		}
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

	@Override
	public void renderCursor(GameContainer gc, Graphics g) {
		int x = gc.getInput().getMouseX();
		int y = gc.getInput().getMouseY();
		
		Shape drawCursor = cursor.transform(Transform.createRotateTransform(-(float)(Math.PI *.625)));

		drawCursor = drawCursor.transform(Transform.createTranslateTransform(x,y));
		g.setColor(Color.white);
		g.fill(drawCursor);
	}
}