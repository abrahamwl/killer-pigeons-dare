package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Wall extends Entity {
	private static SpriteSheet sheet = null;
	
	public Wall () {
		if (sheet == null) {
			try {
				sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		image = sheet.getSubImage(1, 2);
		name = "Wall";
	}

	@Override
	public boolean execute(Room r) {
		return true; //Doesn't do anything.
	}

}
