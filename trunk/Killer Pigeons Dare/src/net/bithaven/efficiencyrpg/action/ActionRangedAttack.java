package net.bithaven.efficiencyrpg.action;

import java.io.File;
import java.util.ArrayList;

import net.bithaven.efficiencyrpg.*;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.ConsumesMeleeAttacked;
import net.bithaven.efficiencyrpg.ability.ConsumesRangedAttacked;
import net.bithaven.efficiencyrpg.ability.TriggersOnMeleeHit;
import net.bithaven.efficiencyrpg.ability.TriggersOnRangedHit;
import net.bithaven.efficiencyrpg.entity.*;
import net.bithaven.efficiencyrpg.event.effect.*;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


public class ActionRangedAttack extends ActionAttack {
	public static final int DAMAGE_PER_LEVEL = 5;
	public int damage;
	public Actor target;
	public Sound fireSound;
	public Sound hitSound;
	public ProjectileEffect projectileEffect = null;

	public ActionRangedAttack(Actor target, int damage) {
		this.damage = damage;
		this.target = target;
	}

	@Override
	public void execute(Actor a) {
		ConsumesRangedAttacked response = target.activeAbilities.getFirst(ConsumesRangedAttacked.class);
		if (response != null) {
			response.attacked(target, this, a);
			return;
		}

		projectileEffect.hitEffects.add(new RisingTextEffect(
				String.valueOf(damage),
				target.getCenterX(),
				target.getCenterY()));

		generateEffects(a);

		target.applyDamage(damage);

		for (TriggersOnRangedHit trigger : a.activeAbilities.getPrioritizedSet(TriggersOnRangedHit.class)) {
			trigger.hit(a, this, target);
		}
		
	}
	
	@Override
	public void generateEffects(Actor a) {
		super.generateEffects(a);
		if (projectileEffect != null) {
			a.room.game.effects.add(projectileEffect);
		}
	}
}
