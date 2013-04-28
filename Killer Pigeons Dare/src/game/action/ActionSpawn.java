package game.action;

import java.util.ArrayList;

import game.*;
import game.entity.Wall;

public class ActionSpawn extends ActionAttack {
	public Dir dir;
	public String es;

	public ActionSpawn(Dir dir, String es) {
		this.dir = dir;
		this.es = es;
	}

	@Override
	public void execute(Room r, Actor a) {
		int newx = a.x + dir.x;
		int newy = a.y + dir.y;
		if(!r.checkForTypeAt(newx, newy, Wall.class)) {
			r.addEntity(es, newx, newy);
		}
		
		
	}
}
