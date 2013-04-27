package game;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Wall extends Entity {
	private static SpriteSheet sheet = null;
	
	public Wall () {
		if (sheet == null) {
			try {
				sheet = new SpriteSheet("res/blocks1.png", 16, 16);
				image = sheet.getSubImage(3, 3);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		name = "Wall";
	}

	@Override
	public void execute(Room r) {
		return; //Doesn't do anything.
	}

}
