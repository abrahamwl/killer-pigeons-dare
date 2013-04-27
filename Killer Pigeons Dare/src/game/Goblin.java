package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Goblin extends Actor {
	Image facingUp = null;
	Image facingLeft = null;
	Image facingRight = null;
	Image facingDown = null;
	
	public Goblin () {
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/goblinguy.png", 33, 36, 0);
			image = sheet.getSubImage(1, 0);
			facingUp = sheet.getSubImage(1, 2);
			facingLeft = sheet.getSubImage(1, 3);
			facingRight = sheet.getSubImage(1, 1);
			facingDown = sheet.getSubImage(1, 0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	boolean moving = false;
	boolean attacking = false;
	
	int wobble = 0;
	
	public void execute(Room r) {
		if (moving) {
			int offx = (int) Math.signum(this.x - r.game.hero.x);
			int offy = (int) Math.signum(this.y - r.game.hero.y);
			if(offx != 0 && offy != 0) {if (wobble == 1) offx = 0; else offy = 0; wobble = (wobble + 1) % 2;} // This code makes the goblin take a diagonal course by alternating horizontal and vertical steps

			int newx = (int) (this.x + offx);
			int newy = (int) (this.y + offy);
			// Move goblin one step towards hero
			if (!r.checkForTypeAt(newx, newy, Wall.class)) {
				this.x = newx;
				this.y = newy;
				
				if(offx == -1) image = facingLeft;
				if(offx ==  1) image = facingRight;
				if(offy == -1) image = facingDown;
				if(offy ==  1) image = facingUp;
			}
		} else if (attacking) {
			r.game.hero.hitpoints--;
		}
	}

	public void decide(Room r) {
		moving = false;
		attacking = false;
		if (Math.abs(this.x - r.game.hero.x) > 1 || Math.abs(this.y - r.game.hero.y) > 1) moving = true;
		else attacking = true;
	}
}
