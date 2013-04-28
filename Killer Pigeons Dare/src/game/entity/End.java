package game.entity;

import game.Entity;
import game.Room;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class End extends Entity {
	public End () {
		super("Room Exit");
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			image = sheet.getSubImage(1, 5);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean execute(Room r) {
		// TODO Auto-generated method stub
		return true;
	}
}
