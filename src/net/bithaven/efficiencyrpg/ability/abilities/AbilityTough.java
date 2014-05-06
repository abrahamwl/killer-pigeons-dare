package net.bithaven.efficiencyrpg.ability.abilities;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.Hooked;
import net.bithaven.efficiencyrpg.ability.TriggersAfterInit;
import net.bithaven.efficiencyrpg.entity.Actor;

import org.newdawn.slick.Image;


public class AbilityTough extends Ability implements TriggersAfterInit {
	public AbilityTough() {
		super(	"Tough",
				"<Name> has 50% more hitpoints.",
				9, 18);
	}

	public void afterInit(Actor a) {
		a.maxHitpoints *= 1.5;
		a.hitpoints = a.maxHitpoints;
	}

	public int getPriority(Class<? extends Hooked> c) {
		return 0;
	}

	@Override
	protected Instance getNewInstance(Actor a) {
		return new Instance(a);
	}
}
