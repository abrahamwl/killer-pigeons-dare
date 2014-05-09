package net.bithaven.efficiencyrpg.controller;

import net.bithaven.efficiencyrpg.Dir;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.action.Action;
import net.bithaven.efficiencyrpg.action.ActionMeleeAttack;
import net.bithaven.efficiencyrpg.action.ActionMove;
import net.bithaven.efficiencyrpg.action.ActionWait;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.Character;
import net.bithaven.efficiencyrpg.levelgenerator.Coord;
import net.bithaven.efficiencyrpg.levelgenerator.PathFinder;

public class ShortestPathController implements Controller {
	Actor a;
	
	public ShortestPathController (Actor monster) {a = monster;}
	
	public Action chooseNextAction() {
		Character h = a.room.game.hero;
		
		int dist = Math.max(Math.abs(a.x - h.x), Math.abs(a.y - h.y));		
		if (dist <= 1) return new ActionMeleeAttack(Dir.fromXY(h.x - a.x, h.y - a.y));
		return chooseMovement(a.room, a, h);
	}
	
	PathFinder pf = new PathFinder();
	
	public Action chooseMovement(Room r, Actor a, Actor t) {
		// Increase this to have the actor look harder for a path 
		int pathDifficulty = 50;
		Coord path = pf.pathCheck(a.x, a.y, t.x, t.y, r, a, pathDifficulty);
		if(path == null) chooseSimpleMovement(r, a, t);
		
		path = nextPos(a.x, a.y, path);
		int dirx = path.x - a.x;
		int diry = path.y - a.y;
		
		return new ActionMove(Dir.fromXY(dirx, diry));
	}
	
	private Coord nextPos(int x, int y, Coord path) {
		while(path != null) if(path.parent.x == x && path.parent.y == y) return path; else path = path.parent;
		return null;
	}
	
	public static Action chooseSimpleMovement(Room room, Actor a, Actor t) {
		Dir tryDir = null;
		double rO = Math.atan2(t.x - a.x, t.y - a.y);
		if(t.x - a.x == 0 && t.y - a.y == 0) {
			System.out.println("t.x: " + t.x + " a.x: " + a.x + " t.y: " + t.y + " a.y: " + a.y); // DEBUG
			return null; 
		}
		System.out.println("rO: " + rO); // DEBUG
		double r = rO + 0.0 * Math.PI;
		r = rO + 0.0 * Math.PI;
		tryDir = Dir.fromRadian(r); // Try straight
		if(room.checkForPassableAt(a.x + tryDir.x, a.y + tryDir.y, a)) return new ActionMove(tryDir); 
		r = rO + 0.25 * Math.PI;
		tryDir = Dir.fromRadian(r); // Try 1/4 turn to right
		if(room.checkForPassableAt(a.x + tryDir.x, a.y + tryDir.y, a)) return new ActionMove(tryDir); 
		r = rO - 0.25 * Math.PI;
		tryDir = Dir.fromRadian(r); // Try 1/4 turn to left
		if(room.checkForPassableAt(a.x + tryDir.x, a.y + tryDir.y, a)) return new ActionMove(tryDir); 
		r = rO + 0.5 * Math.PI;
		tryDir = Dir.fromRadian(r); // Try 1/2 turn to right
		if(room.checkForPassableAt(a.x + tryDir.x, a.y + tryDir.y, a)) return new ActionMove(tryDir); 
		r = rO - 0.5 * Math.PI;
		tryDir = Dir.fromRadian(r); // Try 1/2 turn to left
		if(room.checkForPassableAt(a.x + tryDir.x, a.y + tryDir.y, a)) return new ActionMove(tryDir);
		return new ActionWait(); // Just sit there
	}
}
