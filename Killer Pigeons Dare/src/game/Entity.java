package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class Entity {
	public int x, y; //Location in room coordinates.
	public int cellSize = 64;
	
	String name;

	Image image = null;
	
	public Entity(String name) {
		this.name = name;
	}

	public void render(GameContainer gc, Graphics g) {
		image.draw(x * cellSize, y * cellSize);
	}
	
	abstract public boolean execute(Room r);
}
