package game.entity;

import game.Actor;
import game.controller.FlameoController;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Flameo extends Actor {
	public Flameo () {
		super("Flame", 1);
		controller = new FlameoController(this);
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/blueman.png", 33, 36, 0);
			image = sheet.getSubImage(6, 0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
