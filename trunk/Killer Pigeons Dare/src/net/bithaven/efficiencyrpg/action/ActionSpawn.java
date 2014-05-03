package net.bithaven.efficiencyrpg.action;

import net.bithaven.efficiencyrpg.*;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.Wall;

public class ActionSpawn extends Action {
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
