package net.bithaven.efficiencyrpg.entity;



public class Cobblestone extends Entity {
	public Cobblestone () {
		super("Cobblestone", "res/open1/dc-dngn/floor/cobble_blood1.png", Layer.GROUND);
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
