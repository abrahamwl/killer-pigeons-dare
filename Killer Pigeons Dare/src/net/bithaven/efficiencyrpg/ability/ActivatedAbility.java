package net.bithaven.efficiencyrpg.ability;

import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.entity.Actor;

public interface ActivatedAbility extends Hooked {
	public enum Status {
		OKAY,
		NOT_RECOMMENDED,
		INVALID;
	}
	
	public Status getStatusOf(Actor a, int x, int y);
	public void activate(Actor a, int x, int y);
}
