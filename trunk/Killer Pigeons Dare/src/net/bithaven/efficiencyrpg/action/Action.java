package net.bithaven.efficiencyrpg.action;

import net.bithaven.efficiencyrpg.*;
import net.bithaven.efficiencyrpg.entity.Actor;

public abstract class Action {
	public abstract void execute (Room r, Actor a);
	public void generateEffects (Room r, Actor a) {
	}
}
