package game;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Goblin extends Actor {
	public Goblin () {
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/diggerguy.png", 33, 36, 0);
			image = sheet.getSubImage(0, 0);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void decide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute(Room r) {
		// TODO Auto-generated method stub
		
	}

}
