package net.bithaven.efficiencyrpg.ability.abilities;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.ability.*;
import net.bithaven.efficiencyrpg.action.ActionRangedAttack;
import net.bithaven.efficiencyrpg.entity.*;
import net.bithaven.efficiencyrpg.entity.Character;
import net.bithaven.efficiencyrpg.event.effect.*;
import net.bithaven.efficiencyrpg.other.Damage;


public class AbilityFlameBolt extends Ability implements ActivatedAbility {
	public AbilityFlameBolt() {
		super(	"Flame Bolt",
				"<Name> can fire a bolt of flame at a distance that does 3 fire damage per level.",
				0, 20);
	}

	public int getPriority(Class<? extends Hooked> c) {
		return 1;
	}

	public Status getStatusOf(Actor a, int x, int y) {
		ArrayList<Actor> actors = a.room.entitiesAt(x, y, Actor.class);
		
		if (actors.size() > 0) {
			Actor target = actors.get(0);
			if ((a instanceof Character) && (target instanceof Character)) {
				return Status.NOT_RECOMMENDED;
			} else if (!(a instanceof Character) && !(target instanceof Character)) {
				return Status.NOT_RECOMMENDED;
			} else {
				return Status.OKAY;
			}
		} else {
			return Status.INVALID;
		}
	}

	public void activate(Actor actor, int x, int y) {
		if (getStatusOf(actor, x, y) != Status.INVALID) {
			Actor target = actor.room.entitiesAt(x, y, Actor.class).get(0);
			ActionRangedAttack attack;
			try {
				ProjectileEffect projectileEffect = new ProjectileEffect(new Image("res/open1/effect/bolt04.png"), actor.getCenterX(), actor.getCenterY(), target.getCenterX(), target.getCenterY());
				projectileEffect.fireEffects.add(new SoundEffect(new Sound("res/fire_bolt.wav")));
				attack = new ActionRangedAttack(target, new Damage(actor.getLevel() * 3, Damage.Type.FIRE), projectileEffect);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				attack = new ActionRangedAttack(target, new Damage(actor.getLevel() * 3, Damage.Type.FIRE), null);
			}
			attack.execute(actor);
		}
	}

	@Override
	protected Instance getNewInstance(Actor a) {
		return new Instance(a);
	}
}
