package game.controller;

import game.Dir;
import game.Room;
import game.action.*;
import game.entity.Actor;
import game.entity.Character;
import org.newdawn.slick.GameContainer;

public class AttackController extends BasicController {
	private int waitDistance = 0;
	
	public AttackController (Actor monster, int waitDistance) {
		this(monster);
		
		this.waitDistance = waitDistance;
	}
	
	public AttackController (Actor monster) {
		a = monster;
	}
	
	@Override
	public Action chooseNextAction(Room room, GameContainer gc) {
		Character h = room.game.hero;
		
		int dist = Math.max(Math.abs(a.x - h.x), Math.abs(a.y - h.y));
		
		if (dist <= 1) {
			return new ActionMeleeAttack(Dir.fromXY(h.x - a.x, h.y - a.y));
		}
		
		if (dist <= waitDistance) {
			return new ActionWait();
		}
	
		return chooseMovement(room, a, h);
	}

	static protected Action chooseMovement(Room room, Actor a, Actor t) {
		if (a.x > t.x) {
			if (a.y > t.y) {
				if (room.checkForPassableAt(a.x - 1, a.y - 1, a)) {
					return new ActionMove(Dir.NORTH_WEST);
				} else {
					if (a.x - t.x > a.y - t.y) {
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
			} else if (a.y < t.y) {
				if (room.checkForPassableAt(a.x - 1, a.y + 1, a)) {
					return new ActionMove(Dir.SOUTH_WEST);
				} else {
					if (a.x - t.x > t.y - a.y) {
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
		} else if (a.x < t.x) {
			if (a.y > t.y) {
				if (room.checkForPassableAt(a.x + 1, a.y - 1, a)) {
					return new ActionMove(Dir.NORTH_EAST);
				} else {
					if (t.x - a.x > a.y - t.y) {
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
			} else if (a.y < t.y) {
				if (room.checkForPassableAt(a.x + 1, a.y + 1, a)) {
					return new ActionMove(Dir.SOUTH_EAST);
				} else {
					if (t.x - a.x > t.y - a.y) {
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
			if (a.y > t.y) {
				return new ActionMove(Dir.NORTH);
			} else if (a.y < t.y) {
				return new ActionMove(Dir.SOUTH);
			} else {
				return new ActionWait();
			}
		}
	}
}