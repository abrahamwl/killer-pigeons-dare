package game;

import org.newdawn.slick.*;

public class Game extends BasicGame {
	Character hero = null;

	public Game (String title) {
		super(title);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Game("Killer Pigeons RPG"));
		app.setDisplayMode(800, 600, false);
		app.start();
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setShowFPS(false);
		
		// Generating game objects.
		hero = new Character();		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		hero.render(gc, g);
	}
	
	@Override
	public void update(GameContainer gc, int timePassed) throws SlickException { 
	}

}