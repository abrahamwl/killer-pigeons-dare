package net.bithaven.efficiencyrpg.action;

import java.io.File;
import java.util.ArrayList;

import net.bithaven.efficiencyrpg.*;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.ConsumesMeleeAttacked;
import net.bithaven.efficiencyrpg.ability.TriggersOnMeleeHit;
import net.bithaven.efficiencyrpg.effect.RisingTextEffect;
import net.bithaven.efficiencyrpg.effect.SoundEffect;
import net.bithaven.efficiencyrpg.entity.*;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


public class ActionMeleeAttack extends ActionAttack {
	public static final int DAMAGE_PER_LEVEL = 5;
	public Dir dir;

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
			a.room.game.effects.add(new RisingTextEffect(String.valueOf(a.getLevel() * DAMAGE_PER_LEVEL),
					victim.x * Entity.CELL_SIZE + Entity.CELL_SIZE / 2,
					victim.y * Entity.CELL_SIZE + Entity.CELL_SIZE / 2));
			victim.applyDamage(a.getLevel() * DAMAGE_PER_LEVEL);

			for (TriggersOnMeleeHit trigger : a.activeAbilities.getPrioritizedSet(TriggersOnMeleeHit.class)) {
				trigger.hit(a, this, victim);
			}
			
			generateEffects(a);
		}
	}
	
	@Override
	public void generateEffects(Actor a) {
		super.generateEffects(a);
		a.room.game.effects.add(new SoundEffect(a.attackSound));
	}
}
