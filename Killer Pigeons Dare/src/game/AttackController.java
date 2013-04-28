package game;

import game.action.*;
import game.entity.Character;
import game.entity.Wall;

import org.newdawn.slick.GameContainer;

public class AttackController implements Controller {
	Actor m;
	
	public AttackController (Actor monster) {
		m = monster;
	}
	
	private Action moveAttack (Room room, Dir dir) {
		if (!room.checkForTypeAt(m.x + dir.x, m.y + dir.y, Actor.class)) {
			return new ActionMove(dir);
		} else {
			return new ActionMeleeAttack(dir);
		}
	}

	@Override
	public Action chooseNextAction(Room room, GameContainer gc) {
		Character h = room.game.hero;
		
		if (m.x > h.x) {
			if (m.y > h.y) {
				if (!room.checkForTypeAt(m.x - 1, m.y - 1, Wall.class)) {
					return moveAttack(room, Dir.NORTH_WEST);
				} else {
					if (m.x - h.x > m.y - h.y) {
						if (!room.checkForTypeAt(m.x - 1, m.y, Wall.class)) {
							return moveAttack(room, Dir.WEST);
						} else {
							return moveAttack(room, Dir.NORTH);
						}
					} else {
						if (!room.checkForTypeAt(m.x, m.y - 1, Wall.class)) {
							return moveAttack(room, Dir.NORTH);
						} else {
							return moveAttack(room, Dir.WEST);
						}
					}
				}
			} else if (m.y < h.y) {
				if (!room.checkForTypeAt(m.x - 1, m.y + 1, Wall.class)) {
					return moveAttack(room, Dir.SOUTH_WEST);
				} else {
					if (m.x - h.x > h.y - m.y) {
						if (!room.checkForTypeAt(m.x - 1, m.y, Wall.class)) {
							return moveAttack(room, Dir.WEST);
						} else {
							return moveAttack(room, Dir.SOUTH);
						}
					} else {
						if (!room.checkForTypeAt(m.x, m.y + 1, Wall.class)) {
							return moveAttack(room, Dir.SOUTH);
						} else {
							return moveAttack(room, Dir.WEST);
						}
					}
				}
			} else {
				return moveAttack(room, Dir.WEST);
			}
		} else if (m.x < h.x) {
			if (m.y > h.y) {
				if (!room.checkForTypeAt(m.x + 1, m.y - 1, Wall.class)) {
					return moveAttack(room, Dir.NORTH_EAST);
				} else {
					if (h.x - m.x > m.y - h.y) {
						if (!room.checkForTypeAt(m.x + 1, m.y, Wall.class)) {
							return moveAttack(room, Dir.EAST);
						} else {
							return moveAttack(room, Dir.NORTH);
						}
					} else {
						if (!room.checkForTypeAt(m.x, m.y - 1, Wall.class)) {
							return moveAttack(room, Dir.NORTH);
						} else {
							return moveAttack(room, Dir.EAST);
						}
					}
				}
			} else if (m.y < h.y) {
				if (!room.checkForTypeAt(m.x + 1, m.y + 1, Wall.class)) {
					return moveAttack(room, Dir.SOUTH_EAST);
				} else {
					if (h.x - m.x > h.y - m.y) {
						if (!room.checkForTypeAt(m.x+ 1, m.y, Wall.class)) {
							return moveAttack(room, Dir.EAST);
						} else {
							return moveAttack(room, Dir.SOUTH);
						}
					} else {
						if (!room.checkForTypeAt(m.x, m.y + 1, Wall.class)) {
							return moveAttack(room, Dir.SOUTH);
						} else {
							return moveAttack(room, Dir.EAST);
						}
					}
				}
			} else {
				return moveAttack(room, Dir.EAST);
			}
		} else {
			if (m.y > h.y) {
				return moveAttack(room, Dir.NORTH);
			} else if (m.y < h.y) {
				return moveAttack(room, Dir.SOUTH);
			} else {
				return new ActionWait();
			}
		}
	}
}
