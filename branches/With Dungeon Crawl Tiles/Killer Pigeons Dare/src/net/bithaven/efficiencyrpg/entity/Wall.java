package net.bithaven.efficiencyrpg.entity;


import net.bithaven.efficiencyrpg.Room;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Wall extends Entity {
	public Wall () {
		super("Wall");
		try {
			image = new Image("res/open1/dc-dngn/wall/stone_brick1.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean execute(Room r) {
		return true; //Doesn't do anything.
	}

	@Override
	public boolean passableFor(Actor a) {
		return false;
	}

}
