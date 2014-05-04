package net.bithaven.efficiencyrpg.entity;

import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.ability.MovementPassabilityModifier;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class Water extends Entity {
	public Water () {
		super("Water", "res/open1/dc-dngn/water/dngn_shoals_deep_water1.png");
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
		
		return false;
	}

}