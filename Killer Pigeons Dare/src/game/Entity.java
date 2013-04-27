package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class Entity {
	public int x, y; //Location in room coordinates.
	
	String name;

	Image image = null;
	
	public void render(GameContainer gc, Graphics g) {
		image.draw(x * 32, y * 32);
	}
	
	abstract public boolean execute(Room r);
}
