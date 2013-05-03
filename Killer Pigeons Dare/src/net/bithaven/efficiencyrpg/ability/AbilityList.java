package net.bithaven.efficiencyrpg.ability;


import java.util.EnumSet;
import java.util.HashSet;
import java.util.TreeSet;

public class AbilityList extends HashSet<Ability> {
	public <H extends Hooked> H getFirst(Class<H> c) {
		Ability out = null;
		int priority = Integer.MIN_VALUE;
		for (Ability ability : this) {
			if (c.isInstance(ability)) {
				if (c.cast(ability).getPriority(c) > priority)
				out = ability;
			}
		}
		return c.cast(out);
	}

	public <H extends Hooked> TreeSet<H> getPrioritizedSet(Class<H> c) {
		TreeSet<H> out = new TreeSet<H>(PriorityComparator.getComparator(c));

		for (Ability ability : this) {
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
