package game;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Room {
	Game game = null;
	ArrayList<Entity> ent;
	
	public Room (Game game)	{
		this.game = game;
		ent = new ArrayList<Entity>();
		ent.add(game.hero);
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		game.hero.render(gc, g);
	}
	
	public void tick(GameContainer gc) {
		for (Entity e : ent) {
			e.execute(this);
		}
	}
}
