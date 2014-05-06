package net.bithaven.efficiencyrpg.ability;

import net.bithaven.efficiencyrpg.other.Damage;

public interface HandlesDamaged extends Handler {
	public Damage modifyDamage (Damage damage);
}
