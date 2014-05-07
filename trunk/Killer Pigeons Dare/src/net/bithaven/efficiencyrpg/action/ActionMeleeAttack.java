package net.bithaven.efficiencyrpg.action;

import java.util.ArrayList;

import net.bithaven.efficiencyrpg.*;
import net.bithaven.efficiencyrpg.ability.ConsumesMeleeAttacked;
import net.bithaven.efficiencyrpg.entity.*;
import net.bithaven.efficiencyrpg.entity.Character;
import net.bithaven.efficiencyrpg.entity.features.Damage;
import net.bithaven.efficiencyrpg.event.DamageEvent;

public class ActionMeleeAttack extends ActionAttack {
	public static final int DAMAGE_PER_LEVEL = 5;
	public Dir dir;
	
	private DamageEvent damageEvent = null;

	public ActionMeleeAttack(Dir dir) {
		this.dir = dir;
	}

	@Override
	public void execute(Actor a) {
		if (checkValidityOf(a) == Validity.INVALID) {
			return;
		}
		
		ArrayList<Actor> target = a.room.entitiesAt(a.x + dir.x, a.y + dir.y, Actor.class);

		if (target.size() > 0) {
			Actor victim = (Actor)(target.get(0));
			ConsumesMeleeAttacked response = victim.activeAbilities.getFirst(ConsumesMeleeAttacked.class);
			if (response != null) {
				response.attacked(victim, this, a);
				return;
			}
			
			damageEvent = new DamageEvent(a, this, victim, new Damage(a.getLevel() * DAMAGE_PER_LEVEL, a.defaultMeleeDamageType), a.attackSound);

			generateEvents(a);
		}
	}
	
	@Override
	public void generateEvents(Actor a) {
		super.generateEvents(a);
		if (damageEvent != null) a.room.game.events.add(damageEvent);
	}

	@Override
	public Validity checkValidityOf(Actor a) {
		ArrayList<Actor> target = a.room.entitiesAt(a.x + dir.x, a.y + dir.y, Actor.class);

		if (target.size() > 0) {
			Actor victim = (Actor)(target.get(0));
			
			if (!(a instanceof Character) && !(victim instanceof Character)) {
				return Validity.NOT_RECOMMENDED;
			} else {
				return Validity.OKAY;
			}
		} else {
			return Validity.INVALID;
		}
	}
}
