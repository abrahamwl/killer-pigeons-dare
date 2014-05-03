package net.bithaven.efficiencyrpg.entity;


import net.bithaven.efficiencyrpg.Room;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Finish extends Entity {
	public Finish () {
		super("Finish");
		try {
			image = new Image("res/open1/dc-dngn/gateways/dngn_enter_zot_closed.png");
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
	
	@Override
	public void init (Room r) {
		super.init(r);
	}

}
