package net.bithaven.efficiencyrpg.ability;

public interface Hooked {
	default public int getPriority(Class<? extends Hooked> c) {
		return 0;
	}
}
