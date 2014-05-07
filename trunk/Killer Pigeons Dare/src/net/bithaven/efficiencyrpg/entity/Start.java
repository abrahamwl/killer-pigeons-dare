package net.bithaven.efficiencyrpg.entity;


import java.util.EnumSet;

import net.bithaven.efficiencyrpg.Room;

public class Start extends Entity {
	public boolean started = false;

	public Start () {
		super("Start", "res/open1/dc-dngn/gateways/dngn_enter_zot_closed.png", EnumSet.allOf(Layer.class));
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
