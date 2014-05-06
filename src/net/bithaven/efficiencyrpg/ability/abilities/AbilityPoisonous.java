package net.bithaven.efficiencyrpg.ability.abilities;

import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.Hooked;
import net.bithaven.efficiencyrpg.ability.TriggersOnMeleeHit;
import net.bithaven.efficiencyrpg.action.ActionMeleeAttack;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.features.StatusEffect;


public class AbilityPoisonous extends Ability implements TriggersOnMeleeHit {
	public AbilityPoisonous() {
		super(	"Poisonous",
				"<Name>'s melee attacks apply <its> level x 2 points of poison to the victim.\nEach point of poison does 1 damage a turn.",
				2, 24);
	}

	public int getPriority(Class<? extends Hooked> c) {
		return 0;
	}

	public void hit(Actor a, ActionMeleeAttack attack, Actor victim) {
		StatusEffect.apply(victim, StatusEffect.Effect.POISONED, a.getLevel() * 2);
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
