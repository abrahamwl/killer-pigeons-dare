package net.bithaven.efficiencyrpg.ability;

import net.bithaven.efficiencyrpg.entity.features.Damage;

public interface HandlesDamaged extends Handler {
	public Damage modifyDamage (Damage damage);
}
