package net.bithaven.efficiencyrpg.ability.abilities;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.ConsumesMeleeAttacked;
import net.bithaven.efficiencyrpg.ability.Hooked;
import net.bithaven.efficiencyrpg.action.ActionMeleeAttack;
import net.bithaven.efficiencyrpg.effect.RisingTextEffect;
import net.bithaven.efficiencyrpg.effect.SoundEffect;
import net.bithaven.efficiencyrpg.entity.Actor;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;


public class AbilityBlock extends Ability implements ConsumesMeleeAttacked {
	public static final String name = "Block";
	public static final String generalDescription = " prevents the first melee attack against it in a turn.\nThis stacks with other abilities that prevent melee attacks.";
	public static final Image icon = Game.iconSheet.getSprite(2, 12);
	
	static {
		Ability.getAbilityTypes().add(AbilityBlock.class);
	}

	public AbilityBlock(Actor a) {
		super(a);
	}

	public int getPriority(Class<? extends Hooked> c) {
		return 0;
	}

	public void attacked(ActionMeleeAttack attack, Room r, Actor attacker) {
		attack.generateEffects(r, attacker);
		r.game.effects.add(new RisingTextEffect("BLOCK", a.getCenterX(), a.getCenterY(), Color.blue));
		setEnabled(false);
	}
}
