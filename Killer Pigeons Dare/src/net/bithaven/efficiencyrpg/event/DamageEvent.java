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

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class DamageEvent extends Event {
	private Actor actor;
	private Action action;
	private Actor target;
	private int damage;
	private EventState state = EventState.PREVENT_TURN;
	
	public DamageEvent (Actor actor, Action action, Actor target, int damage, Sound sound) {
		this.actor = actor;
		this.action = action;
		this.target = target;
		this.damage = damage;
		nextEvents.addFirst(new RisingTextEffect(String.valueOf(damage),
				target.x * Entity.CELL_SIZE + Entity.CELL_SIZE / 2,
				target.y * Entity.CELL_SIZE + Entity.CELL_SIZE / 2));
		if (sound != null) nextEvents.addFirst(new SoundEffect(sound));
	}

	@Override
	public void render(Game game, Graphics g) throws SlickException {
	}

	@Override
	public void update(Game game, int timePassed) throws SlickException {
		target.applyDamage(damage);

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
