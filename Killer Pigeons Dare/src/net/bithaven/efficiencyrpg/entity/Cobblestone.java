package net.bithaven.efficiencyrpg.entity;


import net.bithaven.efficiencyrpg.Room;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Cobblestone extends Entity {
	private static SpriteSheet sheet = null;
	
	public Cobblestone () {
		super("Cobblestone");
		if (sheet == null) {
			try {
				sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		image = sheet.getSubImage(0, 6);
	}

	@Override
	public boolean execute() {
		return true; //Doesn't do anything.
	}

	@Override
	public boolean passableFor(Actor a) {
		return true;
	}

}
