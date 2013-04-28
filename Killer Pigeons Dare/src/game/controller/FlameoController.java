package game.controller;

import game.Actor;
import game.BasicController;
import game.Dir;
import game.Room;
import game.action.*;
import game.entity.Character;
import game.entity.Flameo;
import game.entity.Wall;

import org.newdawn.slick.GameContainer;

public class FlameoController extends AttackController {
	public FlameoController(Actor monster) {
		super(monster);
	}

	int flameSpawnTicks = 4; // Number of ticks before creating new flame
	int flameTime = 10; // How long a flame lives
	
	@Override
	public Action chooseNextAction(Room room, GameContainer gc) {	
		Actor t = room.game.hero;

		flameTime--; if(flameTime == 0) a.kill();
		
		int distToHero = Math.abs(a.x - t.x) + Math.abs(a.y - t.y);
		
		if(distToHero > 10) return chooseMovement(room, t); // TODO Shouldn't use MoveAttack...
		
		if(distToHero > 5) return spawnFlame(room, t);
		
		return new ActionBurn();
	}
	
	private Action spawnFlame(Room room, Actor t) {
		int distx = a.x - t.x;
		int disty = a.y - t.y;
		int distToTarget = Math.abs(a.x - t.x) + Math.abs(a.y - t.y);
		int testDist = 0;

		int currpoint = 0;
		boolean dirEquidist[] = new boolean[9]; 
		int pointx[] = new int[9];
		int pointy[] = new int[9];
		
		// Find equidistance directions
		for(int i = 0; i < Dir.values().length; i++) {
				pointx[i] = a.x + Dir.values()[i].x;
				pointy[i] = a.y + Dir.values()[i].y;
				testDist = Math.abs(pointx[i] - t.x) + Math.abs(pointy[i] - t.y);
				dirEquidist[currpoint] = false;
				if(testDist == distToTarget) dirEquidist[currpoint] = true;
				currpoint++;
			}				
		
		// Spawn flame in first equidistance point that doesn't have flame
		for(int i = 0; i < 9; i++)
			if(dirEquidist[i] && 
					!room.checkForTypeAt(pointx[i], pointy[i], Flameo.class) && 
					room.checkForPassableAt(pointx[i], pointy[i], a)) 
				return new ActionSpawn(Dir.values()[i], "Flameo");
		
		return new ActionWait();
	}
}
