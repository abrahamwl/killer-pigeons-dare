package net.bithaven.efficiencyrpg.entity;

import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.ability.MovementPassabilityModifier;
import net.bithaven.efficiencyrpg.event.DamageEvent;
import net.bithaven.efficiencyrpg.other.Damage;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Hellstone extends Entity {
	public Hellstone () {
		super("Hellstone", "res/open1/dc-dngn/floor/rough_red0.png", Layer.GROUND);
	}

	@Override
	public boolean execute() {
		for(Actor a : room.entitiesAt(this.x, this.y, Actor.class))
			if(!a.activeAbilities.getPassabilityModifiers().contains(MovementPassabilityModifier.FLYING))
				room.game.events.add(new DamageEvent(null, null, a, new Damage(10, Damage.Type.FIRE), null));
		
		return true; //Doesn't do anything.
	}

	@Override
	public boolean passableFor(Actor a) {
		return true;
	}

}
