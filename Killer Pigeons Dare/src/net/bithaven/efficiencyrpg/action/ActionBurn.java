package net.bithaven.efficiencyrpg.action;

import java.util.ArrayList;

import net.bithaven.efficiencyrpg.*;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.Entity;


public class ActionBurn extends ActionAttack {
	public static final int DAMAGE_PER_LEVEL = 2;
	public Dir dir;

	public ActionBurn() {
	}

	@Override
	public void execute(Actor a) {
		// Burn everything within a 1 block radius, including the burner
		for(Dir d : Dir.values())
			if(d != Dir.NO_DIRECTION) 
				for(Entity e : a.room.entitiesAt(a.x + d.x, a.y + d.y, Actor.class))
					((Actor)e).applyDamage(a.getLevel() * DAMAGE_PER_LEVEL);
	}
}
