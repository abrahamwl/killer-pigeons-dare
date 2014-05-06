package net.bithaven.efficiencyrpg.ability.abilities;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.MovementPassabilityModifier;
import net.bithaven.efficiencyrpg.entity.Actor;

import org.newdawn.slick.Image;


public class AbilityFlying extends Ability {
	
	public AbilityFlying() {
		super(	"Flying",
				"<Name> may move over water.",
				2, 16);
		movementPassabilityModifier = MovementPassabilityModifier.FLYING;
	}

	@Override
	protected Instance getNewInstance(Actor a) {
		return new Instance(a);
	}
}
