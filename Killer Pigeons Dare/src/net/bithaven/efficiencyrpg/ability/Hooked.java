package net.bithaven.efficiencyrpg.ability;

public interface Hooked {
	public int getPriority(Class<? extends Hooked> c);
}
