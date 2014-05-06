package net.bithaven.efficiencyrpg.entity;


import java.util.EnumSet;

import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.entity.Entity.Layer;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Wall extends Entity {
	public Wall () {
		super("Wall", "res/open1/dc-dngn/wall/stone_brick1.png", EnumSet.allOf(Layer.class));
	}

	@Override
	public boolean execute() {
		return true; //Doesn't do anything.
	}

	@Override
	public boolean passableFor(Actor a) {
		return false;
	}

	@Override
	public boolean isDestructible () {
		return false;
	}
}
