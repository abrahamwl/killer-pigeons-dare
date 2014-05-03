package net.bithaven.efficiencyrpg.entity;


import net.bithaven.efficiencyrpg.Room;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Grass extends Entity {
	private static SpriteSheet sheet = null;
	
	public Grass () {
		super("Grass");
		if (sheet == null) {
			try {
				sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		image = sheet.getSubImage(0, 3);
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
