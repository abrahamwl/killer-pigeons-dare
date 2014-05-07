package net.bithaven.efficiencyrpg.entity;

import net.bithaven.efficiencyrpg.ability.Hooked;
import net.bithaven.efficiencyrpg.ability.MovementPassabilityModifier;
import net.bithaven.efficiencyrpg.ability.TriggersOnMovedOn;
import net.bithaven.efficiencyrpg.ability.abilities.AbilitySoulFrozen;
import net.bithaven.efficiencyrpg.action.ActionMove;
import net.bithaven.efficiencyrpg.entity.features.Damage;
import net.bithaven.efficiencyrpg.entity.features.StatusEffect;
import net.bithaven.efficiencyrpg.event.DamageEvent;

public class Ice extends Entity implements TriggersOnMovedOn {
	public Ice () {
		super("Ice", "res/open1/dc-dngn/water/dngn_shoals_shallow_water1.png", Layer.GROUND);
	}

	@Override
	public boolean execute() {
		for(Actor a : room.entitiesAt(this.x, this.y, Actor.class)) {
			if(!a.activeAbilities.getPassabilityModifiers().contains(MovementPassabilityModifier.FLYING)) {
				room.game.events.add(new DamageEvent(null, null, a, new Damage(4, Damage.Type.COLD), null));
			}
		}
		
		return true;
	}

	@Override
	public boolean passableFor(Actor a) {
		return true;
	}

	public int getPriority(Class<? extends Hooked> c) {
		return -1;
	}

	public void move(Actor a, ActionMove move) {
		if (!a.activeAbilities.getPassabilityModifiers().contains(MovementPassabilityModifier.FLYING) &&
				a.activeAbilities.getFirst(AbilitySoulFrozen.class) == null) {
			StatusEffect.apply(a, StatusEffect.Effect.STOPPED, 2f);
		}
	}

}
