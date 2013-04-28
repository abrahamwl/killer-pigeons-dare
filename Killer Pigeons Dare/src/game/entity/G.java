package game.entity;

import game.Actor;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class G extends Actor {
	Image facingUp = null;
	Image facingLeft = null;
	Image facingRight = null;
	Image facingDown = null;
	
	public G () {
		super("Goblin", 1);
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			image = sheet.getSubImage(1, 0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
