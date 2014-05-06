package net.bithaven.efficiencyrpg.event;

import java.util.LinkedList;

import net.bithaven.efficiencyrpg.Game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class Event {
	public LinkedList<Event> nextEvents = new LinkedList<Event>();
	protected LinkedList<Event> subEvents = new LinkedList<Event>();
	private LinkedList<Event> removeEvents = new LinkedList<Event>();
	
	public abstract void render(Game game, Graphics g)  throws SlickException;

	public abstract void update(Game game, int timePassed)  throws SlickException;

	public abstract EventState getMyEventState ();
	
	public final EventState getEventState() {
		EventState step = getMyEventState();
		for (Event sub : subEvents) {
			EventState subState = sub.getEventState();
			if (subState == EventState.DONE) {
				removeEvents.add(sub);
			} else if (subState.ordinal() < step.ordinal()) {
				step = subState;
			}
		}
		subEvents.removeAll(removeEvents);
		removeEvents.clear();
		return step;
	}
	
	public enum EventState {
		// Order is important here.
		// This is designed to move irreversibly from PREVENT_LOGIC to DONE;
		PREVENT_TURN,
		ALLOW_TURN,
		DONE;
	}
}
