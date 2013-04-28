package game;

import java.util.Random;

import org.newdawn.slick.*;

public class Game extends BasicGame {
	Random random = null;
	
	Character hero = null;
	Room room;

	public Game (String title) {
		super(title);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Game("Killer Pigeons RPG"));
		app.setDisplayMode(512, 512, false);
		app.start();
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		//gc.setShowFPS(false);
		
		random = new Random();
		
		// Generating game objects.
		hero = new Character();
		hero.controller = new UserController(hero);
		room = new Room(this, new Random(random.nextLong()));
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		room.render(gc, g);
	}
	
	@Override
	public void update(GameContainer gc, int timePassed) throws SlickException {
		room.update(gc);
	}

}