package net.bithaven.efficiencyrpg.ability;

import net.bithaven.efficiencyrpg.action.ActionMove;
import net.bithaven.efficiencyrpg.entity.Actor;

public interface TriggersOnMove extends Hooked {
	public void move (Actor a, ActionMove move);
}
