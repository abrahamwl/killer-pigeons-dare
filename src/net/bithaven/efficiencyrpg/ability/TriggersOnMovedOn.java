package net.bithaven.efficiencyrpg.ability;

import net.bithaven.efficiencyrpg.action.ActionMove;
import net.bithaven.efficiencyrpg.entity.Actor;

public interface TriggersOnMovedOn extends Hooked {
	public void move (Actor a, ActionMove move);
}
