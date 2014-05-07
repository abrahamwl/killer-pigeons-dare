package net.bithaven.efficiencyrpg.entity;


import java.util.EnumSet;

public class Tree extends Entity {
	public Tree () {
		super("Tree", "res/open1/dc-dngn/wall/tree2_yellow.png", EnumSet.range(Layer.THING, Layer.ACTOR));
	}

	@Override
	public boolean execute() {
		return true; //Doesn't do anything.
	}

	@Override
	public boolean passableFor(Actor a) {
		return false;
	}

}
