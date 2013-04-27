package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Wall extends Entity {
	private static SpriteSheet sheet = null;
	
	public Wall () {
		if (sheet == null) {
			try {
				sheet = new SpriteSheet(new Image("res/blocks1.png"), 32, 32, 2, 2);
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
