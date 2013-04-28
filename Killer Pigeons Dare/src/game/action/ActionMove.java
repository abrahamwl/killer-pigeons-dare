package game.action;

import game.*;

public class ActionMove extends Action {
	public Dir dir;
	
	public ActionMove (Dir dir) {
		this.dir = dir;
	}

	@Override
	public void execute(Room r, Actor a) {
		if (r.checkForPassableAt(a.x + dir.x, a.y + dir.y, a)) {
			a.x += dir.x;
			a.y += dir.y;
		}
	}
}
