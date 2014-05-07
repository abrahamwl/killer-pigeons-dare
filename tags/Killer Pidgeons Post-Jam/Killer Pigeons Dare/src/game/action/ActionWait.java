package game.action;

import game.*;
import game.entity.Actor;

public class ActionWait extends Action {
	@Override
	public void execute(Room r, Actor a) {
		for (Ability ability : a.abilities) {
			if (ability.type == Ability.Type.COUNTER_WAIT) {
				ability.active = true;
			}
		}
	}
}