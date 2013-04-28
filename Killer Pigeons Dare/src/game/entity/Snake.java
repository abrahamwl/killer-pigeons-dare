package game.entity;

import game.Actor;
import game.controller.AttackController;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Snake extends Actor {
	public Snake () {
		super("Snake", 1);
		controller = new AttackController(this);
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			image = sheet.getSubImage(7, 0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
