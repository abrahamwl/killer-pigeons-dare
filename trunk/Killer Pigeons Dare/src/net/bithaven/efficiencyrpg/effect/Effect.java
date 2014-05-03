package net.bithaven.efficiencyrpg.effect;

import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class Effect {
	protected LinkedList<Effect> childEffects = new LinkedList<Effect>();
	private LinkedList<Effect> removeEffects = new LinkedList<Effect>();
	
	public abstract void render(GameContainer gc, Graphics g)  throws SlickException;

	public abstract void update(GameContainer gc, int timePassed)  throws SlickException;

	public abstract LogicStep getMyLogicStep ();
	
	public final LogicStep getLogicStep() {
		LogicStep step = getMyLogicStep();
		for (Effect child : childEffects) {
			LogicStep childStep = child.getLogicStep();
			if (childStep == LogicStep.DONE) {
				removeEffects.add(child);
			} else if (childStep.ordinal() < step.ordinal()) {
				step = childStep;
			}
		}
		childEffects.removeAll(removeEffects);
		removeEffects.clear();
		return step;
	}
	
	public enum LogicStep {
		// Order is important here.
		// This is designed to move irreversibly from PREVENT_LOGIC to DONE;
		PREVENT_LOGIC,
		ALLOW_LOGIC,
		DONE;
	}
}
