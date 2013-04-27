package game;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Room {
	Game game = null;
	Random random;
	ArrayList<Entity> ent;
	
	public Room (Game game, Random random)	{
		this.game = game;
		this.random = random;
		ent = new ArrayList<Entity>();
		ent.add(game.hero);
		
		//Generate the room.
		game.hero.x = 0;
		game.hero.y = 0;
		
		for (int i = 0; i < 15; i++) {
			Wall wall = new Wall();
			wall.x = i;
			wall.y = 0;
			ent.add(wall);
			wall = new Wall();
			wall.x = 15;
			wall.y = i;
			ent.add(wall);
			wall = new Wall();
			wall.x = 15 - i;
			wall.y = 15;
			ent.add(wall);
			wall = new Wall();
			wall.x = 0;
			wall.y = 15 - i;
			ent.add(wall);
		}
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		for (Entity e : ent) {
			if (!(e instanceof Actor)) e.render(gc, g);
		}
		for (Entity e : ent) {
			if (e instanceof Actor) e.render(gc, g);
		}
	}
	
	public void tick(GameContainer gc) {
		for (Entity e : ent) {
			e.execute(this);
		}
	}
	
	boolean checkForTypeAt (int x, int y, Class<?> type) {
		for (Entity e : ent) {
			if (e.getClass() == type) return true;
		}
		
		return false;
	}
}
