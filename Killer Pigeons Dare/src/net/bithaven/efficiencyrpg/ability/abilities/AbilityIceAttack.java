package net.bithaven.efficiencyrpg.ability.abilities;

import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.Hooked;
import net.bithaven.efficiencyrpg.ability.TriggersOnMeleeHit;
import net.bithaven.efficiencyrpg.action.ActionMeleeAttack;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.features.Damage;
import net.bithaven.efficiencyrpg.entity.features.StatusEffect;
import net.bithaven.efficiencyrpg.event.DamageEvent;


public class AbilityIceAttack extends Ability implements TriggersOnMeleeHit {
	public AbilityIceAttack() {
		super(	"Ice Attack",
				"<Name>'s melee attacks do <its> level x 3 extra points of cold damage and stop the target.",
				3, 28, Category.NORMAL, Damage.Type.COLD);
	}

	public int getPriority(Class<? extends Hooked> c) {
		return 0;
	}

	public void hit(Actor a, ActionMeleeAttack attack, Actor victim) {
		StatusEffect.apply(victim, StatusEffect.Effect.STOPPED);
		a.room.game.events.add(new DamageEvent(a, null, victim, new Damage(a.getLevel() * 3, Damage.Type.COLD), null));
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
