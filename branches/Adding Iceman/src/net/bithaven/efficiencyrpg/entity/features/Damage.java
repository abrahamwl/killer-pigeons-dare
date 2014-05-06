package net.bithaven.efficiencyrpg.entity.features;

public class Damage {
	public Damage(int damage) {
		this(damage, Type.IMPACT);
	}

	public Damage(int damage, Type type) {
		amount = damage;
		this.type = type;
	}

	public final int amount;
	public final Type type;
	
	public static enum Type {
		IMPACT("Impact)"),
		FIRE("Fire"),
		POISON("Poison");
		
		private final String name;
		
		Type (String name) {
			this.name = name;
		}
		
		public String toString() {
			return name;
		}
	}
}
