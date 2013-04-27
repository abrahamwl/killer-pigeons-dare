package game.action;

import game.*;

public class ActionMove extends Action {
	public Dir dir;

	@Override
	public void execute(Room r, Actor a) {
		switch (dir) {
		case NORTH:
			if (r.checkForTypeAt(a.x, a.y - 1, Wall.class)) {
			} else {
				a.y--;
			}
			break;
		case EAST:
			if (r.checkForTypeAt(a.x + 1, a.y, Wall.class)) {
			} else {
				a.x++;
			}
			break;
		case SOUTH:
			if (r.checkForTypeAt(a.x, a.y + 1, Wall.class)) {
			} else {
				a.y++;
			}
			break;
		case WEST:
			if (r.checkForTypeAt(a.x - 1, a.y, Wall.class)) {
			} else {
				a.x--;
			}
			break;
		}
	}
}
