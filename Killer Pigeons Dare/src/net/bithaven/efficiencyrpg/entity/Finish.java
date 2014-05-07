package net.bithaven.efficiencyrpg.entity;


import java.util.EnumSet;

import net.bithaven.efficiencyrpg.Room;

public class Finish extends Entity {
	public Finish () {
		super("Finish", "res/open1/dc-dngn/gateways/dngn_enter_zot_closed.png", EnumSet.range(Layer.GROUND, Layer.THING));
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

	@Override
	public boolean isDestructible () {
		return false;
	}
}
