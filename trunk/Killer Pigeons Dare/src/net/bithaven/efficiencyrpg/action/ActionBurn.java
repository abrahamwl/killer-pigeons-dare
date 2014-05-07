package net.bithaven.efficiencyrpg.action;

import net.bithaven.efficiencyrpg.Dir;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.features.Damage;
import net.bithaven.efficiencyrpg.event.DamageEvent;


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
				for(Actor victim : a.room.entitiesAt(a.x + d.x, a.y + d.y, Actor.class))
					a.room.game.events.add(new DamageEvent(a, this, victim, new Damage(a.getLevel() * DAMAGE_PER_LEVEL, Damage.Type.FIRE), null));
	}

	@Override
	public Validity checkValidityOf(Actor a) {
		return Validity.OKAY;
	}
}
