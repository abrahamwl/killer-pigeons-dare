package game.entity;

import game.Actor;
import game.Controller;
import game.Room;
import game.action.Action;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Flameo extends Actor {
	public Flameo () {
		super("Flame", 1);
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/blueman.png", 33, 36, 0);
			image = sheet.getSubImage(6, 0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	boolean movingToward = false;
	boolean movingAway = false;
	boolean eatGoblin = false;
	
	int wobble = 0;
	
	@Override
	public boolean execute(Room r) {
		if (movingToward) {
			int offx = (int) Math.signum(this.x - r.game.hero.x);
			int offy = (int) Math.signum(this.y - r.game.hero.y);
			if (offx != 0 && offy != 0) {if (wobble == 1) offx = 0; else offy = 0; wobble = (wobble + 1) % 2;} // This code makes the blueman take a diagonal course by alternating horizontal and vertical steps

			int newx = (int) (this.x + offx);
			int newy = (int) (this.y + offy);
			// Move blueman one step towards hero
			if (!r.checkForTypeAt(newx, newy, Wall.class)) {
				this.x = newx;
				this.y = newy;
			}
		} else if (movingAway) {
			int offx = (int) -Math.signum(this.x - r.game.hero.x);
			int offy = (int) -Math.signum(this.y - r.game.hero.y);
			if (offx != 0 && offy != 0) {if (wobble == 1) offx = 0; else offy = 0; wobble = (wobble + 1) % 2;} // This code makes the blueman take a diagonal course by alternating horizontal and vertical steps

			int newx = (int) (this.x + offx);
			int newy = (int) (this.y + offy);
			// Move blueman one step towards hero
			if (!r.checkForTypeAt(newx, newy, Wall.class)) {
				this.x = newx;
				this.y = newy;
			}
		}
		return true;
	}
	
	public class BluemanBrain implements Controller {

		@Override
		public Action chooseNextAction(Room room, GameContainer gc) {
			return null;
		}
		
	}
}
