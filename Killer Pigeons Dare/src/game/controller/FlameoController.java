package game.controller;

import game.Actor;
import game.BasicController;
import game.Dir;
import game.Room;
import game.action.*;
import game.entity.Character;
import game.entity.Wall;

import org.newdawn.slick.GameContainer;

public class FlameoController extends BasicController {
	public FlameoController (Actor monster) {
		a = monster;
	}
	
	int flameSpawnTicks = 4; // Number of ticks before creating new flame
	int flameTime = 10; // How long a flame lives
	
	@Override
	public Action chooseNextAction(Room room, GameContainer gc) {	
		Actor t = room.game.hero;

		flameTime--;
		if(flameTime == 0) die();
		
		int distToHero = Math.abs(a.x - t.x) + Math.abs(a.y - t.y);
		
		if(distToHero > 10) return chooseMovement(room, t);
		
		if(distToHero > 5) return spawnFlame(room);
		
		return new ActionWait();
	}

	private void die() {
		// TODO
	}
	
	private Action spawnFlame(Room room) {
		// TODO
		
		return null;
	}
	
	// TODO remove moveAttack
	
	private Action chooseMovement(Room room, Actor t) {
		if (a.x > t.x) {
			if (a.y > t.y) {
				if (!room.checkForTypeAt(a.x - 1, a.y - 1, Wall.class)) {
					return moveAttack(room, Dir.NORTH_WEST);
				} else {
					if (a.x - t.x > a.y - t.y) {
						if (!room.checkForTypeAt(a.x - 1, a.y, Wall.class)) {
							return moveAttack(room, Dir.WEST);
						} else {
							return moveAttack(room, Dir.NORTH);
						}
					} else {
						if (!room.checkForTypeAt(a.x, a.y - 1, Wall.class)) {
							return moveAttack(room, Dir.NORTH);
						} else {
							return moveAttack(room, Dir.WEST);
						}
					}
				}
			} else if (a.y < t.y) {
				if (!room.checkForTypeAt(a.x - 1, a.y + 1, Wall.class)) {
					return moveAttack(room, Dir.SOUTH_WEST);
				} else {
					if (a.x - t.x > t.y - a.y) {
						if (!room.checkForTypeAt(a.x - 1, a.y, Wall.class)) {
							return moveAttack(room, Dir.WEST);
						} else {
							return moveAttack(room, Dir.SOUTH);
						}
					} else {
						if (!room.checkForTypeAt(a.x, a.y + 1, Wall.class)) {
							return moveAttack(room, Dir.SOUTH);
						} else {
							return moveAttack(room, Dir.WEST);
						}
					}
				}
			} else {
				return moveAttack(room, Dir.WEST);
			}
		} else if (a.x < t.x) {
			if (a.y > t.y) {
				if (!room.checkForTypeAt(a.x + 1, a.y - 1, Wall.class)) {
					return moveAttack(room, Dir.NORTH_EAST);
				} else {
					if (t.x - a.x > a.y - t.y) {
						if (!room.checkForTypeAt(a.x + 1, a.y, Wall.class)) {
							return moveAttack(room, Dir.EAST);
						} else {
							return moveAttack(room, Dir.NORTH);
						}
					} else {
						if (!room.checkForTypeAt(a.x, a.y - 1, Wall.class)) {
							return moveAttack(room, Dir.NORTH);
						} else {
							return moveAttack(room, Dir.EAST);
						}
					}
				}
			} else if (a.y < t.y) {
				if (!room.checkForTypeAt(a.x + 1, a.y + 1, Wall.class)) {
					return moveAttack(room, Dir.SOUTH_EAST);
				} else {
					if (t.x - a.x > t.y - a.y) {
						if (!room.checkForTypeAt(a.x+ 1, a.y, Wall.class)) {
							return moveAttack(room, Dir.EAST);
						} else {
							return moveAttack(room, Dir.SOUTH);
						}
					} else {
						if (!room.checkForTypeAt(a.x, a.y + 1, Wall.class)) {
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
			if (a.y > t.y) {
				return moveAttack(room, Dir.NORTH);
			} else if (a.y < t.y) {
				return moveAttack(room, Dir.SOUTH);
			} else {
				return new ActionWait();
			}
		}
	}
}
