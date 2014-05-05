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


public class AbilitySummonHellstone extends Ability implements ActivatedAbility {
	public AbilitySummonHellstone() {
		super(	"Summon Hellstone",
				"<Name> can summon hellstone next to <itself>!",
				1, 20);
	}

	public int getPriority(Class<? extends Hooked> c) {
		return 1;
	}

	public Status getStatusOf(Actor a, int x, int y) {
		Entity e = a.room.entityAt(x, y, Entity.Layer.GROUND);
		
		if (e != null) {
			if (e.isDestructible() && !(e instanceof Hellstone)) {
				if (Math.abs(x - a.x) < 2 && Math.abs(y - a.y) < 2) {
					return Status.OKAY;
				}
			}		
		}
		return Status.INVALID;
	}

	public void activate(Actor actor, int x, int y) {
		if (getStatusOf(actor, x, y) != Status.INVALID) {
			Entity target = actor.room.entityAt(x, y, Entity.Layer.GROUND);
			target.disable();
			actor.room.addEntity("h", x, y);
		}
	}

	@Override
	protected Instance getNewInstance(Actor a) {
		return new Instance(a);
	}
}
