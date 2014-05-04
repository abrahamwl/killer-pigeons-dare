package net.bithaven.efficiencyrpg.ability.abilities;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.MovementPassabilityModifier;
import net.bithaven.efficiencyrpg.entity.Actor;

import org.newdawn.slick.Image;


public class AbilityFireFriend extends Ability {
	
	public AbilityFireFriend() {
		super(	"Fire Friend",
				"Fire regenerates health.",
				0, 26);
		movementPassabilityModifier = MovementPassabilityModifier.FIRE_FRIEND;
	}

	@Override
	protected Instance getNewInstance(Actor a) {
		return new Instance(a);
	}
}
