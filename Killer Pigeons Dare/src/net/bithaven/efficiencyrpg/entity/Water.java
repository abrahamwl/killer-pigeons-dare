package net.bithaven.efficiencyrpg.entity;

import net.bithaven.efficiencyrpg.ability.MovementPassabilityModifier;


public class Water extends Entity {
	public Water () {
		super("Water", "res/open1/dc-dngn/water/dngn_shoals_deep_water1.png", Layer.GROUND);
	}

	@Override
	public boolean execute() {
		return true; //Doesn't do anything.
	}

	@Override
	public boolean passableFor(Actor actor) {
		if (actor.activeAbilities.getPassabilityModifiers().contains(MovementPassabilityModifier.FLYING)) {
			return true;
		}
		
		if (actor.activeAbilities.getPassabilityModifiers().contains(MovementPassabilityModifier.SWIMMING)) {
			return true;
		}
		
		return false;
	}

}
