package game.action;

import java.util.ArrayList;

import game.*;

public class ActionBurn extends ActionAttack {
	public static final int DAMAGE_PER_LEVEL = 2;
	public Dir dir;

	public ActionBurn() {
	}

	@Override
	public void execute(Room r, Actor a) {
		ArrayList<Entity> target = r.entitiesAt(a.x, a.y, Actor.class);

		if (target.size() > 0) {
			((Actor)(target.get(0))).applyDamage(a.getLevel() * DAMAGE_PER_LEVEL);
		}
	}
}