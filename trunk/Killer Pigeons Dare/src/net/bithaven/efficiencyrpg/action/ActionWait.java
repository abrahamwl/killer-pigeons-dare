package net.bithaven.efficiencyrpg.action;

import net.bithaven.efficiencyrpg.*;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.entity.Actor;

public class ActionWait extends Action {
	@Override
	public void execute(Room r, Actor a) {
		for (Ability ability : a.abilities) {
			ability.doWait();
		}
	}
}
