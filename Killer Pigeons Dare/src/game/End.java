package game;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class End extends Entity {
	public End () {
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
		// TODO Auto-generated method stub
		return true;
	}
}
