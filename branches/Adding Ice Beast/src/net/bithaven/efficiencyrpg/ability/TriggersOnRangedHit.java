package net.bithaven.efficiencyrpg.ability;

import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.action.ActionMeleeAttack;
import net.bithaven.efficiencyrpg.action.ActionRangedAttack;
import net.bithaven.efficiencyrpg.entity.Actor;

public interface TriggersOnRangedHit extends Hooked {
	public void hit (Actor a, ActionRangedAttack attack, Actor victim);
}
