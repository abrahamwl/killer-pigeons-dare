package net.bithaven.efficiencyrpg.controller;

import net.bithaven.efficiencyrpg.Dir;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.ability.ActivatedAbility.Status;
import net.bithaven.efficiencyrpg.ability.abilities.AbilitySummonHellstone;
import net.bithaven.efficiencyrpg.action.*;
import net.bithaven.efficiencyrpg.entity.Actor;

public class FlameoController2 extends BasicController {
	public FlameoController2(Actor monster) {
		a = monster;
	}

	public Action chooseNextAction() {	
		Actor t = a.room.game.hero;

		int distToHero = Math.max(Math.abs(a.x - t.x), Math.abs(a.y - t.y));
		
		AbilitySummonHellstone summonHellstone = a.abilities.getFirst(AbilitySummonHellstone.class);

		if (summonHellstone != null) {
			if(distToHero > 2) return spawnHellstone(a.room, t, summonHellstone, false);
			return spawnHellstone(a.room, t, summonHellstone, true);
		}
		
		return AttackController.chooseMovement(a.room, a, t);
	}
	
	private Action spawnHellstoneAt(Room room, int x, int y, AbilitySummonHellstone sH, boolean hellstoneFirst) {
		if (hellstoneFirst && sH.getStatusOf(a, x, y) != Status.INVALID) {
			return new ActionActivateAbility(sH, x, y);
		} else if (room.game.hero.x == x && room.game.hero.y == y) {
			return new ActionMeleeAttack(Dir.fromXY(x - a.x, y - a.y));			
		} else if (room.checkForPassableAt(x, y, a)) {
			return new ActionMove(Dir.fromXY(x - a.x, y - a.y));
		} else if (!hellstoneFirst && sH.getStatusOf(a, x, y) != Status.INVALID) {
				return new ActionActivateAbility(sH, x, y);
		} else return null;
	}

	private Action spawnHellstone(Room room, Actor t, AbilitySummonHellstone sH, boolean hellstoneFirst) {
		Action out = null;
		if (a.x > t.x) {
			if (a.y > t.y) {
				out = spawnHellstoneAt(room, a.x - 1, a.y - 1, sH, hellstoneFirst);
				if (out != null) {
					return out;
				} else {
					if (a.x - t.x > a.y - t.y) {
						out = spawnHellstoneAt(room, a.x - 1, a.y, sH, hellstoneFirst);
						if (out != null) {
							return out;
						} else {
							out = spawnHellstoneAt(room, a.x, a.y - 1, sH, hellstoneFirst);
							if (out != null) return out; else return new ActionWait();
						}
					} else {
						out = spawnHellstoneAt(room, a.x, a.y - 1, sH, hellstoneFirst);
						if (out != null) {
							return out;
						} else {
							out = spawnHellstoneAt(room, a.x - 1, a.y, sH, hellstoneFirst);
							if (out != null) return out; else return new ActionWait();
						}
					}
				}
			} else if (a.y < t.y) {
				out = spawnHellstoneAt(room, a.x - 1, a.y + 1, sH, hellstoneFirst);
				if (out != null) {
					return out;
				} else {
					if (a.x - t.x > t.y - a.y) {
						out = spawnHellstoneAt(room, a.x - 1, a.y, sH, hellstoneFirst);
						if (out != null) {
							return out;
						} else {
							out = spawnHellstoneAt(room, a.x, a.y + 1, sH, hellstoneFirst);
							if (out != null) return out; else return new ActionWait();
						}
					} else {
						out = spawnHellstoneAt(room, a.x, a.y + 1, sH, hellstoneFirst);
						if (out != null) {
							return out;
						} else {
							out = spawnHellstoneAt(room, a.x - 1, a.y, sH, hellstoneFirst);
							if (out != null) return out; else return new ActionWait();
						}
					}
				}
			} else {
				out = spawnHellstoneAt(room, a.x - 1, a.y, sH, hellstoneFirst);
				if (out != null) return out; else return new ActionWait();
			}
		} else if (a.x < t.x) {
			if (a.y > t.y) {
				out = spawnHellstoneAt(room, a.x + 1, a.y - 1, sH, hellstoneFirst);
				if (out != null) {
					return out;
				} else {
					if (t.x - a.x > a.y - t.y) {
						out = spawnHellstoneAt(room, a.x + 1, a.y, sH, hellstoneFirst);
						if (out != null) {
							return out;
						} else {
							out = spawnHellstoneAt(room, a.x, a.y - 1, sH, hellstoneFirst);
							if (out != null) return out; else return new ActionWait();
						}
					} else {
						out = spawnHellstoneAt(room, a.x, a.y - 1, sH, hellstoneFirst);
						if (out != null) {
							return out;
						} else {
							out = spawnHellstoneAt(room, a.x + 1, a.y, sH, hellstoneFirst);
							if (out != null) return out; else return new ActionWait();
						}
					}
				}
			} else if (a.y < t.y) {
				out = spawnHellstoneAt(room, a.x + 1, a.y + 1, sH, hellstoneFirst);
				if (out != null) {
					return out;
				} else {
					if (t.x - a.x > t.y - a.y) {
						out = spawnHellstoneAt(room, a.x + 1, a.y, sH, hellstoneFirst);
						if (out != null) {
							return out;
						} else {
							out = spawnHellstoneAt(room, a.x, a.y + 1, sH, hellstoneFirst);
							if (out != null) return out; else return new ActionWait();
						}
					} else {
						out = spawnHellstoneAt(room, a.x, a.y + 1, sH, hellstoneFirst);
						if (out != null) {
							return out;
						} else {
							out = spawnHellstoneAt(room, a.x + 1, a.y, sH, hellstoneFirst);
							if (out != null) return out; else return new ActionWait();
						}
					}
				}
			} else {
				out = spawnHellstoneAt(room, a.x + 1, a.y, sH, hellstoneFirst);
				if (out != null) return out; else return new ActionWait();
			}
		} else {
			if (a.y > t.y) {
				out = spawnHellstoneAt(room, a.x, a.y - 1, sH, hellstoneFirst);
				if (out != null) return out; else return new ActionWait();
			} else if (a.y < t.y) {
				out = spawnHellstoneAt(room, a.x, a.y + 1, sH, hellstoneFirst);
				if (out != null) return out; else return new ActionWait();
			} else {
				return new ActionWait();
			}
		}
	}
}
