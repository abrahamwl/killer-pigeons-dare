package net.bithaven.efficiencyrpg.controller;

import net.bithaven.efficiencyrpg.Dir;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.action.*;
import net.bithaven.efficiencyrpg.entity.Actor;

import org.newdawn.slick.GameContainer;

public class FlameoController extends BasicController {
	public FlameoController(Actor monster) {
		a = monster;
	}

	int spawnTickThreshold = 2; // Number of ticks before creating new flame
	int spawnTick = 0;
	
	int moveDistThreshold = 6;
	int spawnDistThreshold = 4;
	
	public Action chooseNextAction() {	
		Actor t = a.room.game.hero;

		int distToHero = Math.abs(a.x - t.x) + Math.abs(a.y - t.y);
		
		if(distToHero > moveDistThreshold) return AttackController.chooseMovement(a.room, a, t); // TODO Shouldn't use MoveAttack...
		
		spawnTick = (spawnTick + 1) % spawnDistThreshold;
		if(distToHero > spawnDistThreshold && spawnTick == 0) return spawnFlame(a.room, t);
		
		return new ActionBurn();
	}
	
	private Action spawnFlame(Room room, Actor t) {
		int distx = a.x - t.x;
		int disty = a.y - t.y;
		int distToTarget = Math.abs(a.x - t.x) + Math.abs(a.y - t.y);
		int testDist = 0;

		Dir currDir = null;
		int currpoint = 0;
		boolean dirEquidist[] = new boolean[9]; 
		int pointx[] = new int[9];
		int pointy[] = new int[9];
		
		System.out.println("------"); // DEBUG
		System.out.println(t.x); // DEBUG
		System.out.println(t.y); // DEBUG
		System.out.println(distx); // DEBUG
		System.out.println(disty); // DEBUG
		System.out.println(distToTarget); // DEBUG
		
		// Find equidistance directions
		for(int i = 0; i < Dir.values().length; i++, currpoint++) {
			currDir = Dir.values()[i]; 
			if(currDir == Dir.NO_DIRECTION) continue;
				pointx[i] = a.x + currDir.x;
				pointy[i] = a.y + currDir.y;

				System.out.println(currDir); // DEBUG
				System.out.println(pointx[i]); // DEBUG
				System.out.println(pointy[i]); // DEBUG
				
				testDist = Math.abs(pointx[i] - t.x) + Math.abs(pointy[i] - t.y);
				
				System.out.println(testDist); // DEBUG
				
				dirEquidist[currpoint] = false;
				if(testDist == distToTarget) 
					dirEquidist[currpoint] = true;
			}				
		
		// Spawn flame in first equidistance point that doesn't have flame
		for(int i = 0; i < 9; i++)
			if(dirEquidist[i] && 
					room.checkForPassableAt(pointx[i], pointy[i], a)) 
				return new ActionSpawn(Dir.values()[i], "F");
		
		return new ActionWait();
	}
}
