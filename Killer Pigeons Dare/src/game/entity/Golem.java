package game.entity;

import game.Ability;
import game.Actor;
import game.controller.AttackController;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Golem extends Actor {
	Image facingUp = null;
	Image facingLeft = null;
	Image facingRight = null;
	Image facingDown = null;
	
	public Golem (int level) {
		super("Golem", level);
		controller = new AttackController(this);
		abilities.add(new Ability(Ability.Type.TOUGH));
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			image = sheet.getSubImage(2, 0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
