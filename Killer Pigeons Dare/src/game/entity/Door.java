package game.entity;

import game.Actor;
import game.Entity;
import game.Room;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Door extends Entity {
	private static SpriteSheet sheet = null;
	public int roomNumber = 1;
	private int distanceFromCharacterStart;
	
	public int getDistanceFromCharacterStart() {
		return distanceFromCharacterStart;
	}

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
		return true; //Doesn't do anything.
	}

	@Override
	public boolean passableFor(Actor a) {
		return true;
	}
	
	@Override
	public void init (Room r) {
		super.init(r);
		distanceFromCharacterStart = (int)(Math.abs(x - r.game.hero.x) + Math.abs(y - r.game.hero.y));
	}

}
