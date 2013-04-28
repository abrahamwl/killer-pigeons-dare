package game.entity;

import game.Actor;

import org.newdawn.slick.*;

public class C extends Actor {
	public C () {
		super("Hero", 1);
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			image = sheet.getSubImage(0, 0);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
