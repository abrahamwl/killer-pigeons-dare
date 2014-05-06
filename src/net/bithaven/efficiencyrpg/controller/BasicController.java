package net.bithaven.efficiencyrpg.controller;

import net.bithaven.efficiencyrpg.Dir;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.action.Action;
import net.bithaven.efficiencyrpg.action.ActionMeleeAttack;
import net.bithaven.efficiencyrpg.action.ActionMove;
import net.bithaven.efficiencyrpg.entity.Actor;

public abstract class BasicController implements Controller {
	protected Actor a;
	
	protected Action moveAttack (Room room, Dir dir) {
		if (!room.checkForTypeAt(a.x + dir.x, a.y + dir.y, Actor.class)) {
			return new ActionMove(dir);
		} else {
			return new ActionMeleeAttack(dir);
		}
	}
}
