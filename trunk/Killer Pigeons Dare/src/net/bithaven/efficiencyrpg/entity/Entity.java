package net.bithaven.efficiencyrpg.entity;


import net.bithaven.efficiencyrpg.Room;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Entity {
	public int x, y; //Location in room coordinates.
	public static final int CELL_SIZE = 32;
	public boolean noDraw = false;
	public static boolean createImage = true;
	
	public Room room;
	
	public String name;

	public Image image = null;
	
	public Entity(String name, String imageName) {
		this.name = name;
		try {
			if (createImage) this.image = new Image(imageName);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
