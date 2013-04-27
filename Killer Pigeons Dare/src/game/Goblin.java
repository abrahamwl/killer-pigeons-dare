package game;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Goblin extends Actor {
	public Goblin () {
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/goblinguy.png", 33, 36, 0);
			image = sheet.getSubImage(0, 0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	boolean moving = false;
	boolean attacking = false;
	
	@Override
	public void execute(Room r) {
		if (moving) {
			int newx = (int) (this.x + Math.signum(this.x - r.game.hero.x));
			int newy = (int) (this.y + Math.signum(this.y - r.game.hero.y));
			// Move goblin one step towards hero
			if (!r.checkForTypeAt(newx, newy, Wall.class)) {
				this.x = newx;
				this.y = newy;
			}
		} else if (attacking) {
			r.game.hero.hitpoints--;
		}
	}

	@Override
	public void decide(Room r) {
		moving = false;
		attacking = false;
		if (Math.abs(this.x - r.game.hero.x) > 1 || Math.abs(this.y - r.game.hero.y) > 1) moving = true;
		else attacking = true;
	}
}
