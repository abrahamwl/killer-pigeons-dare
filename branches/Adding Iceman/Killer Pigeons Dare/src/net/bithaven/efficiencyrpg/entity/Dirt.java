package net.bithaven.efficiencyrpg.entity;


import net.bithaven.efficiencyrpg.Room;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Dirt extends Entity {
	public Dirt () {
		super("Dirt", "res/open1/dc-dngn/floor/dirt0.png", Layer.GROUND);
	}
	
	@Override
	public void init (Room r) {
		/*if (r.checkForTypeAt(x + 1, y, Dirt.class) && r.checkForTypeAt(x - 1, y, Dirt.class))
			image = new Image("res/open1/dc-dngn/floor/dirt0.png");
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
			image = sheet.getSubImage(7, 3);*/
		//Currently unnecessary.
		//if (r.checkForTypeAt(x + 1, y, Dirt.class) && r.checkForTypeAt(x - 1, y, Dirt.class) && r.checkForTypeAt(x, y + 1, Dirt.class) && r.checkForTypeAt(x, y - 1, Dirt.class))
		//	image = sheet.getSubImage(8, 3);
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
