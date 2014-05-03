package net.bithaven.efficiencyrpg.ability.abilities;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.MovementPassabilityModifier;
import net.bithaven.efficiencyrpg.entity.Actor;

import org.newdawn.slick.Image;


public class AbilityFlying extends Ability {
	public static final String name = "Flying";
	public static final String generalDescription = " may move over water.";
	public static final Image icon = Game.iconSheet.getSprite(2, 16);
	
	static {
		Ability.getAbilityTypes().add(AbilityFlying.class);
	}

	public AbilityFlying(Actor a) {
		super(a);
		movementPassabilityModifier = MovementPassabilityModifier.FLYING;
	}
}
