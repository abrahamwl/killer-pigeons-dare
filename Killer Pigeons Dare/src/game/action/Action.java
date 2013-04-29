package game.action;

import game.*;
import game.entity.Actor;

public abstract class Action {
	public abstract void execute (Room r, Actor a);
}
