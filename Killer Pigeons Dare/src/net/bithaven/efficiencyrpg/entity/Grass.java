package net.bithaven.efficiencyrpg.entity;



public class Grass extends Entity {
	public Grass () {
		super("Grass", "res/open1/dc-dngn/floor/dirt_full.png", Layer.GROUND);
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
