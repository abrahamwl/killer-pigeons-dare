package net.bithaven.efficiencyrpg.action;

import net.bithaven.efficiencyrpg.*;
import net.bithaven.efficiencyrpg.entity.Actor;

public abstract class Action {
	public abstract void execute (Actor a);
	public void generateEffects (Actor a) {
	}
}
