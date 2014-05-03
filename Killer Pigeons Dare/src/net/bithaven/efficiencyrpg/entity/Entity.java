package net.bithaven.efficiencyrpg.entity;


import net.bithaven.efficiencyrpg.Room;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class Entity {
	public int x, y; //Location in room coordinates.
	public static final int CELL_SIZE = 64;
	public boolean noDraw = false;
	
	public Room room;
	
	public String name;

	public Image image = null;
	
	public Entity(String name) {
		this.name = name;
	}

	public void render(GameContainer gc, Graphics g) {
		if (!noDraw) {
			image.draw(x * CELL_SIZE, y * CELL_SIZE);
		}
	}
	
	abstract public boolean execute();
	
	public void init (Room r) {
		room = r;
	}
	
	public abstract boolean passableFor(Actor a);

	public void cleanup() {
	}
}
