package game.entity;

import game.Ability;
import game.Actor;
import game.controller.AttackController;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class KillerPigeon extends Actor {
	public KillerPigeon () {
		super("Killer Pidgeon", 1);
		controller = new AttackController(this);
		abilities.add(new Ability(Ability.Type.FLYING));
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			image = sheet.getSubImage(4, 0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}