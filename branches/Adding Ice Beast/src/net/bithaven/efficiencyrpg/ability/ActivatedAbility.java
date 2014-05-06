package net.bithaven.efficiencyrpg.ability;

import net.bithaven.efficiencyrpg.action.Validity;
import net.bithaven.efficiencyrpg.entity.Actor;
import net.bithaven.efficiencyrpg.entity.features.Damage;

abstract public class ActivatedAbility extends Ability implements Hooked {
	public abstract Validity checkValidityOf(Actor a, int x, int y);
	public abstract void activate(Actor a, int x, int y);

	protected ActivatedAbility (String name, String description, int x, int y, Category category, Damage.Type element) {
		super(name, description, x, y, category, element);
	}
}
