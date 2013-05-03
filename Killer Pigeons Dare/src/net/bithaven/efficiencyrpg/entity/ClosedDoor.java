package net.bithaven.efficiencyrpg.entity;


import net.bithaven.efficiencyrpg.Room;

import org.newdawn.slick.*;

public class ClosedDoor extends Entity {
	private static SpriteSheet sheet = null;
	
	public ClosedDoor () {
		super("Wall");
		if (sheet == null) {
			try {
				sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		image = sheet.getSubImage(1, 5);
	}

	@Override
	public boolean execute(Room r) {
		return true; //Doesn't do anything.
	}

	@Override
	public boolean passableFor(Actor a) {
		return false;
	}

	@Override
	public void render (GameContainer gc, Graphics g) {
		super.render(gc, g);
		
		g.setColor(Color.red);
		for (int i = -2; i < 3; i++) {
			g.drawLine(x * CELL_SIZE, y * CELL_SIZE + i, (x + 1) * CELL_SIZE, (y + 1) * CELL_SIZE + i);
		}
	}
}
