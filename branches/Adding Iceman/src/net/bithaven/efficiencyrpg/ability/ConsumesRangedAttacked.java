package net.bithaven.efficiencyrpg.ability;

import net.bithaven.efficiencyrpg.action.ActionRangedAttack;
import net.bithaven.efficiencyrpg.entity.Actor;

public interface ConsumesRangedAttacked extends Consumer {
	public void attacked(Actor a, ActionRangedAttack attack, Actor attacker);
}
