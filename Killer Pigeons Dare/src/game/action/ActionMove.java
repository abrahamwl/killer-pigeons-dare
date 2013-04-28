package game.action;

import game.*;
import game.entity.Wall;

public class ActionMove extends Action {
	public Dir dir;
	
	public ActionMove (Dir dir) {
		this.dir = dir;
	}

	@Override
	public void execute(Room r, Actor a) {
		if (r.checkForTypeAt(a.x + dir.x, a.y + dir.y, Wall.class)) {
		} else {
			a.x += dir.x;
			a.y += dir.y;
		}
	}
}
