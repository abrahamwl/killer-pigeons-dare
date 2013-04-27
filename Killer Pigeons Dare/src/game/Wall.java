package game;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Wall extends Entity {
	private static SpriteSheet sheet = null;
	
	public Wall () {
		if (sheet == null) {
			try {
				sheet = new SpriteSheet("res/blocks16.png", 16, 16, 1);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		image = sheet.getSubImage(3, 3);
		name = "Wall";
	}

	@Override
	public void execute(Room r) {
		return; //Doesn't do anything.
	}

}
