package game.entity;

import game.Actor;
import game.AttackController;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Goblin extends Actor {
	Image facingUp = null;
	Image facingLeft = null;
	Image facingRight = null;
	Image facingDown = null;
	
	public Goblin () {
		super("Goblin", 1);
		controller = new AttackController(this);
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			image = sheet.getSubImage(1, 0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
