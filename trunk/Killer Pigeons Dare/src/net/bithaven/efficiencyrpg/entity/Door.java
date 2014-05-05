package net.bithaven.efficiencyrpg.entity;


import java.util.EnumSet;

import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.entity.Entity.Layer;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Door extends Entity {
	public int roomNumber = 1;
	private int distanceFromCharacterStart;
	
	public int getDistanceFromCharacterStart() {
		return distanceFromCharacterStart;
	}

	public Door (int roomNumber) {
		super("Door", "res/open1/dc-dngn/dngn_open_door.png", EnumSet.range(Layer.GROUND, Layer.THING));
		this.roomNumber = roomNumber;
	}

	@Override
	public boolean execute() {
		return true; //Doesn't do anything.
	}

	@Override
	public boolean passableFor(Actor a) {
		return true;
	}
	
	@Override
	public void init (Room r) {
		super.init(r);
		distanceFromCharacterStart = (int)Math.max(Math.abs(x - r.game.hero.x), Math.abs(y - r.game.hero.y));
	}

	@Override
	public boolean isDestructible () {
		return false;
	}
}
