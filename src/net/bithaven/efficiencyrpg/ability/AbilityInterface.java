package net.bithaven.efficiencyrpg.ability;

import org.newdawn.slick.Image;

import net.bithaven.efficiencyrpg.Game;
import net.bithaven.efficiencyrpg.ability.Ability.DisplayElement;
import net.bithaven.efficiencyrpg.ability.Ability.Instance;
import net.bithaven.efficiencyrpg.entity.Actor;

public interface AbilityInterface {
	
	public abstract Instance on(Actor a);

	public abstract String getGeneralDescription();

	public abstract DisplayElement getDisplayElement(Game game, int x, int y);

	public abstract DisplayElement getDisplayElement(Actor a, Game game, int x, int y);

	public abstract DisplayElement getDisplayElement(Actor a, Game game, int x,
			int y, int targetX, int targetY);

	public abstract void remove(Actor actor);

	public String getName();

	public Image getIcon();
}