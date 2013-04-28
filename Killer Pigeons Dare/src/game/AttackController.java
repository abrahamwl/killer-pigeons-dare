package game;

import game.action.*;
import game.entity.C;
import game.entity.W;

import org.newdawn.slick.GameContainer;

public class AttackController implements Controller {
	Actor m;
	
	public AttackController (Actor monster) {
		m = monster;
	}

	@Override
	public Action chooseNextAction(Room room, GameContainer gc) {
		C h = room.game.hero;
		
		if (m.x > h.x) {
			if (m.y > h.y) {
				if (!room.checkForTypeAt(m.x - 1, m.y - 1, W.class)) {
					return new ActionMove() {{dir = Dir.NORTH_WEST;}};
				} else {
					if (m.x - h.x > m.y - h.y) {
						if (!room.checkForTypeAt(m.x - 1, m.y, W.class)) {
							return new ActionMove() {{dir = Dir.WEST;}};
						} else {
							return new ActionMove() {{dir = Dir.NORTH;}};
						}
					} else {
						if (!room.checkForTypeAt(m.x, m.y - 1, W.class)) {
							return new ActionMove() {{dir = Dir.NORTH;}};
						} else {
							return new ActionMove() {{dir = Dir.WEST;}};
						}
					}
				}
			} else if (m.y < h.y) {
				if (!room.checkForTypeAt(m.x - 1, m.y + 1, W.class)) {
					return new ActionMove() {{dir = Dir.SOUTH_WEST;}};
				} else {
					if (m.x - h.x > h.y - m.y) {
						if (!room.checkForTypeAt(m.x - 1, m.y, W.class)) {
							return new ActionMove() {{dir = Dir.WEST;}};
						} else {
							return new ActionMove() {{dir = Dir.SOUTH;}};
						}
					} else {
						if (!room.checkForTypeAt(m.x, m.y + 1, W.class)) {
							return new ActionMove() {{dir = Dir.SOUTH;}};
						} else {
							return new ActionMove() {{dir = Dir.WEST;}};
						}
					}
				}
			} else {
				return new ActionMove() {{dir = Dir.WEST;}};
			}
		} else if (m.x < h.x) {
			if (m.y > h.y) {
				if (!room.checkForTypeAt(m.x + 1, m.y - 1, W.class)) {
					return new ActionMove() {{dir = Dir.NORTH_EAST;}};
				} else {
					if (h.x - m.x > m.y - h.y) {
						if (!room.checkForTypeAt(m.x + 1, m.y, W.class)) {
							return new ActionMove() {{dir = Dir.EAST;}};
						} else {
							return new ActionMove() {{dir = Dir.NORTH;}};
						}
					} else {
						if (!room.checkForTypeAt(m.x, m.y - 1, W.class)) {
							return new ActionMove() {{dir = Dir.NORTH;}};
						} else {
							return new ActionMove() {{dir = Dir.EAST;}};
						}
					}
				}
			} else if (m.y < h.y) {
				if (!room.checkForTypeAt(m.x + 1, m.y + 1, W.class)) {
					return new ActionMove() {{dir = Dir.SOUTH_EAST;}};
				} else {
					if (h.x - m.x > h.y - m.y) {
						if (!room.checkForTypeAt(m.x+ 1, m.y, W.class)) {
							return new ActionMove() {{dir = Dir.EAST;}};
						} else {
							return new ActionMove() {{dir = Dir.SOUTH;}};
						}
					} else {
						if (!room.checkForTypeAt(m.x, m.y + 1, W.class)) {
							return new ActionMove() {{dir = Dir.SOUTH;}};
						} else {
							return new ActionMove() {{dir = Dir.EAST;}};
						}
					}
				}
			} else {
				return new ActionMove() {{dir = Dir.EAST;}};
			}
		} else {
			if (m.y > h.y) {
				return new ActionMove() {{dir = Dir.NORTH;}};
			} else if (m.y < h.y) {
				return new ActionMove() {{dir = Dir.SOUTH;}};
			} else {
				return new ActionWait();
			}
		}
	}
}
