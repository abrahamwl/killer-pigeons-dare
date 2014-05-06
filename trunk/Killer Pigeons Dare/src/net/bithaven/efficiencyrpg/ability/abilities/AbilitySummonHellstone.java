package net.bithaven.efficiencyrpg.ability.abilities;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.ability.*;
import net.bithaven.efficiencyrpg.action.ActionRangedAttack;
import net.bithaven.efficiencyrpg.action.Validity;
import net.bithaven.efficiencyrpg.entity.*;
import net.bithaven.efficiencyrpg.entity.Character;
import net.bithaven.efficiencyrpg.entity.features.Damage;
import net.bithaven.efficiencyrpg.event.effect.*;


public class AbilitySummonHellstone extends Ability implements ActivatedAbility {
	public AbilitySummonHellstone() {
		super(	"Summon Hellstone",
				"<Name> can summon hellstone next to <itself>!",
				1, 20);
	}

	public int getPriority(Class<? extends Hooked> c) {
		return 1;
	}

	public Validity checkValidityOf(Actor a, int x, int y) {
		if (Math.abs(x - a.x) > 1 || Math.abs(y - a.y) > 1) {
			return Validity.INVALID;
		}
		
		ArrayList<Entity> entities = a.room.entitiesAt(x, y, Entity.Layer.GROUND);
		if (entities.size() == 0) {
			return Validity.INVALID;
		}
		for (Entity e : entities) {
			if (!e.isDestructible()) {
				return Validity.INVALID;
			}else if (e instanceof Hellstone) {
				return Validity.INVALID;
			}
		}
		
		return Validity.OKAY;
	}

	public void activate(Actor actor, int x, int y) {
		if (checkValidityOf(actor, x, y) != Validity.INVALID) {
			ArrayList<Entity> targets = actor.room.entitiesAt(x, y, Entity.Layer.GROUND);
			for (Entity e : targets) e.disable();
			targets = actor.room.entitiesAt(x, y, Entity.Layer.THING);
			for (Entity e : targets)
				if (e.isDestructible()) e.disable();
			actor.room.addEntity("h", x, y);
		}
	}

	@Override
	protected Instance getNewInstance(Actor a) {
		return new Instance(a);
	}
}
