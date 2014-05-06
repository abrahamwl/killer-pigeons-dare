package net.bithaven.efficiencyrpg.ability.abilities;

import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.HandlesDamaged;
import net.bithaven.efficiencyrpg.ability.Hooked;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.features.Damage;

public class AbilitySoulFrozen extends Ability implements HandlesDamaged {
	public AbilitySoulFrozen() {
		super(	"Frozen Soul",
				"<Name> is healed by cold damage instead of being damaged by it.\n<Name> takes double damage from fire damage.\n<Name> cannot have non-cold elemental abilities.",
				9, 26, Category.NATURE, Damage.Type.COLD);
	}

	public int getPriority(Class<? extends Hooked> c) {
		return 1;
	}

	public Damage modifyDamage(Damage damage) {
		if (damage.type == Damage.Type.COLD) {
			return new Damage(-1 * Math.abs(damage.amount), damage.type);
		}
		if (damage.type == Damage.Type.FIRE) {
			return new Damage(Math.max(2 * damage.amount, 0), damage.type);
		}
		
		return damage;
	}

	@Override
	protected Instance getNewInstance(Actor a) {
		return new Instance(a);
	}
}
