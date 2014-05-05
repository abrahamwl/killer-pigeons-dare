package net.bithaven.efficiencyrpg.entity;


import java.util.EnumSet;
import java.util.Set;

import net.bithaven.efficiencyrpg.Room;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Entity {
	public int x, y; //Location in room coordinates.
	public static final int CELL_SIZE = 32;
	public boolean noDraw = false;
	public static boolean createImage = true; // In case Entities are being generated by a utility
												// instead of the game.
	public enum Gender {
		NEUTRAL("it", "it", "itself", "its"),
		MALE("he", "him", "himself", "his"),
		FEMALE("she", "her", "herself", "her");
		
		public final String subject, object, reflexive, possesive;
		private Gender (String subject, String object, String reflexive, String possesive) {
			this.subject = subject;
			this.object = object;
			this.reflexive = reflexive;
			this.possesive = possesive;
		}
	}
	
	public enum Layer {
		GROUND,
		THING,
		ACTOR;
	}
	
	public final EnumSet<Layer> layers;
	
	public Gender gender = Gender.NEUTRAL;
	
	public Room room;
	
	public String name;

	public Image image = null;
	
	public Entity(String name, String imageName, Layer layer) {
		this(name, imageName, EnumSet.of(layer));
	}
	
	public Entity(String name, String imageName, EnumSet<Layer> layers) {
		this.name = name;
		this.layers = layers;
		try {
			if (createImage) this.image = new Image(imageName);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void render(GameContainer gc, Graphics g) {
		if (!noDraw) {
			image.draw(x * CELL_SIZE, y * CELL_SIZE);
		}
	}
	
	/**
	 * @return true unless the entity has not finished taking actions this turn
	 */
	abstract public boolean execute();
	
	public void init (Room r) {
		room = r;
	}
	
	public abstract boolean passableFor(Actor a);

	public void cleanup() {
	}
	
	public boolean isDestructible () {
		return true;
	}
}
