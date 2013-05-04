package net.bithaven.efficiencyrpg.entity;


import net.bithaven.efficiencyrpg.Room;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Hellstone extends Entity {
	public Hellstone () {
		super("Hellstone");
		try {
			image = new Image("res/open1/dc-dngn/floor/rough_red0.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean execute() {
		return true; //Doesn't do anything.
	}

	@Override
	public boolean passableFor(Actor a) {
		return true;
	}

}
