package game.action;

import java.util.ArrayList;

import game.*;
import game.entity.Actor;
import game.entity.Entity;

public class ActionBurn extends ActionAttack {
	public static final int DAMAGE_PER_LEVEL = 2;
	public Dir dir;

	public ActionBurn() {
	}

	@Override
	public void execute(Room r, Actor a) {
		ArrayList<Entity> target = r.entitiesAt(a.x, a.y, Actor.class);

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
