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
		ArrayList<Actor> target = a.room.entitiesAt(a.x, a.y, Actor.class);

		if (target.size() > 0) {
			for(Entity actr : target)
			if (!actr.equals(a)){
				((Actor)(target.get(0))).applyDamage(a.getLevel() * DAMAGE_PER_LEVEL);
				return;
			}
			a.applyDamage(DAMAGE_PER_LEVEL); // If no other actor on flameo square, flameo burns himself to death 
		}
	}
}
