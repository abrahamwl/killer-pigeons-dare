package net.bithaven.efficiencyrpg.entity;


import java.util.EnumSet;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class ClosedDoor extends Entity {
	public ClosedDoor () {
		super("Wall", "res/open1/dc-dngn/dngn_closed_door.png", EnumSet.allOf(Layer.class));
	}

	@Override
	public boolean execute() {
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
