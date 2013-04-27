package game;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class End extends Entity {
	public End () {
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/blocks1.png", 16, 16);
			image = sheet.getSubImage(13, 9);
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
