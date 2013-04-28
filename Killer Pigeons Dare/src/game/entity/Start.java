package game.entity;

import game.Entity;
import game.Room;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Start extends Entity {
	public Start () {
		super("Room Entrance");
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			image = sheet.getSubImage(0, 2);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean execute(Room r) {
		return true;
		
	}
}
