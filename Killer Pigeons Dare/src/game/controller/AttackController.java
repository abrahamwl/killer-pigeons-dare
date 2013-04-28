package game.controller;

import game.Actor;
import game.BasicController;
import game.Dir;
import game.Room;
import game.action.*;
import game.entity.Character;
import game.entity.Wall;

import org.newdawn.slick.GameContainer;

public class AttackController extends BasicController {
	public AttackController (Actor monster) {
		a = monster;
	}
	
	@Override
	public Action chooseNextAction(Room room, GameContainer gc) {
		Character h = room.game.hero;
		
		if (Math.abs(a.x - h.x) <= 1 && Math.abs(a.y - h.y) <= 1) {
			return new ActionMeleeAttack(Dir.fromXY(h.x - a.x, h.y - a.y));
		}
		
		if (a.x > h.x) {
			if (a.y > h.y) {
				if (room.checkForPassableAt(a.x - 1, a.y - 1, a)) {
					return new ActionMove(Dir.NORTH_WEST);
				} else {
					if (a.x - h.x > a.y - h.y) {
						if (room.checkForPassableAt(a.x - 1, a.y, a)) {
							return new ActionMove(Dir.WEST);
						} else {
							return new ActionMove(Dir.NORTH);
						}
					} else {
						if (room.checkForPassableAt(a.x, a.y - 1, a)) {
							return new ActionMove(Dir.NORTH);
						} else {
							return new ActionMove(Dir.WEST);
						}
					}
				}
			} else if (a.y < h.y) {
				if (room.checkForPassableAt(a.x - 1, a.y + 1, a)) {
					return new ActionMove(Dir.SOUTH_WEST);
				} else {
					if (a.x - h.x > h.y - a.y) {
						if (room.checkForPassableAt(a.x - 1, a.y, a)) {
							return new ActionMove(Dir.WEST);
						} else {
							return new ActionMove(Dir.SOUTH);
						}
					} else {
						if (room.checkForPassableAt(a.x, a.y + 1, a)) {
							return new ActionMove(Dir.SOUTH);
						} else {
							return new ActionMove(Dir.WEST);
						}
					}
				}
			} else {
				return new ActionMove(Dir.WEST);
			}
		} else if (a.x < h.x) {
			if (a.y > h.y) {
				if (room.checkForPassableAt(a.x + 1, a.y - 1, a)) {
					return new ActionMove(Dir.NORTH_EAST);
				} else {
					if (h.x - a.x > a.y - h.y) {
						if (room.checkForPassableAt(a.x + 1, a.y, a)) {
							return new ActionMove(Dir.EAST);
						} else {
							return new ActionMove(Dir.NORTH);
						}
					} else {
						if (room.checkForPassableAt(a.x, a.y - 1, a)) {
							return new ActionMove(Dir.NORTH);
						} else {
							return new ActionMove(Dir.EAST);
						}
					}
				}
			} else if (a.y < h.y) {
				if (room.checkForPassableAt(a.x + 1, a.y + 1, a)) {
					return new ActionMove(Dir.SOUTH_EAST);
				} else {
					if (h.x - a.x > h.y - a.y) {
						if (room.checkForPassableAt(a.x+ 1, a.y, a)) {
							return new ActionMove(Dir.EAST);
						} else {
							return new ActionMove(Dir.SOUTH);
						}
					} else {
						if (room.checkForPassableAt(a.x, a.y + 1, a)) {
							return new ActionMove(Dir.SOUTH);
						} else {
							return new ActionMove(Dir.EAST);
						}
					}
				}
			} else {
				return new ActionMove(Dir.EAST);
			}
		} else {
			if (a.y > h.y) {
				return new ActionMove(Dir.NORTH);
			} else if (a.y < h.y) {
				return new ActionMove(Dir.SOUTH);
			} else {
				return new ActionWait();
			}
		}
	}
}
