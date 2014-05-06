package net.bithaven.efficiencyrpg.ability.abilities;

import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.Hooked;
import net.bithaven.efficiencyrpg.ability.TriggersOnMove;
import net.bithaven.efficiencyrpg.action.ActionMove;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.features.StatusEffect;


public class AbilityNegSlow extends Ability implements TriggersOnMove {
	public AbilityNegSlow() {
		super(	"Slow",
				"When <name> moves, it cannot move the next turn.",
				9, 18, Category.NEGATIVE);
	}

	public void move(Actor a, ActionMove move) {
		StatusEffect.apply(a, StatusEffect.Effect.STOPPED, 2f);
	}
	
	public int getPriority(Class<? extends Hooked> c) {
		return -1;
	}

	public class Instance extends Ability.Instance {
		public Instance(Actor a) {
			super(a);
		}
	}

	@Override
	protected net.bithaven.efficiencyrpg.ability.Ability.Instance getNewInstance(
			Actor a) {
		return new Instance(a);
	}
}
