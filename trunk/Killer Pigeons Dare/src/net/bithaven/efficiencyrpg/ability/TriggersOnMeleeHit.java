package net.bithaven.efficiencyrpg.ability;

import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.action.ActionMeleeAttack;
import net.bithaven.efficiencyrpg.entity.Actor;

public interface TriggersOnMeleeHit extends Hooked {
	public void hit (ActionMeleeAttack attack, Room r, Actor victim);
}
