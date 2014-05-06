package net.bithaven.efficiencyrpg.action;

import net.bithaven.efficiencyrpg.entity.Actor;

public abstract class Action {
	public void generateEvents (Actor a) {
	}
	
	public abstract Validity checkValidityOf(Actor a);

	public abstract void execute(Actor a);
}
