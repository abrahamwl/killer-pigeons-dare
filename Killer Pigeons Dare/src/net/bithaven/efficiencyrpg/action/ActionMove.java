package net.bithaven.efficiencyrpg.action;

import net.bithaven.efficiencyrpg.*;
import net.bithaven.efficiencyrpg.entity.Actor;

public class ActionMove extends Action {
	public Dir dir;
	
	public ActionMove (Dir dir) {
		this.dir = dir;
	}

	@Override
	public void execute(Actor a) {
		if (a.room.checkForPassableAt(a.x + dir.x, a.y + dir.y, a)) {
			a.x += dir.x;
			a.y += dir.y;
		}
	}
}
