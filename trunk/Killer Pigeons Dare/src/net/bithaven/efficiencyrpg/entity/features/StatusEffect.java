package net.bithaven.efficiencyrpg.entity.features;

import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.event.DamageEvent;

import org.newdawn.slick.Color;

public class StatusEffect {
	public final Actor on;
	public final Effect effect;
	
	private static final float DEFAULT_AMOUNT = 1f;
	public float amount;
	
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
		POISONED(Color.green, EffectApplyMethod.ADD, 0f),
		STOPPED(Color.cyan, EffectApplyMethod.GREATEST, 1f);
		
		public final Color color;
		public final EffectApplyMethod applyMethod;
		public final float countDown;
		
		Effect (Color color, EffectApplyMethod applyMethod, float countDown) {
			this.color = color;
			this.applyMethod = applyMethod;
			this.countDown = countDown;
		}
		
	}
	
	private void reduceBy (float amount) {
		this.amount -= amount;
		if (this.amount <= 0f) {
			on.statusEffects.remove(effect);
		}
	}
	
	public void execute () {
		switch (effect) {
		case POISONED:
			on.room.game.events.add(new DamageEvent(null, null, on, new Damage(((Float)amount).intValue(), Damage.Type.POISON), null));
			break;
		default:
			break;
		}
		reduceBy(effect.countDown);
	}
	
	@Override
	public String toString () {
		return effect.toString() + " " + ((Float)amount).intValue();
	}
}
