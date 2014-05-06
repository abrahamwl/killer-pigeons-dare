package net.bithaven.efficiencyrpg.ability;

import net.bithaven.efficiencyrpg.entity.Actor;

public interface TriggersAfterInit extends Hooked {
	public void afterInit(Actor a);
}
