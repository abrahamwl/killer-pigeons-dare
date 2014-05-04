package net.bithaven.efficiencyrpg.ability.abilities;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.Hooked;
import net.bithaven.efficiencyrpg.ability.TriggersOnMeleeHit;
import net.bithaven.efficiencyrpg.action.ActionMeleeAttack;
import net.bithaven.efficiencyrpg.entity.Actor;


public class AbilityPoisonous extends Ability implements TriggersOnMeleeHit {
	public AbilityPoisonous() {
		super(	"Poisonous",
				"'s melee attacks cause its level x 2 damage per turn.",
				2, 24);
	}

	public int getPriority(Class<? extends Hooked> c) {
		return 0;
	}

	public void hit(Actor a, ActionMeleeAttack attack, Actor victim) {
		victim.poisoned += a.getLevel() * 2;
	}

	public class Instance extends Ability.Instance {
		public Instance(Actor a) {
			super(a);
		}

		@Override
		public String getDescription() {
			return a.name + "'s melee attacks cause " + a.level * 2 + " damage per turn.";
		}
	}

	@Override
	protected net.bithaven.efficiencyrpg.ability.Ability.Instance getNewInstance(
			Actor a) {
		return new Instance(a);
	}
}
