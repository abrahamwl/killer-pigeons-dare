package net.bithaven.efficiencyrpg.entity;


import net.bithaven.efficiencyrpg.Room;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Start extends Entity {
	private static SpriteSheet sheet = null;
	public boolean started = false;

	public Start () {
		super("Start");
		if (sheet == null) {
			try {
				sheet = new SpriteSheet("res/fancy_door.png", 64, 64, 0);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		image = sheet.getSubImage(0, 0); 
	}

	@Override
	public boolean execute() {
		return true; //Doesn't do anything.
	}

	@Override
	public boolean passableFor(Actor a) {
		return true;
	}
	
	@Override
	public void init (Room r) {
		super.init(r);
	}

}
