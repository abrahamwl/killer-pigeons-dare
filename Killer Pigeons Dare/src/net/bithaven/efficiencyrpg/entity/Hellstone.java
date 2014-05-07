package net.bithaven.efficiencyrpg.entity;

import net.bithaven.efficiencyrpg.ability.MovementPassabilityModifier;
import net.bithaven.efficiencyrpg.entity.features.Damage;
import net.bithaven.efficiencyrpg.event.DamageEvent;

public class Hellstone extends Entity {
	public Hellstone () {
		super("Hellstone", "res/open1/dc-dngn/floor/rough_red0.png", Layer.GROUND);
	}

	@Override
	public boolean execute() {
		for(Actor a : room.entitiesAt(this.x, this.y, Actor.class))
			if(!a.activeAbilities.getPassabilityModifiers().contains(MovementPassabilityModifier.FLYING))
				room.game.events.add(new DamageEvent(null, null, a, new Damage(10, Damage.Type.FIRE), null));
		
		return true; //Doesn't do anything.
	}

	@Override
	public boolean passableFor(Actor a) {
		return true;
	}

}
