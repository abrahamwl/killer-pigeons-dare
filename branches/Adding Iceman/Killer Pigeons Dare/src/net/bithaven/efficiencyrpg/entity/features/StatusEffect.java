package net.bithaven.efficiencyrpg.entity.features;

import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.event.DamageEvent;

import org.newdawn.slick.Color;

public class StatusEffect {
	public final Actor on;
	public final Effect effect;
	
	private static final float DEFAULT_AMOUNT = 1f;
	public float amount = DEFAULT_AMOUNT;
	
	private StatusEffect (Actor on, Effect effect, float amount) {
		this.on = on;
		this.effect = effect;
		this.amount = amount;
	}
	
	public static void apply (Actor on, Effect effect) {
		apply(on, effect, DEFAULT_AMOUNT);
	}
	
	public static void apply (Actor on, Effect effect, float amount) {
		StatusEffect status = on.statusEffects.get(effect);
		if (status == null) {
			on.statusEffects.put(effect, new StatusEffect(on, effect, amount));
			return;
		}

		switch (effect.applyMethod) {
		case ADD:
			status.amount += amount;
			break;
		case GREATEST:
			status.amount = Math.max(amount, status.amount);
			break;
		case NEWEST:
			status.amount = amount;
			break;
		default:
			on.statusEffects.put(effect, new StatusEffect(on, effect, amount));
			break;
		}
	}
	
	public enum EffectApplyMethod { ADD, GREATEST, NEWEST; }
	
	public enum Effect {
		POISONED(Color.green, EffectApplyMethod.ADD),
		STOPPED(Color.cyan, EffectApplyMethod.GREATEST);
		
		public final Color color;
		public final EffectApplyMethod applyMethod;
		
		Effect (Color color, EffectApplyMethod applyMethod) {
			this.color = color;
			this.applyMethod = applyMethod;
		}
		
	}
	
	private void reduceBy (float amount) {
		this.amount -= amount;
		if (this.amount <= 0) {
			on.statusEffects.remove(effect);
		}
	}
	
	public void execute () {
		switch (effect) {
		case POISONED:
			on.room.game.events.add(new DamageEvent(null, null, on, new Damage(((Float)amount).intValue(), Damage.Type.POISON), null));
			break;
		case STOPPED:
			reduceBy(1f);
			break;
		}
	}
	
	@Override
	public String toString () {
		return effect.toString() + " " + ((Float)amount).intValue();
	}
}
