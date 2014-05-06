package net.bithaven.efficiencyrpg.ability;

import net.bithaven.efficiencyrpg.action.ActionMeleeAttack;
import net.bithaven.efficiencyrpg.entity.Actor;

public interface ConsumesMeleeAttacked extends Consumer {
	public void attacked(Actor a, ActionMeleeAttack attack, Actor attacker);
}
