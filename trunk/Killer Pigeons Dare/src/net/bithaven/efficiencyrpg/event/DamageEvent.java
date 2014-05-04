package net.bithaven.efficiencyrpg.event;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.ability.TriggersOnMeleeHit;
import net.bithaven.efficiencyrpg.ability.TriggersOnRangedHit;
import net.bithaven.efficiencyrpg.action.Action;
import net.bithaven.efficiencyrpg.action.ActionAttack;
import net.bithaven.efficiencyrpg.action.ActionMeleeAttack;
import net.bithaven.efficiencyrpg.action.ActionRangedAttack;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.Entity;
import net.bithaven.efficiencyrpg.event.effect.RisingTextEffect;
import net.bithaven.efficiencyrpg.event.effect.SoundEffect;
import net.bithaven.efficiencyrpg.other.Damage;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class DamageEvent extends Event {
	private Actor actor;
	private Action action;
	private Actor target;
	private Damage damage;
	private EventState state = EventState.PREVENT_TURN;
	private RisingTextEffect textEffect;
	
	public DamageEvent (Actor actor, Action action, Actor target, int damage, Sound sound) {
		this(actor, action, target, new Damage(damage), sound);
	}

	public DamageEvent (Actor actor, Action action, Actor target, Damage damage, Sound sound) {
		this.actor = actor;
		this.action = action;
		this.target = target;
		this.damage = damage;
		textEffect = new RisingTextEffect(String.valueOf(damage.amount), target);
		nextEvents.addFirst(textEffect);
		if (sound != null) nextEvents.addFirst(new SoundEffect(sound));
	}


	@Override
	public void render(Game game, Graphics g) throws SlickException {
	}

	@Override
	public void update(Game game, int timePassed) throws SlickException {
		int amount = target.applyDamage(damage).amount;
		if (amount < 0) {
			textEffect.color = Color.green;
		} else if (amount == 0) {
			textEffect.color = Color.gray;
		}

		if (action instanceof ActionMeleeAttack) {
			for (TriggersOnMeleeHit trigger : actor.activeAbilities.getPrioritizedSet(TriggersOnMeleeHit.class)) {
				trigger.hit(actor, (ActionMeleeAttack)action, target);
			}
		}

		if (action instanceof ActionRangedAttack) {
			for (TriggersOnRangedHit trigger : actor.activeAbilities.getPrioritizedSet(TriggersOnRangedHit.class)) {
				trigger.hit(actor, (ActionRangedAttack)action, target);
			}
		}
		
		state = EventState.DONE;
	}

	@Override
	public EventState getMyEventState() {
		return state;
	}

}
