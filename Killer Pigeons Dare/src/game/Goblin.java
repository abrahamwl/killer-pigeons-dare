package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Goblin extends Actor {
	Image facingUp = null;
	Image facingLeft = null;
	Image facingRight = null;
	Image facingDown = null;
	
	public Goblin () {
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/goblinguy.png", 33, 36, 0);
			image = sheet.getSubImage(1, 0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
