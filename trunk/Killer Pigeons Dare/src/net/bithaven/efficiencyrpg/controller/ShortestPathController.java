package net.bithaven.efficiencyrpg.controller;

import net.bithaven.efficiencyrpg.Dir;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.action.Action;
import net.bithaven.efficiencyrpg.action.ActionMeleeAttack;
import net.bithaven.efficiencyrpg.action.ActionMove;
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
		Coord path = pf.pathCheck(a.x, a.y, t.x, t.y, r, a, 20);
		if(path == null) AttackController.chooseMovement(r, a, t);
		
		path = nextPos(a.x, a.y, path);
		int dirx = path.x - a.x;
		int diry = path.y - a.y;
		
		return new ActionMove(Dir.fromXY(dirx, diry));
	}
	
	private Coord nextPos(int x, int y, Coord path) {
		while(path != null) if(path.parent.x == x && path.parent.y == y) return path; else path = path.parent;
		return null;
	}
}
