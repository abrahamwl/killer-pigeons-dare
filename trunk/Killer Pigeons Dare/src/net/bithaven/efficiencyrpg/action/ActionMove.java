package net.bithaven.efficiencyrpg.action;

import net.bithaven.efficiencyrpg.Dir;
import net.bithaven.efficiencyrpg.ability.PriorityComparator;
import net.bithaven.efficiencyrpg.ability.TriggersOnMove;
import net.bithaven.efficiencyrpg.ability.TriggersOnMovedOn;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.features.StatusEffect;

import com.google.common.collect.TreeMultiset;

public class ActionMove extends Action {
	public Dir dir;
	
	public ActionMove (Dir dir) {
		this.dir = dir;
	}

	@Override
	public void execute(Actor a) {
		if (checkValidityOf(a) != Validity.INVALID) {
			a.x += dir.x;
			a.y += dir.y;
			for (TriggersOnMove ability : a.activeAbilities.getPrioritizedSet(TriggersOnMove.class)) {
				ability.move(a, this);
			}

			TreeMultiset<TriggersOnMovedOn> list = TreeMultiset.create(PriorityComparator.getComparator(TriggersOnMovedOn.class));

			for (Actor movedOn : a.room.entitiesAt(a.x, a.y, Actor.class)) {
				list.addAll(movedOn.activeAbilities.getAll(TriggersOnMovedOn.class));
			}
			list.addAll(a.room.entitiesAt(a.x, a.y, TriggersOnMovedOn.class));

			for(TriggersOnMovedOn movedOn : list) {
				movedOn.move(a, this);
			}
		}
	}

	@Override
	public Validity checkValidityOf(Actor a) {
		if (a.statusEffects.get(StatusEffect.Effect.STOPPED) != null) {
			return Validity.INVALID;
		}
		if (a.room.checkForPassableAt(a.x + dir.x, a.y + dir.y, a)) {
			return Validity.OKAY;
		}
		return Validity.INVALID;
	}
}
