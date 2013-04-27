package game;

import game.action.*;;

// The Actor class is the class inherited by all "characters" in the game. That is, the player character, all monsters, 
// and other NPCs.
public class Actor extends Entity {
	Controller controller;

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
