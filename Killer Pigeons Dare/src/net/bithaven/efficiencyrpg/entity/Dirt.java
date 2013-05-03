package net.bithaven.efficiencyrpg.entity;


import net.bithaven.efficiencyrpg.Room;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Dirt extends Entity {
	private static SpriteSheet sheet = null;
	
	public Dirt () {
		super("Dirt");
		if (sheet == null) {
			try {
				sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		image = sheet.getSubImage(8, 3);
	}
	
	@Override
	public void init (Room r) {
		if (r.checkForTypeAt(x + 1, y, Dirt.class) && r.checkForTypeAt(x - 1, y, Dirt.class))
			image = sheet.getSubImage(1, 3);
		if (r.checkForTypeAt(x, y + 1, Dirt.class) && r.checkForTypeAt(x, y + 1, Dirt.class))
			image = sheet.getSubImage(2, 3);
		if (r.checkForTypeAt(x - 1, y, Dirt.class) && r.checkForTypeAt(x, y + 1, Dirt.class))
			image = sheet.getSubImage(3, 3);
		if (r.checkForTypeAt(x - 1, y, Dirt.class) && r.checkForTypeAt(x, y - 1, Dirt.class))
			image = sheet.getSubImage(4, 3);
		if (r.checkForTypeAt(x + 1, y, Dirt.class) && r.checkForTypeAt(x, y - 1, Dirt.class))
			image = sheet.getSubImage(5, 3);
		if (r.checkForTypeAt(x + 1, y, Dirt.class) && r.checkForTypeAt(x, y + 1, Dirt.class))
			image = sheet.getSubImage(6, 3);
		if (r.checkForTypeAt(x + 1, y, Dirt.class) && r.checkForTypeAt(x - 1, y, Dirt.class) && r.checkForTypeAt(x, y + 1, Dirt.class))
			image = sheet.getSubImage(7, 3);
		//Currently unnecessary.
		//if (r.checkForTypeAt(x + 1, y, Dirt.class) && r.checkForTypeAt(x - 1, y, Dirt.class) && r.checkForTypeAt(x, y + 1, Dirt.class) && r.checkForTypeAt(x, y - 1, Dirt.class))
		//	image = sheet.getSubImage(8, 3);
	}

	@Override
	public boolean execute(Room r) {
		return true; //Doesn't do anything.
	}

	@Override
	public boolean passableFor(Actor a) {
		return true;
	}

}
