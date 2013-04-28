package game;

import game.action.*;

// The Actor class is the class inherited by all "characters" in the game. That is, the player character, all monsters, 
// and other NPCs.
public class Actor extends Entity {
	Controller controller;
	
	private int level;
	private int hitpoints;
	private boolean dead = false;
	
	public Actor (String name, int level) {
		super(name);
		hitpoints = 10 * level;
		this.level = level;
	}

	@Override
	public boolean execute(Room r) {
		Action a = controller.chooseNextAction(r, r.gc);
		
		if (a instanceof ActionNoneYet) {
			return false;
		} else {
			a.execute(r, this);
			return true;
		}
	}
}
