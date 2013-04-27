package game;

import org.newdawn.slick.*;

public class Game extends BasicGame {

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
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
	}
	
	@Override
	public void update(GameContainer gc, int timePassed) throws SlickException { 
	}

}