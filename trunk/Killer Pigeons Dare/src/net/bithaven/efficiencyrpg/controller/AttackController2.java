package net.bithaven.efficiencyrpg.controller;

import net.bithaven.efficiencyrpg.Dir;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.action.*;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.Character;

public class AttackController2 extends BasicController {
	private int waitDistance = 0;
	
	public AttackController2 (Actor monster, int waitDistance) {
		this(monster);
		
		this.waitDistance = waitDistance;
	}
	
	public AttackController2 (Actor monster) {
		a = monster;
	}
	
	public Action chooseNextAction() {
		Character h = a.room.game.hero;
		
		int dist = Math.max(Math.abs(a.x - h.x), Math.abs(a.y - h.y));
		
		if (dist <= 1) {
			return new ActionMeleeAttack(Dir.fromXY(h.x - a.x, h.y - a.y));
		}
		
		if (dist <= waitDistance) {
			return new ActionWait();
		}
	
		return chooseMovement(a.room, a, h);
	}

	public static Action chooseMovement(Room room, Actor a, Actor t) {
		Dir tryDir = null;
		double r = Math.atan2(t.y - a.y, t.x - a.x);
		tryDir = Dir.fromRadian(r);
		if(room.checkForPassableAt(a.x + tryDir.x, a.y + tryDir.y, a)) return new ActionMove(tryDir); 
		tryDir = Dir.fromRadian(r + 0.25 * Math.PI);
		if(room.checkForPassableAt(a.x + tryDir.x, a.y + tryDir.y, a)) return new ActionMove(tryDir); 
		tryDir = Dir.fromRadian(r - 0.25 * Math.PI);
		if(room.checkForPassableAt(a.x + tryDir.x, a.y + tryDir.y, a)) return new ActionMove(tryDir); 
		tryDir = Dir.fromRadian(r + 0.5 * Math.PI);
		if(room.checkForPassableAt(a.x + tryDir.x, a.y + tryDir.y, a)) return new ActionMove(tryDir); 
		tryDir = Dir.fromRadian(r - 0.5 * Math.PI);
		if(room.checkForPassableAt(a.x + tryDir.x, a.y + tryDir.y, a)) return new ActionMove(tryDir);
		return new ActionWait();
	}
}
