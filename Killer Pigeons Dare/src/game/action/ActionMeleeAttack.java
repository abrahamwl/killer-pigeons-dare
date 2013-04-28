package game.action;

import java.util.ArrayList;

import game.*;

public class ActionMeleeAttack extends ActionAttack {
	public static final int DAMAGE_PER_LEVEL = 5;
	public Dir dir;

	public ActionMeleeAttack(Dir dir) {
		this.dir = dir;
	}

	@Override
	public void execute(Room r, Actor a) {
		ArrayList<Entity> target = r.entitiesAt(a.x + dir.x, a.y + dir.y, Actor.class);

		if (target.size() > 0) {
			((Actor)(target.get(0))).applyDamage(a.getLevel() * DAMAGE_PER_LEVEL);
		}
	}
}
