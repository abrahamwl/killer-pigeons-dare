package net.bithaven.efficiencyrpg.other;

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
	
	public enum Type {
		IMPACT,
		FIRE,
		POISON;
	}
}