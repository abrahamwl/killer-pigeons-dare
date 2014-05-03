package net.bithaven.efficiencyrpg.action;

import net.bithaven.efficiencyrpg.*;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.Wall;

public class ActionSpawnFlameo extends Action {
	public Dir dir;
	public String es = "F";

	public ActionSpawnFlameo(Dir dir) {
		this.dir = dir;
	}

	@Override
	public void execute(Actor a) {
		int newx = a.x + dir.x;
		int newy = a.y + dir.y;
		if(!a.room.checkForTypeAt(newx, newy, Wall.class)) {
			a.room.addEntity(es, newx, newy);
		}
	}
}
