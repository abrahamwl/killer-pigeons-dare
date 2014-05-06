package net.bithaven.efficiencyrpg.ability;

public interface Hooked extends AbilityInterface {
	public int getPriority(Class<? extends Hooked> c);
}
