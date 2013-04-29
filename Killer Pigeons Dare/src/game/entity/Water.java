package game.entity;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import game.Ability;
import game.Room;

public class Water extends Entity {
	private static SpriteSheet sheet = null;
	
	public Water () {
		super("Water");
		if (sheet == null) {
			try {
				sheet = new SpriteSheet("res/game.png", 63, 63, 1);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		image = sheet.getSubImage(0, 8);
	}

	@Override
	public boolean execute(Room r) {
		return true; //Doesn't do anything.
	}

	@Override
	public boolean passableFor(Actor actor) {
		for (Ability a : actor.abilities) {
			if (a.type == Ability.Type.FLYING && a.active) {
				return true;
			}
		}
		
		return false;
	}

}
