package net.bithaven.efficiencyrpg.action;

import net.bithaven.efficiencyrpg.ability.AbilityInterface;
import net.bithaven.efficiencyrpg.entity.Actor;

public class ActionWait extends Action {
	@Override
	public void execute(Actor a) {
		for (AbilityInterface ability : a.abilities) {
			ability.on(a).doWait();
		}
	}

	@Override
	public Validity checkValidityOf(Actor a) {
		return Validity.OKAY;
	}
}
