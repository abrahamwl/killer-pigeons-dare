package net.bithaven.efficiencyrpg.ability.abilities;

import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.ConsumesMeleeAttacked;
import net.bithaven.efficiencyrpg.ability.Hooked;
import net.bithaven.efficiencyrpg.action.ActionMeleeAttack;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.event.effect.RisingTextEffect;

import org.newdawn.slick.Color;


public class AbilityCounterWait extends Ability implements ConsumesMeleeAttacked {
	public AbilityCounterWait() {
		super(	"Counter Wait",
				"<Name> prevents the first melee attack against <it> in a turn and makes a melee attack back against the attacker.\nThis stacks with other abilities that prevent melee attacks.",
				5, 25);
	}

	public int getPriority(Class<? extends Hooked> c) {
		return 1;
	}

	public void attacked(Actor a, ActionMeleeAttack attack, Actor attacker) {
		attack.generateEvents(attacker);
		a.room.game.events.add(new RisingTextEffect("COUNTER", a, Color.blue));
		on(a).setEnabled(false);
		ActionMeleeAttack counter = new ActionMeleeAttack(attack.dir.flip());
		counter.execute(a);
	}
	
	public class Instance extends Ability.Instance {
		public Instance(Actor a) {
			super(a);
		}

		@Override
		public void doNewTurn() {
			setEnabled(false);
		}

		@Override
		public void doWait() {
			super.doWait();
			setEnabled(true);
		}
	}

	@Override
	protected net.bithaven.efficiencyrpg.ability.Ability.Instance getNewInstance(
			Actor a) {
		return new Instance(a);
	}
}
