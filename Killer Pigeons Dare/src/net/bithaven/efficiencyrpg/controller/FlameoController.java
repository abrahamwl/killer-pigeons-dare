package net.bithaven.efficiencyrpg.controller;

import net.bithaven.efficiencyrpg.Dir;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.action.*;
import net.bithaven.efficiencyrpg.entity.Actor;

public class FlameoController extends BasicController {
	public FlameoController(Actor monster) {
		a = monster;
	}

	int spawnTickThreshold = 2; // Number of ticks before creating new flame
	int spawnTick = 0;
	
	int moveDistThreshold = 6;
	int spawnDistThreshold = 1;
	
	public Action chooseNextAction() {	
		Actor t = a.room.game.hero;

		int distToHero = Math.abs(a.x - t.x) + Math.abs(a.y - t.y);
		
		if(distToHero > moveDistThreshold) return AttackController.chooseMovement(a.room, a, t); // TODO Shouldn't use MoveAttack...
		
		spawnTick = (spawnTick + 1) % (spawnDistThreshold + spawnTickThreshold);
		if(distToHero > spawnDistThreshold && spawnTick == 0) return spawnFlame(a.room, t);
		
		return new ActionBurn();
	}
	
	private Action spawnFlame(Room room, Actor t) {
		int distToTarget = Math.abs(a.x - t.x) + Math.abs(a.y - t.y);
		int testDist = 0;

		int pointx = 0;
		int pointy = 0;
		
		// Find equidistance directions
		for(Dir currDir : Dir.compass) {
			pointx = a.x + currDir.x;
			pointy = a.y + currDir.y;
			
			testDist = Math.abs(pointx - t.x) + Math.abs(pointy - t.y);
			
			if(testDist == distToTarget && 
				room.checkForPassableAt(pointx, pointy, a)) 
				return new ActionSpawnFlameo(currDir);
		}				
		
		return new ActionWait();
	}
}
