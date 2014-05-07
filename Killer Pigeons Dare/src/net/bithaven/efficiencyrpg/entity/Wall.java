package net.bithaven.efficiencyrpg.entity;


import java.util.EnumSet;

public class Wall extends Entity {
	public Wall () {
		super("Wall", "res/open1/dc-dngn/wall/stone_brick1.png", EnumSet.allOf(Layer.class));
	}

	@Override
	public boolean execute() {
		return true; //Doesn't do anything.
	}

	@Override
	public boolean passableFor(Actor a) {
		return false;
	}

	@Override
	public boolean isDestructible () {
		return false;
	}
}
