package game;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Start extends Entity {
	public Start () {
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/blocks1.png", 16, 16);
			image = sheet.getSubImage(0, 10);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean execute(Room r) {
		return true;
		
	}
}
