package net.bithaven.efficiencyrpg.ability.abilities;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.ConsumesMeleeAttacked;
import net.bithaven.efficiencyrpg.ability.Hooked;
import net.bithaven.efficiencyrpg.action.ActionAttack;
import net.bithaven.efficiencyrpg.action.ActionMeleeAttack;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.event.effect.RisingTextEffect;

import org.apache.commons.lang3.text.WordUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;


public class AbilityCounterWait extends Ability implements ConsumesMeleeAttacked {
	public AbilityCounterWait() {
		super(	"Counter Wait",
				" prevents the first melee attack against it in a turn and makes a melee attack back against the attacker.\nThis stacks with other abilities that prevent melee attacks.",
				Game.iconSheet.getSprite(5, 25));
	}

	public int getPriority(Class<? extends Hooked> c) {
		return 1;
	}

	public void attacked(Actor a, ActionMeleeAttack attack, Actor attacker) {
		attack.generateEffects(attacker);
		a.room.game.effects.add(new RisingTextEffect("COUNTER", a.getCenterX(), a.getCenterY(), Color.blue));
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
