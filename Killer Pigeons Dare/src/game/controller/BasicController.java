package game.controller;

import game.Actor;
import game.Controller;
import game.Dir;
import game.Room;
import game.action.Action;
import game.action.ActionMeleeAttack;
import game.action.ActionMove;

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
