package net.bithaven.efficiencyrpg.ability.abilities;

import org.newdawn.slick.Image;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.Ability.DisplayElement;
import net.bithaven.efficiencyrpg.ability.Ability.Instance;
import net.bithaven.efficiencyrpg.ability.HandlesDamage;
import net.bithaven.efficiencyrpg.ability.Hooked;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.other.Damage;

public class AbilityFireFriend extends Ability implements HandlesDamage {
	public AbilityFireFriend() {
		super(	"Fire Friend",
				" is healed by fire.",
				0, 26);
	}

	public int getPriority(Class<? extends Hooked> c) {
		return 1;
	}

	public Damage modifyDamage(Damage damage) {
		if (damage.type == Damage.Type.FIRE) {
			return new Damage(-1 * Math.abs(damage.amount), damage.type);
		}
		
		return damage;
	}

	@Override
	protected Instance getNewInstance(Actor a) {
		return new Instance(a);
	}
}
