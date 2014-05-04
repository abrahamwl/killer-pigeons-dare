package net.bithaven.efficiencyrpg.entity;

import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.ability.MovementPassabilityModifier;
import net.bithaven.efficiencyrpg.other.Damage;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Hellstone extends Entity {
	public Hellstone () {
		super("Hellstone", "res/open1/dc-dngn/floor/rough_red0.png");
	}

	@Override
	public boolean execute() {
		for(Entity e : room.entitiesAt(this.x, this.y, Actor.class))
			if(((Actor)e).activeAbilities.getPassabilityModifiers().contains(MovementPassabilityModifier.FIRE_FRIEND))
				((Actor)e).applyHealth(1);
			else if(!((Actor)e).activeAbilities.getPassabilityModifiers().contains(MovementPassabilityModifier.FLYING))
				((Actor)e).applyDamage(new Damage(2));
		
		return true; //Doesn't do anything.
	}

	@Override
	public boolean passableFor(Actor a) {
		return true;
	}

}
