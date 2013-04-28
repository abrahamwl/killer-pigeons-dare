package game;

import game.entity.Character;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Random;

import org.newdawn.slick.*;

public class Game extends BasicGame {
	public static final int MARGIN = 800 - 512;
	Random random = null;
	
	public Character hero = null;
	Room room;
	static File[] roomFiles = null;

	public Game (String title) {
		super(title);
	}
	
	// Can be passed multiple room files on the command line to create a complex room
	public static void main(String[] args) throws SlickException {
		// Scan directory for room files
		roomFiles = (new File("./")).listFiles(new roomFileFilter());
		
		AppGameContainer app = new AppGameContainer(new Game("Killer Pigeons RPG"));
		app.setDisplayMode(512 + MARGIN, 512, false);
		app.start();
	}

	Music musc = null;
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		// Set up and play music
		musc = new Music("res/Medieval1.ogg");
		musc.play();
		
		//gc.setShowFPS(false);
		//gc.setMouseGrabbed(true);
		Image image = new Image(1, 1);
		gc.setMouseCursor(image, 0, 0);
		
		random = new Random();
		
		// Generating game objects.
		hero = new Character();
		room = new Room(this, new Random(random.nextLong()));
		
		// If room files have been passed on the command line, load them all 
		if(roomFiles != null) {
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

			room = new Room(this, roomStrings); 
		}
		
		room.init();
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		room.render(gc, g);
	}
	
	@Override
	public void update(GameContainer gc, int timePassed) throws SlickException {
		room.update(gc);
		if(!musc.playing()) musc.play();
	}

}

class roomFileFilter implements FilenameFilter {
	@Override
	public boolean accept(File arg0, String arg1) {
		return arg1.matches("room.*");
	}
}
