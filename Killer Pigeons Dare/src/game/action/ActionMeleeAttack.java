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
			Actor victim = (Actor)(target.get(0));
			for (Ability ability : victim.abilities) {
				if (ability.active) {
					if (ability.type == Ability.Type.COUNTER_WAIT) {
						ability.active = false;
						new ActionMeleeAttack(dir.flip()).execute(r, victim);
						return;
					}
					if (ability.type == Ability.Type.BLOCK) {
						ability.active = false;
						return;
					}
				}
			}
			victim.applyDamage(a.getLevel() * DAMAGE_PER_LEVEL);
		}
	}
}
