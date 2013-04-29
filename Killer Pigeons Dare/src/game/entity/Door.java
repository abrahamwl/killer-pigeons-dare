package game.entity;

import java.util.ArrayList;

import game.Actor;
import game.Entity;
import game.Room;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Door extends Entity {
	private static SpriteSheet sheet = null;
	int roomNumber = 1;
	
	public Door (int roomNumber) {
		super("Door");
		this.roomNumber = roomNumber;
		if (sheet == null) {
			try {
				sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		image = sheet.getSubImage(1, 5); 
	}

	@Override
	public boolean execute(Room r) {
		ArrayList<Entity> hero = r.entitiesAt(this.x, this.y, Character.class);
		if(hero.size() > 0) 
			r.game.loadRoom(roomNumber);
		return true; //Doesn't do anything.
	}

	@Override
	public boolean passableFor(Actor a) {
		return true;
	}

}
