package game.entity;

import game.Actor;
import game.controller.FlameoController;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Flameo extends Actor {
	public Flameo (int level) {
		super("Flameo", level);
		controller = new FlameoController(this);
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			image = sheet.getSubImage(6, 0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
