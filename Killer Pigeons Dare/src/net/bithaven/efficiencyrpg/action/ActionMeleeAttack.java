package net.bithaven.efficiencyrpg.action;

import java.io.File;
import java.util.ArrayList;

import net.bithaven.efficiencyrpg.*;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.ConsumesMeleeAttacked;
import net.bithaven.efficiencyrpg.ability.TriggersOnMeleeHit;
import net.bithaven.efficiencyrpg.entity.*;
import net.bithaven.efficiencyrpg.event.DamageEvent;
import net.bithaven.efficiencyrpg.event.effect.RisingTextEffect;
import net.bithaven.efficiencyrpg.event.effect.SoundEffect;
import net.bithaven.efficiencyrpg.other.Damage;
import net.bithaven.efficiencyrpg.other.Damage.Type;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


public class ActionMeleeAttack extends ActionAttack {
	public static final int DAMAGE_PER_LEVEL = 5;
	public Dir dir;
	
	private DamageEvent damageEvent = null;

	public ActionMeleeAttack(Dir dir) {
		this.dir = dir;
	}

	@Override
	public void execute(Actor a) {
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
}
