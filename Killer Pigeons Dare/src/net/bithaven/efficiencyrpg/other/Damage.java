package net.bithaven.efficiencyrpg.other;

public class Damage {
	public int amount = 0;
	public Type type = Type.IMPACT;
	
	public enum Type {
		IMPACT,
		FIRE;
	}
}
