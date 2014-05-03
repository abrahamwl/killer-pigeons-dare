package net.bithaven.efficiencyrpg.ability.abilities;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.Hooked;
import net.bithaven.efficiencyrpg.ability.TriggersAfterInit;
import net.bithaven.efficiencyrpg.entity.Actor;

import org.newdawn.slick.Image;


public class AbilityTough extends Ability implements TriggersAfterInit {
	public static final String name = "Tough";
	public static final String generalDescription = " has 50% more hitpoints.";
	public static final Image icon = Game.iconSheet.getSprite(9, 18);
	
	public AbilityTough(Actor a) {
		super(a);
	}

	public void afterInit() {
		a.maxHitpoints *= 1.5;
		a.hitpoints = a.maxHitpoints;
	}

	public int getPriority(Class<? extends Hooked> c) {
		return 0;
	}
}
