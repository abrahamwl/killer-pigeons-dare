package net.bithaven.efficiencyrpg.ability.abilities;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.ability.Ability;
import net.bithaven.efficiencyrpg.ability.ConsumesMeleeAttacked;
import net.bithaven.efficiencyrpg.ability.Hooked;
import net.bithaven.efficiencyrpg.action.ActionMeleeAttack;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.event.effect.RisingTextEffect;
import net.bithaven.efficiencyrpg.event.effect.SoundEffect;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;


public class AbilityBlock extends Ability implements ConsumesMeleeAttacked {	
	public AbilityBlock() {
		super(	"Block",
				" prevents the first melee attack against it in a turn.\nThis stacks with other abilities that prevent melee attacks.",
				Game.iconSheet.getSprite(2, 12));
	}

	public int getPriority(Class<? extends Hooked> c) {
		return 0;
	}
	
	public void attacked(Actor a, ActionMeleeAttack attack, Actor attacker) {
		attack.generateEvents(attacker);
		a.room.game.events.add(new RisingTextEffect("BLOCK", a.getCenterX(), a.getCenterY(), Color.blue));
		on(a).setEnabled(false);
	}

	@Override
	protected Instance getNewInstance(Actor a) {
		return new Instance(a);
	}
}
