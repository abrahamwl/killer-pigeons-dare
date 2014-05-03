package net.bithaven.efficiencyrpg.action;

import net.bithaven.efficiencyrpg.*;
import net.bithaven.efficiencyrpg.ability.ActivatedAbility;
import net.bithaven.efficiencyrpg.entity.*;

public class ActionActivateAbility extends Action {
	ActivatedAbility ability;
	int targetX, targetY;
	
	public ActionActivateAbility(ActivatedAbility ability, int targetX, int targetY) {
		this.ability = ability;
		this.targetX = targetX;
		this.targetY = targetY;
	}

	@Override
	public void execute(Actor a) {
		ability.activate(a, targetX, targetY);
	}
}
