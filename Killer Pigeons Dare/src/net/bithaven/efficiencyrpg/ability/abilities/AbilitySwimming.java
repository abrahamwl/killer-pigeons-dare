package net.bithaven.efficiencyrpg.ability.abilities;

import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.Hooked;
import net.bithaven.efficiencyrpg.ability.MovementPassabilityModifier;
import net.bithaven.efficiencyrpg.ability.TriggersOnMove;
import net.bithaven.efficiencyrpg.action.ActionMove;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.Water;
import net.bithaven.efficiencyrpg.entity.features.StatusEffect;


public class AbilitySwimming extends Ability implements TriggersOnMove {
	
	public AbilitySwimming() {
		super(	"Swimming",
				"<Name> may move slowly through water.",
				13, 18);
		movementPassabilityModifier = MovementPassabilityModifier.SWIMMING;
	}

	@Override
	protected Instance getNewInstance(Actor a) {
		return new Instance(a);
	}

	public int getPriority(Class<? extends Hooked> c) {
		return -1;
	}

	public void move(Actor a, ActionMove move) {
		if (!a.room.entitiesAt(a.x, a.y, Water.class).isEmpty() && !a.activeAbilities.getPassabilityModifiers().contains(MovementPassabilityModifier.FLYING)) {
			StatusEffect.apply(a, StatusEffect.Effect.STOPPED, 2f);
		}
	}
}
