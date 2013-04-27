package game;

import org.newdawn.slick.*;

public class Character extends Actor {
	public Character () {
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet("res/diggerguy.png", 33, 36, 0);
			image = sheet.getSubImage(0, 0);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	int hitpoints = 10;
	
	@Override
	public void execute(Room r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decide(Room r) {
		// TODO Auto-generated method stub
		
	}
}
