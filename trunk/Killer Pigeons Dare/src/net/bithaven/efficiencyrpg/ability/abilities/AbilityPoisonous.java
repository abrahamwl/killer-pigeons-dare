package net.bithaven.efficiencyrpg.ability.abilities;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.Hooked;
import net.bithaven.efficiencyrpg.ability.TriggersOnMeleeHit;
import net.bithaven.efficiencyrpg.action.ActionMeleeAttack;
import net.bithaven.efficiencyrpg.entity.Actor;

import org.newdawn.slick.Image;


public class AbilityPoisonous extends Ability implements TriggersOnMeleeHit {
	public static final String name = "Poisonous";
	public static final String generalDescription = "'s melee attacks cause its level x 2 damage per turn.";
	public static final Image icon = Game.iconSheet.getSprite(2, 24);
	
	static {
		Ability.getAbilityTypes().add(AbilityPoisonous.class);
	}

	public AbilityPoisonous(Actor a) {
		super(a);
	}

	public int getPriority(Class<? extends Hooked> c) {
		return 0;
	}

	public void hit(ActionMeleeAttack attack, Room r, Actor victim) {
		victim.poisoned += a.getLevel() * 2;
	}

	@Override
	public String getDescription() {
		return a.name + "'s melee attacks cause " + a.level * 2 + " damage per turn.";
	}
}
