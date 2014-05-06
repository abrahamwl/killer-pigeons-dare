package net.bithaven.efficiencyrpg.action;

import net.bithaven.efficiencyrpg.*;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.features.StatusEffect;

public class ActionMove extends Action {
	public Dir dir;
	
	public ActionMove (Dir dir) {
		this.dir = dir;
	}

	@Override
	public void execute(Actor a) {
		if (checkValidityOf(a) != Validity.INVALID) {
			a.x += dir.x;
			a.y += dir.y;
		}
	}

	@Override
	public Validity checkValidityOf(Actor a) {
		if (a.statusEffects.get(StatusEffect.Effect.STOPPED) != null) {
			return Validity.INVALID;
		}
		if (a.room.checkForPassableAt(a.x + dir.x, a.y + dir.y, a)) {
			return Validity.OKAY;
		}
		return Validity.INVALID;
	}
}
