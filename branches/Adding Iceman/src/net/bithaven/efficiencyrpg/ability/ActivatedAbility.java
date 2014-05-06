package net.bithaven.efficiencyrpg.ability;

import net.bithaven.efficiencyrpg.action.Validity;
import net.bithaven.efficiencyrpg.entity.Actor;

public interface ActivatedAbility extends Hooked {
	public Validity checkValidityOf(Actor a, int x, int y);
	public void activate(Actor a, int x, int y);
}
