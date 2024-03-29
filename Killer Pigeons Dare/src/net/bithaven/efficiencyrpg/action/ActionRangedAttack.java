package net.bithaven.efficiencyrpg.action;

import net.bithaven.efficiencyrpg.ability.ConsumesRangedAttacked;
import net.bithaven.efficiencyrpg.entity.*;
import net.bithaven.efficiencyrpg.entity.Character;
import net.bithaven.efficiencyrpg.entity.features.Damage;
import net.bithaven.efficiencyrpg.event.DamageEvent;
import net.bithaven.efficiencyrpg.event.Event;
import net.bithaven.efficiencyrpg.event.effect.*;

import org.newdawn.slick.Sound;


public class ActionRangedAttack extends ActionAttack {
	public static final int DAMAGE_PER_LEVEL = 5;
	public Damage damage;
	public Actor target;
	public Sound fireSound;
	public Sound hitSound;
	private Event projectileEvent = null;

	public ActionRangedAttack(Actor target, Damage damage, ProjectileEffect effect) {
		this.damage = damage;
		this.target = target;
		this.projectileEvent = effect;
	}

	@Override
	public void execute(Actor a) {
		ConsumesRangedAttacked response = target.activeAbilities.getFirstHooked(ConsumesRangedAttacked.class);
		if (response != null) {
			response.attacked(target, this, a);
			return;
		}

		if (projectileEvent != null) {
			projectileEvent.nextEvents.addFirst(new DamageEvent(a, this, target, damage, null));
		} else {
			projectileEvent = new DamageEvent(a, this, target, damage, null);
		}

		generateEvents(a);
	}
	
	@Override
	public void generateEvents(Actor a) {
		super.generateEvents(a);
		if (projectileEvent != null) {
			a.room.game.events.add(projectileEvent);
		}
	}

	@Override
	public Validity checkValidityOf(Actor a) {
		if (!(a instanceof Character) && !(target instanceof Character)) {
			return Validity.NOT_RECOMMENDED;
		} else {
			return Validity.OKAY;
		}
	}
}
