package game.action;

import game.*;
import game.entity.W;

public class ActionMove extends Action {
	public Dir dir;

	@Override
	public void execute(Room r, Actor a) {
		if (r.checkForTypeAt(a.x + dir.x, a.y + dir.y, W.class)) {
		} else {
			a.x += dir.x;
			a.y += dir.y;
		}
	}
}
