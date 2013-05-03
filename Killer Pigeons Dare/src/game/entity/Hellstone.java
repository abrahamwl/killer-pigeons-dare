package game.entity;

import game.Room;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Hellstone extends Entity {
	private static SpriteSheet sheet = null;
	
	public Hellstone () {
		super("Hellstone");
		if (sheet == null) {
			try {
				sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		image = sheet.getSubImage(2, 9);
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
