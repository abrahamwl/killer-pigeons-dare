package net.bithaven.efficiencyrpg.ability;


import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class AbilityList extends LinkedHashSet<Ability> {
	private static final long serialVersionUID = -5451091765501641622L;

	@SuppressWarnings("unchecked")
	public <T extends Ability> T getFirstAbility(Class<T> c) {
		AbilityInterface out = null;
		int priority = Integer.MIN_VALUE;
		for (AbilityInterface ability : this) {
			if (c.isInstance(ability)) {
				if (c.isInstance(Hooked.class)) {
					if (((Hooked)ability).getPriority((Class<? extends Hooked>)c) > priority) out = ability;
				} else {
					return c.cast(ability);
				}
			}
		}
		return c.cast(out);
	}

	public <H extends Hooked> H getFirst(Class<H> c) {
		AbilityInterface out = null;
		int priority = Integer.MIN_VALUE;
		for (AbilityInterface ability : this) {
			if (c.isInstance(ability)) {
				if (((Hooked)ability).getPriority((Class<? extends Hooked>)c) > priority) out = ability;
			}
		}
		return c.cast(out);
	}
	
	public <H> ArrayList<H> getAll(Class<H> c) {
		ArrayList<H> out = new ArrayList<H>();

		for (AbilityInterface ability : this) {
			if (c.isInstance(ability)) {
				out.add(c.cast(ability));
			}
		}
		return out;
	}
	
	public <H extends Hooked> TreeSet<H> getPrioritizedSet(Class<H> c) {
		TreeSet<H> out = new TreeSet<H>(PriorityComparator.getComparator(c));

		for (AbilityInterface ability : this) {
			if (c.isInstance(ability)) {
				out.add(c.cast(ability));
			}
		}
		return out;
	}
	
	public EnumSet<MovementPassabilityModifier> getPassabilityModifiers() {
		EnumSet<MovementPassabilityModifier> out = EnumSet.noneOf(MovementPassabilityModifier.class);
		
		for (Ability ability : this) {
			if (ability.movementPassabilityModifier != null) {
				out.add(ability.movementPassabilityModifier);
			}
		}
		
		return out;
	}
}
