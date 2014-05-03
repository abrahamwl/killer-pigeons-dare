package net.bithaven.efficiencyrpg.ability.abilities;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.ConsumesMeleeAttacked;
import net.bithaven.efficiencyrpg.ability.Hooked;
import net.bithaven.efficiencyrpg.action.ActionAttack;
import net.bithaven.efficiencyrpg.action.ActionMeleeAttack;
import net.bithaven.efficiencyrpg.effect.RisingTextEffect;
import net.bithaven.efficiencyrpg.entity.Actor;

import org.apache.commons.lang3.text.WordUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;


public class AbilityCounterWait extends Ability implements ConsumesMeleeAttacked {
	public static final String name = "Counter Wait";
	public static final String generalDescription = " prevents the first melee attack against it in a turn and makes a melee attack back against the attacker.\nThis stacks with other abilities that prevent melee attacks.";
	public static final Image icon = Game.iconSheet.getSprite(5, 25);
	
	static {
		Ability.getAbilityTypes().add(AbilityCounterWait.class);
	}

	public AbilityCounterWait(Actor a) {
		super(a);
	}

	public int getPriority(Class<? extends Hooked> c) {
		return 1;
	}

	public void attacked(ActionMeleeAttack attack, Room r, Actor attacker) {
		attack.generateEffects(r, attacker);
		r.game.effects.add(new RisingTextEffect("COUNTER", a.getCenterX(), a.getCenterY(), Color.blue));
		setEnabled(false);
		ActionMeleeAttack counter = new ActionMeleeAttack(attack.dir.flip());
		counter.execute(r, a);
	}
	
	@Override
	public void doNewTurn() {
		setEnabled(false);
	}

	@Override
	public void doWait() {
		super.doWait();
		setEnabled(true);
	}

}
