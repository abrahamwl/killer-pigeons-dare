package game.entity;

import game.Actor;
import game.controller.AttackController;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Goblin extends Actor {
	Image facingUp = null;
	Image facingLeft = null;
	Image facingRight = null;
	Image facingDown = null;
	
	public Goblin (int level) {
		super("Goblin", level);
		controller = new AttackController(this, 2);
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			image = sheet.getSubImage(8, 5);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
